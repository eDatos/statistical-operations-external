package es.gobcan.istac.statistical.operations.external.service;

import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operations;

public interface OperationService {

    Operation findOperation(String operationId);

    Operations findBySubjectArea(String areaId);

    Instances findOperationInstances(String operationId);

    Instance findOperationInstance(String operationId, String instanceId);
}
