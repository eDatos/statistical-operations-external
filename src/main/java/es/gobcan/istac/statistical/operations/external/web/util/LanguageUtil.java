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

    public String getTraduction(InternationalString traductions) {
        Locale locale = LocaleContextHolder.getLocale();

        List<LocalisedString> textTraductions = traductions.getTexts();
        int i = 0;
        int defaultLangIndex = -1;

        for (LocalisedString traduction : textTraductions) {

            if (traduction.getLang().equals(locale.toString())) {
                return traduction.getValue();
            }

            if (traduction.getLang().equals(Constants.DEFAULT_LANG)) {
                defaultLangIndex = i;
            }

            i++;
        }

        if (defaultLangIndex == -1) {
            return "No traduction";
        }
        return textTraductions.get(defaultLangIndex).getValue();
    }
}
