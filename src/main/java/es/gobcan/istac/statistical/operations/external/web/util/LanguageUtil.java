package es.gobcan.istac.statistical.operations.external.web.util;

import java.util.List;

import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.springframework.stereotype.Component;

@Component("languageUtil")
public class LanguageUtil {

    private static final String defaultLang = "es";

    public String getTraduction(InternationalString traductions) {
        List<LocalisedString> textTraductions = traductions.getTexts();
        for (LocalisedString traduction : textTraductions) {

            // TODO: Cambiar defaultLang por una cookie tal vez
            if (traduction.getLang().equals(defaultLang)) {
                return traduction.getValue();
            }
        }

        return textTraductions.get(0).getValue();
    }
}
