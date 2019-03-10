package com.mLukasik.validator;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mLukasik.model.Koszyk;
import com.mLukasik.model.Parametry;
import com.mLukasik.model.Zamowienie;

public class ZamowienieValidator implements Validator
{
	int czyJestWolnyStolik = 1;
	boolean pusteZamowienie = false;
	Parametry parametry = new Parametry();
	List<Koszyk> koszyk;
	
	public ZamowienieValidator()
	{
		
	}
	
	public ZamowienieValidator(int czyJestWolnyStolik, boolean pusteZamowienie, Parametry parametry, List<Koszyk> koszyk)
	{
		this.czyJestWolnyStolik = czyJestWolnyStolik;
		this.pusteZamowienie = pusteZamowienie;
		this.parametry = parametry;
		this.koszyk = koszyk;
	}
	
	@Override
	public boolean supports(Class<?> c) 
	{
		return Zamowienie.class.equals(c);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		//jesli ktos chce zlozyc zamowienie i czas obecny > 19 to blokuj zamowienie
		boolean czyPotrawaNiedostepna = false;
		Zamowienie zamowienie = (Zamowienie)obj;
		boolean zlyFormatGodziny = false;
		for(int i = 0; i < koszyk.size(); i++)
		{
			if(koszyk.get(i).getPotrawa().getCzyJestDostepna())
			{
				czyPotrawaNiedostepna = true;
			}
		}
		if(czyPotrawaNiedostepna)
		{
			err.rejectValue("koszyk", "error.PotrawaNiedostepna");
		}
		LocalTime czas = LocalTime.now();
		try
		{
			czas = LocalTime.parse(zamowienie.getCzasReal());
		}
		catch(Exception e)
		{
			zlyFormatGodziny = true;
		}
		if(zlyFormatGodziny)
		{
			err.rejectValue("czasReal", "error.ZlyFormatGodzinyOtwarcia");
        }
		else if((parametry.getCzyAutoZwalniac() && LocalTime.now().isAfter(parametry.getGodzinaZamkniecia().minusSeconds((parametry.getCoIleZwalniac().toSecondOfDay())))) || LocalTime.now().isAfter(parametry.getGodzinaZamkniecia().minusHours(1)))
		{
			err.rejectValue("czasReal", "error.DzisiajNieMozeszZamowic");
		}
		else
		{
			int sekundyTeraz = LocalTime.now().toSecondOfDay();
			int sekundyCzasReal = czas.toSecondOfDay();
			int roznica = sekundyCzasReal - sekundyTeraz;
			if(roznica < 3600 && roznica >= 0)
			{
				err.rejectValue("czasReal", "error.PotrzebaGodzinyNaPrzygotowanie");
			}
			else if(roznica < 0)
			{
				err.rejectValue("czasReal", "error.NieMoznaZamawiacWprzeszlosci");
			}
			else if(czas.isBefore(parametry.getGodzinaOtwarcia()) || czas.isAfter(parametry.getGodzinaZamkniecia().minusHours(1)) || parametry.getCzyZamkniete() == true)
			{
				err.rejectValue("czasReal", "error.ZleGodzinyZamowienia");
			}
		}
		if(zamowienie.getIloscMiejsc() <= 0)
		{
			err.rejectValue("iloscMiejsc", "error.ZaMalaIloscMiejsc");
		}
		if(czyJestWolnyStolik == 0)
		{
			err.rejectValue("iloscMiejsc", "error.BrakWolnegoStolika");
		}
		if(pusteZamowienie)
		{
			err.rejectValue("koszyk", "error.pustyKoszyk");
		}
	}
}
