package es.gobcan.istac.statistical.operations.external.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @PostConstruct
    public void fetchMetadataValues() {
        // Temporal hasta que se actualicen los metadatos
        this.operationsApi = "https://www3.gobiernodecanarias.org/istac/api/operations/v1.0"; // this.getPropertyById(applicationProperties.getMetadata().getOperationsApiKey());
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

        Map<String, String> res = restTemplate.getForObject(applicationProperties.getMetadata().getEndpoint() + "/properties/" + propertyId, Map.class);
        return res.get("value");
    }

}
