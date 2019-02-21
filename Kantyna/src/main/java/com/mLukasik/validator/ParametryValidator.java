package com.mLukasik.validator;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.mLukasik.model.Parametry;

public class ParametryValidator implements Validator
{
	@Override
	public boolean supports(Class<?> c) 
	{
		return Parametry.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		boolean zlyFormatGodzinyO = false;
		boolean zlyFormatGodzinyZ = false;
		boolean zlyFormatZwalniania = false;
		Parametry parametry = (Parametry)obj;
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		if(parametry.getSzukanieStolika() < 0)
		{
			err.rejectValue("szukanieStolika", "error.ZaMalaLiczba");
		}
		try
		{
			Time godzinaOtwar = new Time(formatter.parse(parametry.getGodzinaO()).getTime());
		}
		catch (ParseException e) 
		{
			zlyFormatGodzinyO = true;
		}
		try
		{
			Time godzinaZam  = new Time(formatter.parse(parametry.getGodzinaZ()).getTime());
		}
		catch(ParseException e)
		{
			zlyFormatGodzinyZ = true;
		}
		try
		{
			Time zwalnianie  = new Time(formatter.parse(parametry.getZwalnianie()).getTime());
		}
		catch(ParseException e)
		{
			zlyFormatZwalniania = true;
		}
		if(zlyFormatGodzinyO)
		{
			err.rejectValue("godzinaO", "error.ZlyFormatGodzinyOtwarcia");
        }
		if(zlyFormatGodzinyZ)
		{
			err.rejectValue("godzinaZ", "error.ZlyFormatGodzinyZamkniecia");
		}
		if(zlyFormatZwalniania)
		{
			err.rejectValue("zwalnianie", "error.ZlyFormatZwalniania");
        }
	}
}
