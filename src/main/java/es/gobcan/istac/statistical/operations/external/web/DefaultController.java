package es.gobcan.istac.statistical.operations.external.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.gobcan.istac.statistical.operations.external.config.ApplicationProperties;

@Controller("defaultController")
public class DefaultController {

    private final Logger log = LoggerFactory.getLogger(DefaultController.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @RequestMapping(value = {"", "/index.html", "/**/{path:[^\\.]*}"})
    @SuppressWarnings("unchecked")
    public ModelAndView index() throws Exception {
        ModelAndView modelAndView = new ModelAndView("pages/index");
        return modelAndView;
    }

    public static String getHeaderHTML() throws Exception {
        return getHTML("http://estadisticas.arte-consultores.com/sie/external-static/navbar/navbar.html");
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

}
