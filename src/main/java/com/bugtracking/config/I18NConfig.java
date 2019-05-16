package com.bugtracking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class I18NConfig {
    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }
}
