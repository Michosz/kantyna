package com.mLukasik.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.mLukasik.model.Koszyk;

public class KoszykValidator implements Validator
{
	public KoszykValidator()
	{
		
	}
		
	@Override
	public boolean supports(Class<?> c) 
	{
		return Koszyk.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		Koszyk koszyk = (Koszyk)obj;
		if(koszyk.getIlosc() <= 0)
		{
			err.rejectValue("ilosc", "error.Ilosc");
		}
	}
}
