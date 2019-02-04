package com.mLukasik.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.mLukasik.model.Uzytkownik;

public class UzytkownikValidator implements Validator
{
	boolean czyLoginIstnieje = false;
	boolean czyTelefonIstnieje = false;
	
	public UzytkownikValidator()
	{
		
	}
	
	public UzytkownikValidator(boolean czyLoginIstnieje, boolean czyTelefonIstnieje)
	{
		this.czyLoginIstnieje = czyLoginIstnieje;
		this.czyTelefonIstnieje = czyTelefonIstnieje;
	}
	
	@Override
	public boolean supports(Class<?> c) 
	{
		return Uzytkownik.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		Uzytkownik uzytkownik = (Uzytkownik)obj;
		String loginRegex = "[1-9][0-9]{5}@edu.p.lodz.pl";
		String telefonRegex = "[0-9]{3}-[0-9]{3}-[0-9]{3}";
		Pattern pattern = Pattern.compile(loginRegex);
        Matcher matcher = pattern.matcher(uzytkownik.getLogin());
        if(!(matcher.matches()) & uzytkownik.getRolaa() != null) 
        {
        	err.rejectValue("Login", "error.zlyLogin");
        }
        pattern = Pattern.compile(telefonRegex);
        matcher = pattern.matcher(uzytkownik.getTelefon());
        if(!matcher.matches())
        {
        	err.rejectValue("Telefon", "error.zlyFormat");
        }
        if(czyLoginIstnieje)
        {
        	err.rejectValue("Login", "error.JuzIstniejeLogin");
        }
        if(czyTelefonIstnieje)
        {
        	err.rejectValue("Telefon", "error.JuzIstniejeTelefon");
        }
		if(uzytkownik.getImie().length() < 2)
		{
			err.rejectValue("Imie", "error.ZaKrotkieImie");
		}
		if(uzytkownik.getNazwisko().length() < 2)
		{
			err.rejectValue("Nazwisko", "error.ZaKrotkieNazwisko");
		}
		if(uzytkownik.getHaslo().length() < 2)
		{
			err.rejectValue("Haslo", "error.ZaKrotkieHaslo");
		}
	}
}