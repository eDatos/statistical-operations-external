package es.gobcan.istac.statistical.operations.external.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.gobcan.istac.statistical.operations.external.config.ApplicationProperties;

@Controller()
public class DefaultController {

    private final Logger log = LoggerFactory.getLogger(DefaultController.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @RequestMapping(value = {"", "/index.html", "/**/{path:[^\\.]*}"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("pages/index");
        return modelAndView;
    }
}
