package es.gobcan.istac.statistical.operations.external.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("constants")
public final class Constants {

    public static final String SPRING_PROFILE_ENV = "env";
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    public static final String INTERNAL_CONFIG_ID = "INTERNAL";

    public static final String DEFAULT_LANG = "es";
    public static final String NAME_ATTRIBUTE_LANG = "lang";

    public static final List<String> AVAILABLE_LANGS = Arrays.asList(Constants.DEFAULT_LANG, "en");

    private Constants() {
    }
}