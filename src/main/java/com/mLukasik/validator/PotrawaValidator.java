package com.mLukasik.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.mLukasik.model.Potrawa;

public class PotrawaValidator implements Validator
{
	boolean czyNazwaIstnieje = false;
	int czyPromocja = 0;
	
	public PotrawaValidator()
	{
		
	}
	
	public PotrawaValidator(boolean czyNazwaIstnieje)
	{
		this.czyNazwaIstnieje = czyNazwaIstnieje;
	}
	
	public PotrawaValidator(int czyPromocja)
	{
		this.czyPromocja = czyPromocja;
	}
	
	@Override
	public boolean supports(Class<?> c) 
	{
		return Potrawa.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		Potrawa potrawa = (Potrawa)obj;
		if(czyPromocja == 0)
        {
			String nazwaRegex = "[a-zA-Z][a-zA-Z\\s]{2,}";
			Pattern pattern = Pattern.compile(nazwaRegex);
			Matcher matcher = pattern.matcher(potrawa.getNazwa());
	        if(!matcher.matches()) 
	        {
	        	err.rejectValue("Nazwa", "error.ZlaNazwa");
	        }
	        if(czyNazwaIstnieje)
	        {
	        	err.rejectValue("Nazwa", "error.NazwaPotrawyJuzIstnieje");
	        }
	        if(potrawa.getCena() <= 0)
	        {
	        	err.rejectValue("Cena", "error.ZaMalaCena");
	        }
	        try
			{
				if(ImageIO.read(potrawa.getObrazek().getInputStream()) == null)
				{
					err.rejectValue("Obrazek", "error.ToNieObrazek");
				}
			} 
			catch (Exception e1) 
			{
			}
        }
        else
        {
        	if(potrawa.getCenaPromocyjna() <= 0)
        	{
        		err.rejectValue("cenaPromocyjna", "error.ZaMalaCena");
        	}
        }
	}
}
