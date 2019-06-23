package com.mLukasik.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.mLukasik.model.RodzajPotrawy;

public class RodzajPotrawyValidator implements Validator
{
	boolean czyRodzajIstnieje = false;
	
	public RodzajPotrawyValidator()
	{
		
	}
	
	public RodzajPotrawyValidator(boolean czyRodzajIstnieje)
	{
		this.czyRodzajIstnieje = czyRodzajIstnieje;
	}
	
	@Override
	public boolean supports(Class<?> c) 
	{
		return RodzajPotrawy.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		RodzajPotrawy rodzajPot = (RodzajPotrawy)obj;
		String rodzajRegex = "[a-zA-z][a-zA-z\\s]{2,}";
		Pattern pattern = Pattern.compile(rodzajRegex);
        Matcher matcher = pattern.matcher(rodzajPot.getRodzaj());
        if(!(matcher.matches())) 
        {
        	err.rejectValue("Rodzaj", "error.ZlaNazwa");
        }
        if(czyRodzajIstnieje)
        {
        	err.rejectValue("Rodzaj", "error.JuzIstniejeRodzaj");
        }
	}
}