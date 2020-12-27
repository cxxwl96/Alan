package com.alan.common.config;

import com.alan.common.config.properties.ProjectProperties;
import com.alan.common.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * xss过滤拦截器
 * @author cxxwl96@sina.com
 * @date 2020/12/9
 */
@Configuration
public class XssFilterConfig {
    private static final int FILTER_ORDER = 1;

    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean(ProjectProperties properties) {
        ProjectProperties.Xxs propertiesXxs = properties.getXxs();
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
        registration.setFilter(new XssFilter());
        registration.setOrder(FILTER_ORDER);
        registration.setEnabled(propertiesXxs.isEnabled());
        registration.addUrlPatterns(propertiesXxs.getUrlPatterns().split(","));
        Map<String, String> initParameters = new HashMap<>(16);
        initParameters.put("excludes", propertiesXxs.getExcludes());
        registration.setInitParameters(initParameters);
        return registration;
    }
}
