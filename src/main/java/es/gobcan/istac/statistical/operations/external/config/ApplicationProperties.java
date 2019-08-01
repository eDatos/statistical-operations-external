package es.gobcan.istac.statistical.operations.external.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties are configured in the application.yml file.
 */
@Component("applicationProperties")
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Metadata metadata = new Metadata();
    private final CategoriesSchemes categoriesSchemes = new CategoriesSchemes();

    public Metadata getMetadata() {
        return metadata;
    }

    public CategoriesSchemes getCategoriesSchemes() {
        return categoriesSchemes;
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

    public static class CategoriesSchemes {

        private String schemePrefix;
        private List<Category> categories = new ArrayList<>();

        public String getSchemePrefix() {
            return schemePrefix;
        }

        public void setSchemePrefix(String schemePrefix) {
            this.schemePrefix = schemePrefix;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public static class Category {

            private String key;
            private String nestedId;
            private String color;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getNestedId() {
                return nestedId;
            }

            public void setNestedId(String nestedId) {
                this.nestedId = nestedId;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

        }
    }
}