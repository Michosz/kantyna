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
	boolean czyHasloTakieSamo = false;
	
	public UzytkownikValidator()
	{
		
	}
	
	public UzytkownikValidator(boolean czyLoginIstnieje, boolean czyTelefonIstnieje)
	{
		this.czyLoginIstnieje = czyLoginIstnieje;
		this.czyTelefonIstnieje = czyTelefonIstnieje;
	}
	
	public UzytkownikValidator(boolean czyLoginIstnieje, boolean czyTelefonIstnieje, boolean czyHasloTakieSamo)
	{
		this.czyLoginIstnieje = czyLoginIstnieje;
		this.czyTelefonIstnieje = czyTelefonIstnieje;
		this.czyHasloTakieSamo = czyHasloTakieSamo;
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
        if(!(matcher.matches())) 
        {
        	if(uzytkownik.getRolaa() == null || uzytkownik.getRolaa().equals("KLIENT"))
        	{
        		err.rejectValue("Login", "error.zlyLogin");
        	}
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
		if(uzytkownik.getHaslo().length() < 3)
		{
			err.rejectValue("Haslo", "error.ZaKrotkieHaslo");
		}
	}
	
	public void validate2(Object obj, Errors err) 
	{
		Uzytkownik uzytkownik = (Uzytkownik)obj;
		String telefonRegex = "[0-9]{3}-[0-9]{3}-[0-9]{3}";
		if(uzytkownik.getTelefon() == null)
		{
			err.rejectValue("Telefon", "error.zlyFormat");
		}
		else if(uzytkownik.getTelefon() != null)
        {
    		Pattern pattern = Pattern.compile(telefonRegex);
    	    Matcher matcher = pattern.matcher(uzytkownik.getTelefon());
    	    if(!matcher.matches())
        	{
    	    	err.rejectValue("Telefon", "error.zlyFormat");
        	}
        }
        if(czyTelefonIstnieje)
        {
        	err.rejectValue("Telefon", "error.JuzIstniejeTelefon");
        }
        if(uzytkownik.getHaslo().length() == 0)
        {
        	
        }
        else if(uzytkownik.getHaslo() != null && uzytkownik.getHaslo().length() < 3)
		{
			err.rejectValue("Haslo", "error.ZaKrotkieHaslo");
		}
        if(uzytkownik.getHaslo() != null && czyHasloTakieSamo == true)
        {
        	err.rejectValue("Haslo", "error.musiBycInneHaslo");
        }
	}
	
	public void walidacjaKomentarza(Object obj, Errors err)
	{
		Uzytkownik uzytkownik = (Uzytkownik)obj;
		if(uzytkownik.getKomentarz().length() < 3)
		{
			err.rejectValue("Komentarz", "error.ZaKrotkiKomentarz");
		}
	}
}