package es.gobcan.istac.statistical.operations.external.service.impl;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;

import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import es.gobcan.istac.statistical.operations.external.config.ApplicationProperties;
import es.gobcan.istac.statistical.operations.external.service.MetadataService;
import es.gobcan.istac.statistical.operations.external.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private ApplicationProperties applicationProperties;

    private UriComponentsBuilder operationsApiUrl;

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
    public Operations findBySubjectArea(String nestedId) {
        log.debug("Consultando operaciones del area: {}", nestedId);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForObject(this.operationsApiUrl.cloneBuilder().query("query=SUBJECT_AREA_URN LIKE \"urn:sdmx:org.sdmx.infomodel.categoryscheme.Category={categorySchemePrefix}.{nestedId}\"")
                        .buildAndExpand(applicationProperties.getCategoriesSchemes().getSchemePrefix(), nestedId).toUriString(), Operations.class);
    }

}
