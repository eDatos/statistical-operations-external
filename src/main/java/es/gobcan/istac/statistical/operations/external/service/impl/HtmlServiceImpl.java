package es.gobcan.istac.statistical.operations.external.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.statistical.operations.external.annotation.NotLogging;
import es.gobcan.istac.statistical.operations.external.config.ApplicationProperties;
import es.gobcan.istac.statistical.operations.external.service.HtmlService;

@Service("htmlService")
@NotLogging
public class HtmlServiceImpl implements HtmlService {

    private final Logger logs = LoggerFactory.getLogger(HtmlServiceImpl.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public String getHeaderHtml() {
        return getHtml(applicationProperties.getMetadata().getNavbarTemplateUrl());
    }

    @Override
    public String getFooterHtml() {
        return getHtml(applicationProperties.getMetadata().getFooterTemplateUrl());
    }

    private String getHtml(String urlToRead) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(urlToRead, String.class);
        } catch (Exception e) {
            logs.error("Error al obtener el HTML de la direasón: {}", urlToRead);
            return null;
        }
    }

}
