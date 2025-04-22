package com.winter.app.message;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import ch.qos.logback.core.util.Duration;

@Configuration
public class MessageBean {
	
	@Bean
	LocaleResolver localeResolver() {
		//1. Session에 Locale정보 저장
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.KOREAN);
		
		//2. Cookie에 Locale 정보 저장
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(Locale.KOREAN);
		//cookieLocaleResolver.setCookieMaxAge();
		
		return resolver;
	}
	
	//
	@Bean
	LocaleChangeInterceptor changeInterceptor() {
		LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
		changeInterceptor.setParamName("lang");
		//url?lang=en , url?lang=ko, url?lang=jp
		
		return changeInterceptor;
	}

}
