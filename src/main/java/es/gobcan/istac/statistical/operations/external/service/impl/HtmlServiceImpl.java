package es.gobcan.istac.statistical.operations.external.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.statistical.operations.external.annotation.NotLogging;
import es.gobcan.istac.statistical.operations.external.config.ApplicationProperties;
import es.gobcan.istac.statistical.operations.external.service.HtmlService;

@Service("htmlService")
@NotLogging
public class HtmlServiceImpl implements HtmlService {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Override
    public String getHeaderHtml() {
        try {
            return getHtml(applicationProperties.getMetadata().getNavbarTemplateUrl());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getFooterHtml() {
        try {
            return getHtml(applicationProperties.getMetadata().getFooterTemplateUrl());
        } catch (Exception e) {
            return null;
        }
    }

    private String getHtml(String urlToRead) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(urlToRead, String.class);
    }

}
