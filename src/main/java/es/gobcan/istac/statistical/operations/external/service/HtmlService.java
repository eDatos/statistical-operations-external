package es.gobcan.istac.statistical.operations.external.service;

import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;

public interface HtmlService {

    public String getHeaderHtml();

    public String getFooterHtml();

    public String getMetaKeywords(Operation operation);

    public String getMetaKeywords(Instance instance);
}
