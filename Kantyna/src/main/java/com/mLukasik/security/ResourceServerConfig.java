package com.mLukasik.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@Order(3)
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter 
{
	private static final String RESOURCE_ID = "resource_id";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) 
	{
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception 
	{
        http.
        	antMatcher("/api/**")
        	//.authorizeRequests().antMatchers("/api/parametry").authenticated().and().httpBasic().and().csrf().disable()
               // antMatcher("/api/**")
        		//.anonymous().disable()
                .authorizeRequests()
            	.antMatchers("/api/oauth/token").permitAll()
            	.antMatchers("/api/oauth/authorize").permitAll()
            	.antMatchers("/api/oauth/check_token").permitAll()
            	.antMatchers("/api/oauth/confirm_access").permitAll()
            	.antMatchers("/api/oauth/error").permitAll()
            	.antMatchers("/api/**").permitAll()
               /* .antMatchers("/users/**").hasRole("KLIENT")
                .antMatchers("/api/menu").hasRole("KLIENT")
                .antMatchers("/api/komentarze/**").hasRole("KLIENT")
                .antMatchers("/api/parametry/**").hasRole("KLIENT")*/
        		.and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
}