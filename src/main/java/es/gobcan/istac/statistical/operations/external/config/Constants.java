package es.gobcan.istac.statistical.operations.external.config;

import java.util.Arrays;
import java.util.List;

public final class Constants {

    public static final String SPRING_PROFILE_ENV = "env";
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    public static final String INTERNAL_CONFIG_ID = "INTERNAL";

    public static final List<String> LOCALES = Arrays.asList("en", "es");

    private Constants() {
    }
}