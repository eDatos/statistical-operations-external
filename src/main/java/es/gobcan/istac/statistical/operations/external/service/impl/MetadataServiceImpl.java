package es.gobcan.istac.statistical.operations.external.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import es.gobcan.istac.statistical.operations.external.config.ApplicationProperties;
import es.gobcan.istac.statistical.operations.external.service.MetadataService;

@Service("metadataService")
public class MetadataServiceImpl implements MetadataService {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    private ApplicationProperties applicationProperties;

    private String operationsApi;
    private String navbarUrl;
    private String footerUrl;

    private UriComponentsBuilder metadataEndpoint;

    @PostConstruct
    public void fetchMetadataValues() {

        this.metadataEndpoint = UriComponentsBuilder.fromHttpUrl(applicationProperties.getMetadata().getEndpoint()).path("/properties/{property-id}");

        // Temporal hasta que se actualicen los metadatos
        this.operationsApi = this.getPropertyById(applicationProperties.getMetadata().getOperationsApiKey()) + "/v1.0/operations";
        this.navbarUrl = "http://estadisticas.arte-consultores.com/sie/external-static/navbar/navbar.html"; // this.getPropertyById(applicationProperties.getMetadata().getNavbarPathKey());
        this.footerUrl = "http://estadisticas.arte-consultores.com/sie/external-static/footer/footer.html"; // this.getPropertyById(applicationProperties.getMetadata().getFooterPathKey());

    }

    @Override
    public String getOperationsApi() {
        return operationsApi;
    }

    @Override
    public String getNavbarUrl() {
        return navbarUrl;
    }

    @Override
    public String getFooterUrl() {
        return footerUrl;
    }

    @Override
    public String getPropertyById(String propertyId) {
        log.debug("Obteniendo valor de metadata: {}", propertyId);
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> res = restTemplate.getForObject(metadataEndpoint.buildAndExpand(propertyId).toUriString(), Map.class);
        return res.get("value");
    }

}
