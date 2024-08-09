package com.devrish.martcart.config;

import com.devrish.martcart.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean(AuthFilter authFilter) {
        final FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(authFilter);
        registrationBean.setOrder(1);

        // authorized authentication routes
        registrationBean.addUrlPatterns("/api/auth/logout");

        // all user routes are authorized
        registrationBean.addUrlPatterns("/api/user/*");

        // all cart routes are authorized
        registrationBean.addUrlPatterns("/api/cart/*");

        // all order routes are authorized
        registrationBean.addUrlPatterns("/api/order/*");

        // all other routes unauthorized

        return registrationBean;
    }

    // put other filters in order for convenience

}
