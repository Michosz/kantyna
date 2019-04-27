package com.mLukasik.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter 
{
//zmienic client_id, secret i moze cos jeszcze, zeby bylo oryginalnie
	static final String CLIEN_ID = "kantyna-client";
	static final String CLIENT_SECRET = "$2a$10$YT/pYNaBrv6VPtcJS5rfH.M2ykheBNHkxoMVdbMiej3kwr8MeOc.e"; //kantyna-secret
	static final String GRANT_TYPE_PASSWORD = "password";
	static final String AUTHORIZATION_CODE = "authorization_code";
	static final String REFRESH_TOKEN = "refresh_token";
	static final String IMPLICIT = "implicit";
	static final String SCOPE_READ = "read";
	static final String SCOPE_WRITE = "write";
	static final String TRUST = "trust";
	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60; //poprzednio bylo 1*60*60
	static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 3*60; //poprozednio bylo 6*60*60

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public JwtAccessTokenConverter accessTokenConverter()
	{
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("as466gf");
		return converter;
	}

	@Bean
	public TokenStore tokenStore()
	{
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception 
	{
		configurer
			.inMemory()
			.withClient(CLIEN_ID)
			.secret(CLIENT_SECRET)
			.authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT )
			.scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
			.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS).
			refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception 
	{
		endpoints.tokenStore(tokenStore())
			.pathMapping("/oauth/authorize", "/api/oauth/authorize")
			.pathMapping("/oauth/check_token", "/api/oauth/check_token")
			.pathMapping("/oauth/confirm_access", "/api/oauth/confirm_access")
			.pathMapping("/oauth/error", "/api/oauth/error")
			.pathMapping("/oauth/token", "/api/oauth/token")
			.authenticationManager(authenticationManager)
			.accessTokenConverter(accessTokenConverter());
	}
}