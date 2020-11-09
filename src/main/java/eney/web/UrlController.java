/*package com.eney.portal.web;

import com.eney.portal.service.UrlService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Controller
public class UrlController {

    @Autowired
    UrlService urlService;

    @RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
    public void url(@PathVariable  String id, HttpServletResponse resp,HttpServletRequest request) throws IOException {
        final String url = urlService.findUrlByShortUrl(id);
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        System.out.println(urlPathHelper.getRequestUri(request));

        if (url != null) {
            resp.addHeader("Location", url);
            resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
*/