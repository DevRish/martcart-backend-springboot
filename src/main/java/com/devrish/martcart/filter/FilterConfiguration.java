package com.devrish.martcart.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean() {
        final FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/api/user"); // will modify later to add other protected routes

        return registrationBean;
    }

}
