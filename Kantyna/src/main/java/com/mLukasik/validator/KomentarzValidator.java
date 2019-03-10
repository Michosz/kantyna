package com.mLukasik.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.mLukasik.model.Komentarz;
public class KomentarzValidator implements Validator
{
	public KomentarzValidator()
	{
		
	}
	
	@Override
	public boolean supports(Class<?> c) 
	{
		return Komentarz.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		Komentarz komentarz = (Komentarz)obj;
        if(komentarz.getJakaOcena() == null || komentarz.getJakaOcena() == "")
        {
        	err.rejectValue("Ocena", "error.PustaOcena");
        }
        if(komentarz.getKomentarz().length() < 3)
        {
         	err.rejectValue("Komentarz", "error.ZaKrotkiKomentarz");
        }
	}
}
