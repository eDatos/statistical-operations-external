package es.gobcan.istac.statistical.operations.external.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import es.gobcan.istac.statistical.operations.external.service.MetadataService;
import es.gobcan.istac.statistical.operations.external.service.OperationService;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@Service
public class OperationServiceImpl implements OperationService {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private UriComponentsBuilder operationsApiUrl;
    @Autowired
    private MetadataService metadataService;

    @PostConstruct
    public void init() {
        this.operationsApiUrl = UriComponentsBuilder.fromHttpUrl(metadataService.getOperationsApi());
    }

    @Override
    public Operation findOperation(String operationId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(this.operationsApiUrl.cloneBuilder().path("/{operation-id}").buildAndExpand(operationId).toUriString(), Operation.class);
    }

    @Override
    public Operations findBySubjectArea(String areaId) {
        String areaUrn = buildNestedId(areaId);
        log.debug("Consultando operaciones del area: {} con urn: {}", areaId, areaUrn);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(this.operationsApiUrl.cloneBuilder()
                .query("query=SUBJECT_AREA_URN LIKE \"urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:TEMAS_CANARIAS(01.000).{q}\"").buildAndExpand(areaUrn).toUriString(), Operations.class);
    }

    private String buildNestedId(String areaId) {
        String ids[] = areaId.split("_");

        ArrayList<String> urnSection = new ArrayList<String>();
        for (int i = 0; i < ids.length; i++) {
            ArrayList<String> urnChunk = new ArrayList<String>();
            for (int j = 0; j <= i; j++) {
                urnChunk.add(ids[j]);
            }
            urnSection.add(StringUtils.join(urnChunk, "_"));
        }
        return StringUtils.join(urnSection, ".");
    }

}
