package com.mLukasik.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.mLukasik.model.Uzytkownik;
import com.mLukasik.repository.UzytkownikRepository;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler 
{
	private UzytkownikRepository uzytkownikRepository;
	
	@Override
	public void onAuthenticationFailure (HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException 
	{
		String login = "";
		login = request.getParameter("login");
		if(uzytkownikRepository == null)
		{
		    ServletContext servletContext = request.getServletContext();
		    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    uzytkownikRepository = webApplicationContext.getBean(UzytkownikRepository.class);
		}
		List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
		uzytkownik = uzytkownikRepository.findByLogin(login);        
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		if(uzytkownik.size() > 0 && uzytkownik.get(0).getCzyAktywny() == false)
		{
			HttpSession session = request.getSession(false);
			session.setAttribute("komentarz", uzytkownik.get(0).getKomentarz());
		    redirectStrategy.sendRedirect(request, response, "/logowanie?error=1");
		}
		else
		{
			 redirectStrategy.sendRedirect(request, response, "/logowanie?error=2");
		}
	}
}