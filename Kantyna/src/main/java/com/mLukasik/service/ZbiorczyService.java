package com.mLukasik.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mLukasik.model.Koszyk;
import com.mLukasik.model.Potrawa;
import com.mLukasik.model.Potrawy_Zamowienia;
import com.mLukasik.model.RodzajPotrawy;
import com.mLukasik.model.Rola;
import com.mLukasik.model.Uzytkownik;
import com.mLukasik.model.Zamowienie;
import com.mLukasik.repository.PotrawaRepository;
import com.mLukasik.repository.RodzajPotrawyRepository;
import com.mLukasik.repository.RolaRepository;
import com.mLukasik.repository.UzytkownikRepository;
import com.mLukasik.repository.ZamowienieRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class ZbiorczyService 
{
	@Autowired
	private RodzajPotrawyRepository rodzajPotrawyRepository;
	@Autowired
	private RolaRepository rolaRepository;
	@Autowired
	private PotrawaRepository potrawaRepository;
	@Autowired
	private ZamowienieRepository zamowienieRepository;
	@Autowired
	private UzytkownikRepository uzytkownikRepository;
	
	public List<String> stworzListeRodzajow()
	{
		List<RodzajPotrawy> listaRodzajow = new ArrayList<RodzajPotrawy>();
	    listaRodzajow = rodzajPotrawyRepository.findAll();
		List<String> lista = new ArrayList<String>();
		for(RodzajPotrawy rp: listaRodzajow)
		{
			lista.add(rp.getRodzaj());
		}
		return lista;
	}
	
	public List<String> stworzListeRol()
	{
		List<Rola> role = new ArrayList<Rola>();
	    role = rolaRepository.findAll();
		ArrayList<String> lista = new ArrayList<String>();
		for(Rola rola: role)
		{
			lista.add(rola.getRola().replace("ROLE_", ""));
		}
		return lista;
	}
	
	public List<Potrawa> zmianaFormatu(List<Potrawa> potrawy)
	{
		for(Potrawa pot: potrawy)
		{
			byte[] encodeBase64 = Base64.encodeBase64(pot.getZdjecie());
			String base64Encoded;
			try
			{
				//imageBlob is storing the base64 representation of your image data. For storing that onto your disk you need to
				//decode that base64 representation into the original binary format representation.
				//https://stackoverflow.com/questions/50427495/java-blob-to-image-file
				base64Encoded = new String(encodeBase64, "UTF-8");
				pot.setBase64(base64Encoded);
			} 
			catch (UnsupportedEncodingException e) 
			{
			}
		}
		return potrawy;
	}
	
	public List<Koszyk> zmianaFormatu2(List<Koszyk> koszyk)
	{
		for(Koszyk kosz: koszyk)
		{
			byte[] encodeBase64 = Base64.encodeBase64(kosz.getPotrawa().getZdjecie());
			String base64Encoded;
			try
			{
				//imageBlob is storing the base64 representation of your image data. For storing that onto your disk you need to
				//decode that base64 representation into the original binary format representation.
				//https://stackoverflow.com/questions/50427495/java-blob-to-image-file
				base64Encoded = new String(encodeBase64, "UTF-8");
				kosz.getPotrawa().setBase64(base64Encoded);
			} 
			catch (UnsupportedEncodingException e) 
			{
			}
		}
		return koszyk;
	}
	
	public List<Potrawa> szukaniePotraw(HttpServletRequest request)
	{
		List<Potrawa> potrawy = new ArrayList<Potrawa>();
		if(request.getParameter("rodzaj") != null)
		{
			if(request.getParameter("rodzaj").equalsIgnoreCase("wszystkie"))
			{
				potrawy = potrawaRepository.findAll();
			}
			else
			{
				potrawy = potrawaRepository.findByRodzajPotrawyRodzaj(request.getParameter("rodzaj"));
			}
		}
		else
		{
			potrawy = potrawaRepository.findAll();
		}
		potrawy = zmianaFormatu(potrawy);
		List<Potrawa> potrawy2 = new ArrayList<Potrawa>();
		if(request.getParameter("nazwa") != null)
		{
			for(int i = 0; i < potrawy.size(); i++)
			{
				if((potrawy.get(i).getNazwa().toLowerCase().contains(request.getParameter("nazwa"))))
				{
					potrawy2.add(potrawy.get(i));
				}
			}
		}
		else
		{
			potrawy2 = potrawy;
		}
		return potrawy2;
	}
	
	public boolean czyPrzypomnienie()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Zamowienie> zamowienia = new ArrayList<Zamowienie>();
		zamowienia = zamowienieRepository.findByUzytkownikLoginAndCzyZrealizowaneFalse(auth.getName());
		boolean czyPrzypomnienie = false;
		double roznica = 0;
		for(int i = 0; i < zamowienia.size(); i++)
		{
			int czasReal = zamowienia.get(i).getCzasRealizacji().toSecondOfDay();
			int teraz = LocalTime.now().toSecondOfDay();
			roznica = czasReal - teraz;
			if(roznica > 0 && roznica <= 1800)
			{
				czyPrzypomnienie = true;
			}
		}
		return czyPrzypomnienie;
	}
	
	public List<Zamowienie> zmianaFormatu3(List<Zamowienie> listaZamowien)
	{
		for(int i = 0; i < listaZamowien.size(); i++)
		{
			for(Potrawy_Zamowienia potrawa_zamowienie: listaZamowien.get(i).getPotrawy_Zamowienia())
			{
				byte[] encodeBase64 = Base64.encodeBase64(potrawa_zamowienie.getPotrawa().getZdjecie());
				String base64Encoded;
				try
				{
					base64Encoded = new String(encodeBase64, "UTF-8");
					potrawa_zamowienie.getPotrawa().setBase64(base64Encoded);
				} 
				catch (UnsupportedEncodingException e) 
				{
				}
			}
		}
		return listaZamowien;
	}
	
	public boolean czyTelefonIstnieje(String telefon)
	{
		List<Uzytkownik> listaU = uzytkownikRepository.findByTelefon(telefon);
		if(listaU.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean czyLoginIstnieje(String login)
	{
		List<Uzytkownik> listaU = uzytkownikRepository.findByLogin(login);
		if(listaU.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//chyba zbedne
	/*public LocalTime coIleMilisekundZwalniac()
	{
		List<Parametry> parametry = new ArrayList<Parametry>();
		parametry = parametryRepository.findByIdParametru(1);
		LocalTime czas = parametry.get(0).getCoIleZwalniac();
		int cal = czas.toSecondOfDay(); 
		cal = cal * 1000;
		return czas;
		//return cal;
	}*/
}
