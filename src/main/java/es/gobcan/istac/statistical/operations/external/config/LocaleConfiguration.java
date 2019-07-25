package es.gobcan.istac.statistical.operations.external.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class LocaleConfiguration extends WebMvcConfigurerAdapter {

    public class SmartCookieLocaleResolver extends CookieLocaleResolver {

        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            Locale locale = (Locale) request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME);
            return (locale != null && Constants.AVAILABLE_LANGS.contains(locale.toString())) ? locale : Locale.getDefault();
        }
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        SmartCookieLocaleResolver r = new SmartCookieLocaleResolver();
        r.setDefaultLocale(new Locale(Constants.DEFAULT_LANG));
        r.setCookieName(Constants.NAME_ATTRIBUTE_LANG);
        r.setCookieMaxAge(86400); // 24 * 60 * 60
        r.setCookieHttpOnly(false);
        return r;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(Constants.NAME_ATTRIBUTE_LANG);
        registry.addInterceptor(localeChangeInterceptor);
    }
}
