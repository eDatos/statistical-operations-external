package es.gobcan.istac.statistical.operations.external.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties are configured in the application.yml file.
 */
@Component("applicationProperties")
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Metadata metadata = new Metadata();

    public Metadata getMetadata() {
        return metadata;
    }

    public static class Metadata {

        private String endpoint;
        private String navbarPathKey;
        private String footerPathKey;
        private String operationsApiKey;

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getNavbarPathKey() {
            return navbarPathKey;
        }

        public void setNavbarPathKey(String navbarPathKey) {
            this.navbarPathKey = navbarPathKey;
        }

        public String getFooterPathKey() {
            return footerPathKey;
        }

        public void setFooterPathKey(String footerPathKey) {
            this.footerPathKey = footerPathKey;
        }

        public String getOperationsApiKey() {
            return operationsApiKey;
        }

        public void setOperationsApiKey(String operationsApiKey) {
            this.operationsApiKey = operationsApiKey;
        }

    }
}