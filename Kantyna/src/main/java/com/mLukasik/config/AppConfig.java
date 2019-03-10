package com.mLukasik.config;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableScheduling
public class AppConfig implements WebMvcConfigurer 
{
	   @Bean
	   public LocaleResolver localeResolver() 
	   {	
	       SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
	       sessionLocaleResolver.setDefaultLocale(new Locale("PL"));
	       return sessionLocaleResolver;
	   }
	   
	   @Bean
	   public LocaleChangeInterceptor localeChangeInterceptor() 
	   {
	       LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	       lci.setParamName("lang");
	       return lci;
	   }
	   
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) 
	   {
	       registry.addInterceptor(localeChangeInterceptor());
	   }
	   
	   @Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) 
	   {
	      registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
	            .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	   }
	   
	   @Bean
	   public MessageSource messageSource() 
	   {
	       ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	       messageSource.setBasename("classpath:messages");
	       messageSource.setDefaultEncoding("UTF-8");
	       return messageSource;
	   }
	   
	   @Bean
	   public LocalValidatorFactoryBean getValidator() 
	   {
	       LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	       bean.setValidationMessageSource(messageSource());
	       return bean;
	   }
}
