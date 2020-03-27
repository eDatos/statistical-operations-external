package es.gobcan.istac.statistical.operations.external.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.SecondarySubjectAreas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.statistical.operations.external.annotation.NotLogging;
import es.gobcan.istac.statistical.operations.external.service.HtmlService;
import es.gobcan.istac.statistical.operations.external.service.MetadataService;
import es.gobcan.istac.statistical.operations.external.web.util.LanguageUtil;

@Service
@NotLogging
public class HtmlServiceImpl implements HtmlService {

    private final Logger log = LoggerFactory.getLogger(HtmlServiceImpl.class);

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getHeaderHtml() {
        Locale locale = LocaleContextHolder.getLocale();
        String appName = messageSource.getMessage("global.title", null, locale);
        return getHtml(String.format("%s?appName=%s", metadataService.getNavbarUrl(), appName));
    }

    @Override
    public String getFooterHtml() {
        return getHtml(metadataService.getFooterUrl());
    }

    @Override
    public String getMetaKeywords(Operation operation) {
        List<String> keywords = new ArrayList<>();
        keywords.add(operation.getId());
        keywords.add(languageUtil.getTranslation(operation.getSubjectArea().getName()));

        if (operation.getAcronym() != null) {
            keywords.add(languageUtil.getTranslation(operation.getAcronym()));
        }

        SecondarySubjectAreas secondarySubjectAreas = operation.getSecondarySubjectAreas();
        if (secondarySubjectAreas != null) {
            for (Resource resource : secondarySubjectAreas.getSecondarySubjectAreas()) {
                keywords.add(languageUtil.getTranslation(resource.getName()));
            }
        }
        return String.join(",", keywords);
    }

    @Override
    public String getMetaKeywords(Instance instance) {
        List<String> keywords = new ArrayList<>();
        keywords.add(instance.getId());
        keywords.add(languageUtil.getTranslation(instance.getStatisticalOperation().getName()));

        if (instance.getAcronym() != null) {
            keywords.add(languageUtil.getTranslation(instance.getAcronym()));
        }
        return String.join(",", keywords);
    }

    private String getHtml(String urlToRead) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(urlToRead, String.class);
        } catch (Exception e) {
            log.error("Error when obtaining HTML from URL: {}", urlToRead);
            return null;
        }
    }

}
