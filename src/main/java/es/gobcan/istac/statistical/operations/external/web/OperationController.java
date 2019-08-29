package es.gobcan.istac.statistical.operations.external.web;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

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

import es.gobcan.istac.statistical.operations.external.config.ApplicationProperties;
import es.gobcan.istac.statistical.operations.external.config.ApplicationProperties.CategoriesSchemes.Category;
import es.gobcan.istac.statistical.operations.external.service.HtmlService;
import es.gobcan.istac.statistical.operations.external.service.OperationService;

@Controller
public class OperationController {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private OperationService operationService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private HtmlService htmlService;

    @GetMapping(value = {"", "/index.html"})
    public ModelAndView index() {
        log.debug("Operaciones");

        ModelAndView model = new ModelAndView("pages/operations");
        model.addObject("headerHtml", htmlService.getHeaderHtml());
        model.addObject("footerHtml", htmlService.getFooterHtml());
        return model;
    }

    @GetMapping("/operations/{operationId}")
    public ModelAndView operation(@PathVariable String operationId) {
        log.debug("Operación {}", operationId);
        Operation operation = operationService.findOperation(operationId);
        Instances operationInstances = operationService.findOperationInstances(operationId);

        ModelAndView model = new ModelAndView("pages/operation");
        model.addObject("operation", operation);
        model.addObject("instances", operationInstances);
        model.addObject("keywords", htmlService.getMetaKeywords(operation));
        model.addObject("headerHtml", htmlService.getHeaderHtml());
        model.addObject("footerHtml", htmlService.getFooterHtml());
        return model;
    }

    @GetMapping("/operations/subject-area/{subjectNestedId:.+}")
    public ModelAndView subjectOperations(@PathVariable String subjectNestedId) {
        log.debug("Operaciones de la área temática con nestedId: {}", subjectNestedId);
        Operations operations = operationService.findBySubjectArea(subjectNestedId);

        List<Category> categories = applicationProperties.getCategoriesSchemes().getCategories();
        Optional<Category> category = categories.stream().filter(c -> c.getNestedId().equals(subjectNestedId)).findFirst();

        ModelAndView model = new ModelAndView("pages/subject-area-operations", "operations", operations);
        model.addObject("category", category.isPresent() ? category.get() : null);
        model.addObject("headerHtml", htmlService.getHeaderHtml());
        model.addObject("footerHtml", htmlService.getFooterHtml());
        return model;
    }

    @GetMapping("/operations/{operationId}/instances/{instanceId}")
    public ModelAndView operationInstance(@PathVariable String operationId, @PathVariable String instanceId) {
        log.debug("Instancia {} de la operación {}", instanceId, operationId);
        Instance operationInstance = operationService.findOperationInstance(operationId, instanceId);

        ModelAndView model = new ModelAndView("pages/instance");
        model.addObject("instance", operationInstance);
        model.addObject("keywords", htmlService.getMetaKeywords(operationInstance));
        model.addObject("headerHtml", htmlService.getHeaderHtml());
        model.addObject("footerHtml", htmlService.getFooterHtml());
        return model;
    }
}
