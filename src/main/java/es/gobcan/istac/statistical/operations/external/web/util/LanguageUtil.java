package es.gobcan.istac.statistical.operations.external.web.util;

import java.util.List;
import java.util.Locale;

import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import es.gobcan.istac.statistical.operations.external.config.Constants;

@Component("languageUtil")
public class LanguageUtil {

    public String getTranslation(InternationalString translations) {
        Locale locale = LocaleContextHolder.getLocale();

        List<LocalisedString> textTranslations = translations.getTexts();
        int i = 0;
        int defaultLangIndex = -1;

        for (LocalisedString translation : textTranslations) {

            if (translation.getLang().equals(locale.toString())) {
                return translation.getValue();
            }

            if (translation.getLang().equals(Constants.DEFAULT_LANG)) {
                defaultLangIndex = i;
            }

            i++;
        }

        if (defaultLangIndex == -1) {
            return "No translation";
        }
        return textTranslations.get(defaultLangIndex).getValue();
    }
}
