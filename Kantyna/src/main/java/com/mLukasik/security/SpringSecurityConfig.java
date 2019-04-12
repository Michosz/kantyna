package com.mLukasik.security;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


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
	
	
	/*@Configuration
    @Order(1)
    public class ApiSecurityAdapter extends WebSecurityConfigurerAdapter 
    {
		@Resource(name = "userService")
		private UserDetailsService userDetailsService;
		
		@Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception 
	    {
	        return super.authenticationManagerBean();
	    }

	    @Autowired
	    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception
	    {
	        auth.userDetailsService(userDetailsService)
	                .passwordEncoder(bcp);
	    }
	    
        @Override
        protected void configure(HttpSecurity http) throws Exception
        {*/
         //   http//.antMatcher("/oauth/**")
          //  .antMatcher("/api/**") //<= Security only available for /api/**
            //    .authorizeRequests()
            //    .antMatchers("/api/**").permitAll()
                	/*.antMatchers("/api/oauth/token").permitAll()
                	.antMatchers("/api/oauth/authorize").permitAll()
                	.antMatchers("/api/oauth/check_token").permitAll()
                	.antMatchers("/api/oauth/confirm_access").permitAll()
                	.antMatchers("/api/oauth/error").permitAll()
                	
                    .antMatchers("/api/komentarze/**").hasRole("KLIENT")
                    .antMatchers("/api/rejestracja/**").permitAll()
                    .antMatchers("/api/edytuj/**").permitAll()
                    .antMatchers("/api/parametry/**").permitAll()*/
                    //.antMatchers("/oauth/token").permitAll()
                	
                  //  .anyRequest().authenticated()
                //    .and().csrf().disable();
                    //.anonymous().disable();
     //   }

	   /* @Bean
	    public FilterRegistrationBean corsFilter() 
	    {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("*");
	        source.registerCorsConfiguration("/**", config);
	        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	        bean.setOrder(0);
	        return bean;
	    }*/
        
    //}
	
	/*@EnableWebSecurity
	@EnableGlobalMethodSecurity(securedEnabled = true)
    @Configuration
    public class WebSecurityAdapter extends WebSecurityConfigurerAdapter
    {*/
    	@Resource(name = "userService")
		private UserDetailsService userDetailsService;
    	
    	@Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception 
	    {
	        return super.authenticationManagerBean();
	    }

	    @Autowired
	    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception
	    {
	        auth.userDetailsService(userDetailsService)
	                .passwordEncoder(bcp);
	    }
    	
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
			.antMatchers("/api/**").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/main").permitAll()
			.antMatchers("/rejestracja").hasAnyRole("MANAGER", "ANONYMOUS")
			.antMatchers("/parametry").hasRole("MANAGER")
			.antMatchers("/logowanie/**").permitAll()
			//.antMatchers("/potrawa").permitAll()
			.antMatchers("/nowy-stolik").hasRole("MANAGER")
			.antMatchers("/stoliki").hasAnyRole("KLIENT", "MANAGER")
			.antMatchers("/zwolnij").hasRole("MANAGER")
			.antMatchers("/nowa-potrawa").hasRole("MANAGER")
			.antMatchers("/usunZmenu/**").hasRole("MANAGER")
			//.antMatchers("/komentarz/**").permitAll()
			.antMatchers("/dodajKom").hasRole("KLIENT")
			.antMatchers("/zamowienie").hasRole("KLIENT")
			.antMatchers("/zamowienia").hasAnyRole("MANAGER", "KLIENT")
			.antMatchers("/nowy-rodzaj").permitAll()
			.antMatchers("/a").permitAll()
			.antMatchers("/koszyk").hasRole("KLIENT")
			.antMatchers("/usunZkoszyka").hasRole("KLIENT")
			.antMatchers("/info").hasRole("KLIENT")
			.antMatchers("/konto").hasAnyRole("MANAGER", "KLIENT")
			.antMatchers("/listaUzytk").hasRole("MANAGER")
			//.antMatchers("/api/**").permitAll()
			.anyRequest().authenticated()
			.and().httpBasic()
			.and().csrf().disable()
			.formLogin()
			.loginPage("/logowanie").permitAll()
			.failureUrl("/logowanie?error=1")
			.defaultSuccessUrl("/", true).usernameParameter("login")
			.passwordParameter("haslo")
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/logowanie")
			//.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true) 
			.and().exceptionHandling().accessDeniedPage("/");
		}
		
		@Override
		public void configure(WebSecurity web) throws Exception 
		{
			web.ignoring()
			.antMatchers("/resources/**","/statics/**","/css/**","/js/**","/images/**");
		}
 //   }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}