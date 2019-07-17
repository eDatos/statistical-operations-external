package es.gobcan.istac.statistical.operations.external.web;

import java.lang.invoke.MethodHandles;

import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.gobcan.istac.statistical.operations.external.service.OperationService;

@Controller
public class OperationController {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private OperationService operationService;

    @RequestMapping(value = {"", "/index.html"})
    public ModelAndView index() {
        log.debug("Operaciones");
        ModelAndView modelAndView = new ModelAndView("pages/operations");
        return modelAndView;
    }

    @GetMapping("/operations/{operationId}")
    public ModelAndView operation(@PathVariable String operationId) {
        log.debug("Operación {}", operationId);
        Operation operation = operationService.findOperation(operationId);
        ModelAndView modelAndView = new ModelAndView("pages/operation");

        modelAndView.addObject("operation", operation);
        return modelAndView;
    }
}
