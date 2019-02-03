package com.mLukasik.validator;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.mLukasik.model.Zamowienie;

public class ZamowienieValidator implements Validator
{
	int czyJestWolnyStolik = 1;
	boolean pusteZamowienie = false;
	
	public ZamowienieValidator()
	{
		
	}
	
	public ZamowienieValidator(int czyJestWolnyStolik, boolean pusteZamowienie)
	{
		this.czyJestWolnyStolik = czyJestWolnyStolik;
		this.pusteZamowienie = pusteZamowienie;
	}
	
	@Override
	public boolean supports(Class<?> c) 
	{
		return Zamowienie.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		Zamowienie zamowienie = (Zamowienie)obj;
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		boolean zlyFormatGodziny = false;
		try
		{
			//LocalTime czas = LocalTime.parse(zamowienie.getCzasReal());
			Time czas = new Time(formatter.parse(zamowienie.getCzasReal()).getTime());
		}
		catch(Exception e)
		{
			zlyFormatGodziny = true;
		}
		if(zlyFormatGodziny)
		{
			err.rejectValue("czasReal", "error.ZlyFormatGodzinyOtwarcia");
        }
		if(zamowienie.getIloscMiejsc() == 0)
		{
			err.rejectValue("iloscMiejsc", "error.ZaMalaIloscMiejsc");
		}
		if(czyJestWolnyStolik == 0)
		{
			err.rejectValue("iloscMiejsc", "error.BrakWolnegoStolika");
		}
		if(pusteZamowienie)
		{
			err.rejectValue("listaIlosci", "error.ListaIlosci");
		}
	}
}
