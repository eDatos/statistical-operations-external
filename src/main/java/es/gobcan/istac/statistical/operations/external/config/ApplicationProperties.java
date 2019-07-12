package es.gobcan.istac.statistical.operations.external.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Metadata metadata = new Metadata();

    public Metadata getMetadata() {
        return metadata;
    }

    public static class Metadata {

        private String operationsApi;
        private String navbarTemplateUrl;
        private String footerTemplateUrl;

        public String getOperationsApi() {
            return operationsApi;
        }

        public void setOperationsApi(String operationsApi) {
            this.operationsApi = operationsApi;
        }

        public String getNavbarTemplateUrl() {
            return navbarTemplateUrl;
        }

        public void setNavbarTemplateUrl(String navbarTemplateUrl) {
            this.navbarTemplateUrl = navbarTemplateUrl;
        }

        public String getFooterTemplateUrl() {
            return footerTemplateUrl;
        }

        public void setFooterTemplateUrl(String footerTemplateUrl) {
            this.footerTemplateUrl = footerTemplateUrl;
        }
    }
}