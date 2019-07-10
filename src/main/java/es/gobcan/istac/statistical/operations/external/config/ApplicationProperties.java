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

        private String endpoint;
        private String installationType;
        private String navbarPathKey;
        private String footerPathKey;

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getInstallationType() {
            return installationType;
        }

        public void setInstallationType(String installationType) {
            this.installationType = installationType;
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
    }
}