package es.gobcan.istac.statistical.operations.external.service.impl;

import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.statistical.operations.external.config.ApplicationProperties;
import es.gobcan.istac.statistical.operations.external.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public Operation findOperation(String operationId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(applicationProperties.getMetadata().getOperationsApi() + "/operations/" + operationId, Operation.class);
    }

}
