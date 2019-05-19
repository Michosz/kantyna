package com.mLukasik.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mLukasik.model.Uzytkownik;
import com.mLukasik.repository.UzytkownikRepository;

public class SerializerWyjatkuOauth extends StdSerializer<WyjatekOauth> 
{
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UzytkownikRepository uzytkownikRepository;
	
    public SerializerWyjatkuOauth() 
    {
        super(WyjatekOauth.class);
    }

    @Override
    public void serialize(WyjatekOauth value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException 
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
        String jezyk = " ";
        jezyk = request.getParameter("lang");
        String login = " ";
        login = request.getParameter("username");
        List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
        uzytkownik = uzytkownikRepository.findByLogin(login);
        jsonGenerator.writeStartObject();
        if(value.getMessage().contains(messageSource.getMessage("page.logowanie.ZleDane", null, new Locale("en"))))
        {
        	//jsonGenerator.writeObjectField("errors", Arrays.asList(/*value.getOAuth2ErrorCode(), //zwraca kod, z reguly jest to bad request */ value.getMessage()));
        	jsonGenerator.writeObjectField("errors", messageSource.getMessage("page.logowanie.ZleDane", null, new Locale(jezyk)));
        }
        else if(value.getMessage().contains(messageSource.getMessage("page.logowanie.Ban", null, new Locale("en"))))
        {
          	jsonGenerator.writeObjectField("errors", messageSource.getMessage("page.logowanie.Ban", null, new Locale(jezyk)));
        	jsonGenerator.writeObjectField("komentarz", uzytkownik.get(0).getKomentarz());
        }
        jsonGenerator.writeNumberField("code", value.getHttpErrorCode());
        jsonGenerator.writeEndObject();
    }
}