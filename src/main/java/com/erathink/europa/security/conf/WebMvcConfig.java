package com.erathink.europa.security.conf;

/**
 * Created by fengyun on 2017/12/17.
 */
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.erathink.europa.commons.CoreConstants;
import com.erathink.europa.user.entity.UserBO;
import com.erathink.europa.user.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.erathink.europa.commons.ErrorEnum;
import com.erathink.europa.commons.Response;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/public/" };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Autowired
    ApplicationContext context;

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.CHINA);
        Locale.setDefault(Locale.CHINA);
        return slr;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages");
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // intercepter path
        addInterceptor.addPathPatterns("/**");
        // exclude paths
        String excludes = context.getEnvironment().getProperty("europa.web-url.excludes");
        // String excludes = ResourceUtils.getConfig("cbc.web-url.excludes");
        if (StringUtils.isNotBlank(excludes)) {
            String[] paths = excludes.split(",");
            addInterceptor.excludePathPatterns(paths);
        }
        super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String method = request.getMethod();
            if (HttpMethod.OPTIONS.matches(method)) {
                response.setStatus(HttpServletResponse.SC_OK);
                return true;
            }
            HttpSession session = request.getSession();
            String uri = request.getRequestURI();

            UserEntity user = (UserEntity) session.getAttribute(CoreConstants.SESSION_KEY_USER);
            LOGGER.debug("SecurityInterceptor! sessionId {} with mobile {} accessing URI {} with method {}",
                    session.getId(), user == null ? "NOT_LOGIN" : user.getUserName(), uri, method);
            //request from system user
            if (null != user) {
                return true;
            }
            if (HttpMethod.GET.matches(method)) {
                response.sendRedirect("/signin");
                return false;
            }

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(Response.wrap(ErrorEnum.COMM_NOT_LOGIN)));
            return false;
        }

    }
}

