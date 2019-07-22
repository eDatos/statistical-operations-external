'use strict';

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var cookies = decodedCookie.split(';');
    for (var i = 0; i <cookies.length; i++) {
        var cookie = cookies[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) == 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}

function getLang() {
    try {
        var lang = getCookie(CONFIGURATION.NAME_ATTRIBUTE_LANG);

        if (lang && CONFIGURATION.AVAILABLE_LANGS.indexOf(lang) != -1) {
            return lang;
        }
    }
    catch(e) {
        console.log('Ha habido un error al obtener el lenguaje en la cookie');
    }

    return CONFIGURATION.DEFAULT_LANG;
}

function getTranslatedText(traductions) {
    var texts = traductions.text;
    var lang = getLang();
    var defaultLangIndex = -1;
    if (Array.isArray(texts)) {
        for(var i=0; i<texts.length; i++) {
            if (texts[i].lang == lang) {
                return texts[i].value;
            }

            if (texts[i].lang == CONFIGURATION.DEFAULT_LANG) {
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

(function() {
    $('.dropwdown-toggle.language-flag').attr('data-lang', getLang());
    $('.dropwdown-toggle').on('click', function() {
        $(this).closest('.dropdown').toggleClass('active');
    })
})()