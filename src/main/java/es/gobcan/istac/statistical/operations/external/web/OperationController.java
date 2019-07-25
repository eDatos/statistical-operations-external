package es.gobcan.istac.statistical.operations.external.web;

import java.lang.invoke.MethodHandles;

import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import es.gobcan.istac.statistical.operations.external.service.OperationService;

@Controller
public class OperationController {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private OperationService operationService;

    @GetMapping(value = {"", "/index.html"})
    public ModelAndView index() {
        log.debug("Operaciones");
        return new ModelAndView("pages/operations");
    }

    @GetMapping("/operations/{operationId}")
    public ModelAndView operation(@PathVariable String operationId) {
        log.debug("Operación {}", operationId);
        Operation operation = operationService.findOperation(operationId);
        Instances operationInstances = operationService.findOperationInstances(operationId);

        ModelAndView model = new ModelAndView("pages/operation");
        model.addObject("operation", operation);
        model.addObject("instances", operationInstances);
        return model;
    }

    @GetMapping("/operations/subject-area/{subjectNestedId:.+}")
    public ModelAndView subjectOperations(@PathVariable String subjectNestedId) {
        log.debug("Operaciones de la área temática con nestedId: {}", subjectNestedId);
        Operations operations = operationService.findBySubjectArea(subjectNestedId);
        return new ModelAndView("pages/subject-area-operations", "operations", operations);
    }

    @GetMapping("/operations/{operationId}/instances/{instanceId}")
    public ModelAndView operationInstance(@PathVariable String operationId, @PathVariable String instanceId) {
        log.debug("Instancia {} de la operación {}", instanceId, operationId);
        Instance operationInstance = operationService.findOperationInstance(operationId, instanceId);
        return new ModelAndView("pages/instance", "instance", operationInstance);
    }
}
