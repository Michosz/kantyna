package com.mLukasik.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{
	
	private BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
	
	@Autowired
	private DataSource ds;
	
	private String userQuery = "select Login, Haslo, czy_aktywny from uzytkownicy where Login=?";
	
	private String rolesQuery = "select u.Login, r.Rola from uzytkownicy u inner join role r on (u.id_roli = r.id_roli) where u.Login=?";
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
	     auth.jdbcAuthentication().usersByUsernameQuery(userQuery).authoritiesByUsernameQuery(rolesQuery)
	     .dataSource(ds).passwordEncoder(bcp);
	 }
	
	@Override
	protected void configure(HttpSecurity httpSec) throws Exception 
	{
		httpSec
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/main").permitAll()
		.antMatchers("/rejestracja").permitAll()
		.antMatchers("/parametry").hasRole("MANAGER")
		.antMatchers("/logowanie/**").permitAll()
		.antMatchers("/potrawa").permitAll()
		.antMatchers("/stoliki").permitAll()
		.antMatchers("/nowa-potrawa").permitAll()
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().csrf().disable()
		.formLogin()
		.loginPage("/logowanie").permitAll()
		.failureUrl("/logowanie?error=1")
		.defaultSuccessUrl("/welcome", true).usernameParameter("login")
		.passwordParameter("haslo")
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/logowanie")
		.deleteCookies("JSESSIONID")
		.invalidateHttpSession(true) 
		.and().exceptionHandling().accessDeniedPage("/welcome");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception 
	{
		web.ignoring()
		.antMatchers("/resources/**","/statics/**","/css/**","/js/**","/images/**");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
