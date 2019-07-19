package es.gobcan.istac.statistical.operations.external.service.impl;

import javax.annotation.PostConstruct;

import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import es.gobcan.istac.statistical.operations.external.service.MetadataService;
import es.gobcan.istac.statistical.operations.external.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

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

}
