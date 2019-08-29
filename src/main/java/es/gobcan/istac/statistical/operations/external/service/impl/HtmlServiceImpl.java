package es.gobcan.istac.statistical.operations.external.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.SecondarySubjectAreas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.gobcan.istac.statistical.operations.external.annotation.NotLogging;
import es.gobcan.istac.statistical.operations.external.service.HtmlService;
import es.gobcan.istac.statistical.operations.external.service.MetadataService;
import es.gobcan.istac.statistical.operations.external.web.util.LanguageUtil;

@Service("htmlService")
@NotLogging
public class HtmlServiceImpl implements HtmlService {

    private final Logger logs = LoggerFactory.getLogger(HtmlServiceImpl.class);

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private LanguageUtil languageUtil;

    @Override
    public String getHeaderHtml() {
        return getHtml(metadataService.getNavbarUrl());
    }

    @Override
    public String getFooterHtml() {
        return getHtml(metadataService.getFooterUrl());
    }

    @Override
    public String getMetaKeywords(Operation operation) {
        List<String> keywords = new ArrayList<>();
        keywords.add(languageUtil.getTraduction(operation.getSubjectArea().getName()));

        if (operation.getAcronym() != null) {
            keywords.add(languageUtil.getTraduction(operation.getAcronym()));
        }

        SecondarySubjectAreas secondarySubjectAreas = operation.getSecondarySubjectAreas();
        if (secondarySubjectAreas != null) {
            for (Resource resource : secondarySubjectAreas.getSecondarySubjectAreas()) {
                keywords.add(languageUtil.getTraduction(resource.getName()));
            }
        }
        return String.join(",", keywords);
    }

    @Override
    public String getMetaKeywords(Instance instance) {
        List<String> keywords = new ArrayList<>();

        keywords.add(languageUtil.getTraduction(instance.getStatisticalOperation().getName()));

        return String.join(",", keywords);
    }

    private String getHtml(String urlToRead) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(urlToRead, String.class);
        } catch (Exception e) {
            logs.error("Error al obtener el HTML de la dirección: {}", urlToRead);
            return null;
        }
    }

}
