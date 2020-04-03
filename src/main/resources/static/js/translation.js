'use strict';

function getLang() {
    var lang = CONFIGURATION.CURRENT_LANG;
    if (lang && CONFIGURATION.AVAILABLE_LANGS.indexOf(lang) !== -1) {
        return lang;
    }
    return CONFIGURATION.DEFAULT_LANG;
}

function getTranslatedText(translations) {
    var texts = translations.text;
    var lang = getLang();
    var defaultLangIndex = -1;
    if (Array.isArray(texts)) {
        for(var i=0; i<texts.length; i++) {
            if (texts[i].lang === lang) {
                return texts[i].value;
            }

            if (texts[i].lang === CONFIGURATION.DEFAULT_LANG) {
                defaultLangIndex = i;
            }
        }
    }
    else {
        console.error('No se ha proporcionado un array');
    }

    if (defaultLangIndex === -1) {
        console.error('Sin traducción');
        return '';
    }
    return texts[defaultLangIndex].value;
}
