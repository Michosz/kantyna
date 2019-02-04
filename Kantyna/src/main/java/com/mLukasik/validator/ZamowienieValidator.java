package com.mLukasik.validator;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mLukasik.model.Parametry;
import com.mLukasik.model.Zamowienie;

public class ZamowienieValidator implements Validator
{
	int czyJestWolnyStolik = 1;
	boolean pusteZamowienie = false;
	Parametry parametry = new Parametry();
	
	public ZamowienieValidator()
	{
		
	}
	
	public ZamowienieValidator(int czyJestWolnyStolik, boolean pusteZamowienie, Parametry parametry)
	{
		this.czyJestWolnyStolik = czyJestWolnyStolik;
		this.pusteZamowienie = pusteZamowienie;
		this.parametry = parametry;
	}
	
	@Override
	public boolean supports(Class<?> c) 
	{
		return Zamowienie.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		//przetestowac jutro przypadek, ze jak jest np godzina 21 to nie mozesz tego dnia zamowienia zlozyc
		//jesli ktos chce zlozyc zamowienie i czas obecny > 19 to blokuj zamowienie
		Zamowienie zamowienie = (Zamowienie)obj;
		//DateFormat formatter = new SimpleDateFormat("HH:mm");
		boolean zlyFormatGodziny = false;
		LocalTime czas = LocalTime.now();
		try
		{
			czas = LocalTime.parse(zamowienie.getCzasReal());
			//Time czas = new Time(formatter.parse(zamowienie.getCzasReal()).getTime());
		}
		catch(Exception e)
		{
			zlyFormatGodziny = true;
		}
		if(zlyFormatGodziny)
		{
			err.rejectValue("czasReal", "error.ZlyFormatGodzinyOtwarcia");
        }
		else if(LocalTime.now().isAfter(parametry.getGodzinaZamkniecia().minusHours(1)))
		{
			err.rejectValue("czasReal", "error.DzisiajNieMozeszZamowic");
		}
		else
		{
			if(czas.isBefore(parametry.getGodzinaOtwarcia()) || czas.isAfter(parametry.getGodzinaZamkniecia().minusHours(1)) || parametry.getCzyZamkniete() == true)
			{
				err.rejectValue("czasReal", "error.ZleGodzinyZamowienia");
			}
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
