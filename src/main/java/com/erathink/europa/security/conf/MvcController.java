package com.erathink.europa.security.conf;

/**
 * Created by fengyun on 2017/12/17.
 */
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = { "/{url:(?!static|europa).*$}" })
public class MvcController {
    @RequestMapping(value = { "/**" })
    public String handleAll(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("not found");
        return null;
    }
}