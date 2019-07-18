'use strict';
var DEFAULT_LANG = 'es';
var LANG_COOKIE_NAME = 'lang';
var ALLOWED_LANGS = [DEFAULT_LANG, 'en'];

var OPERATIONS_API_URL = 'https://www3.gobiernodecanarias.org/istac/api/operations/v1.0/operations.json';

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
        var lang = getCookie(LANG_COOKIE_NAME);

        if (lang && ALLOWED_LANGS.indexOf(lang) != -1) {
            return lang;
        }
    }
    catch(e) {
        console.log('Ha habido un error al obtener el lenguaje en la cookie');
    }

    return DEFAULT_LANG;
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

            if (texts[i].lang == DEFAULT_LANG) {
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