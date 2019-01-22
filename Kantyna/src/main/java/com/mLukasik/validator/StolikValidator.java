package com.mLukasik.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.mLukasik.model.Stolik;

public class StolikValidator implements Validator
{
	boolean czyNazwaIstnieje = false;
	
	public StolikValidator()
	{
		
	}
	
	public StolikValidator(boolean czyNazwaIstnieje)
	{
		this.czyNazwaIstnieje = czyNazwaIstnieje;
	}
	
	@Override
	public boolean supports(Class<?> c) 
	{
		return Stolik.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		Stolik stolik = (Stolik)obj;
		String nazwaRegex = "^[a-zA-Z]{3,}$";
		Pattern pattern = Pattern.compile(nazwaRegex);
        Matcher matcher = pattern.matcher(stolik.getNazwa());
        if(!matcher.matches()) 
        {
        	err.rejectValue("Nazwa", "error.ZlaNazwa");
        }
        if(czyNazwaIstnieje)
        {
        	err.rejectValue("Nazwa", "error.NazwaStolikaJuzIstnieje");
        }
        if(stolik.getIloscMiejsc() <= 0)
        {
        	err.rejectValue("iloscMiejsc", "error.ZaMalaIloscMiejsc");
        }
	}
}
