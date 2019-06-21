package com.mLukasik.validator;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
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
			//Time godzinaOtwar = new Time(formatter.parse(parametry.getGodzinaO()).getTime());
			LocalTime czas = LocalTime.parse(parametry.getGodzinaO());
		}
		catch (Exception e) 
		{
			zlyFormatGodzinyO = true;
		}
		try
		{
			LocalTime czas = LocalTime.parse(parametry.getGodzinaZ());
		}
		catch(Exception e)
		{
			zlyFormatGodzinyZ = true;
		}
		try
		{
			LocalTime czas = LocalTime.parse(parametry.getZwalnianie());
		}
		catch(Exception e)
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
		String telefonRegex = "[0-9]{3}-[0-9]{3}-[0-9]{3}";
		Pattern pattern = Pattern.compile(telefonRegex);
		Matcher matcher = pattern.matcher(parametry.getTelefon());
        if(!matcher.matches())
        {
        	err.rejectValue("Telefon", "error.zlyFormat");
        }
        String emailRegex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        pattern = Pattern.compile(emailRegex);
		matcher = pattern.matcher(parametry.getEmail());
	    if(!matcher.matches())
        {
        	err.rejectValue("Email", "error.ZlyFormatEmail");
        }
	}
}
