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
            	//.antMatchers("/api/**").permitAll()
                .antMatchers("/api/parametry/**").hasRole("KLIENT")
                .antMatchers("/api/konto/**").hasAnyRole("KLIENT", "MANAGER")
                .antMatchers("/api/edytuj/**").hasRole("KLIENT")
                .antMatchers("/api/komentarze/**").permitAll()
                .antMatchers("/api/stoliki/**").hasRole("KLIENT")
                .antMatchers("/api/zamowieniaAktualne/**").hasRole("KLIENT")
                .antMatchers("/api/zamowieniaZrealizowane/**").hasRole("KLIENT")
                .antMatchers("/api/zamowienie/**").hasRole("KLIENT")
                .antMatchers("/api/zlozZamowienie/**").hasRole("KLIENT")
                .antMatchers("/api/koszyk/**").hasRole("KLIENT")
                .antMatchers("/api/dodajDoKoszyka/**").hasRole("KLIENT") 
                .antMatchers("/api/usunZKoszyka/**").hasRole("KLIENT")
                .antMatchers("/api/dodajDoKoszyka/**").hasRole("KLIENT")
                .antMatchers("/api/usunWszystko/**").hasRole("KLIENT")
                .antMatchers("/api/dodajKomentarz/**").hasRole("KLIENT")
                .antMatchers("/api/kluczPubliczny").permitAll()//.hasRole("KLIENT")
                .antMatchers("/api/zaplac/**").hasRole("KLIENT")
                .antMatchers("/api/rejestracja/**").permitAll()
                .antMatchers("/api/rodzaje/**").permitAll()
                .antMatchers("/api/filtrowanieMenu/**").permitAll()
                .antMatchers("/api/menu/**").permitAll()
        		.and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
}