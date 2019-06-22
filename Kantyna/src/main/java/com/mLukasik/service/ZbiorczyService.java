package com.mLukasik.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mLukasik.model.ChargeRequest;
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
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class ZbiorczyService 
{
	@Value("${stripe.secret.key}")
	String secretKey;
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
				if((potrawy.get(i).getNazwa().toLowerCase().contains(request.getParameter("nazwa").toLowerCase())))
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
	
	public List<Zamowienie> generujListeZamowien(Authentication auth, List<Zamowienie> listaZamowien)
	{
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) auth.getAuthorities();
		if(authorities.get(0).getAuthority().contains("ROLE_KLIENT"))
		{
			listaZamowien = zamowienieRepository.findByUzytkownikLoginAndCzyZrealizowaneFalse(auth.getName());
		}
		if(authorities.get(0).getAuthority().contains("ROLE_MANAGER"))
		{
			listaZamowien = zamowienieRepository.findByCzyZrealizowaneFalse();
			List<Zamowienie> listaZamowienNowych = zamowienieRepository.findByCzyManagerJeWidzialFalse();
			for(Zamowienie z: listaZamowienNowych)
			{
				z.setCzyManagerJeWidzial(true);
				zamowienieRepository.save(z);
			}
		}
		return listaZamowien;
	}
	
	public List<Zamowienie> policzCeneZamowien(List<Zamowienie> listaZamowien, Authentication auth)
	{
		double cena = 0;
		int centy = 0;
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) auth.getAuthorities();
		if(authorities.get(0).getAuthority().contains("ROLE_KLIENT"))
		{
			for(int j = 0; j < listaZamowien.size(); j++)
			{
				cena = 0;
				centy = 0;
				for(int i = 0; i < listaZamowien.get(j).getPotrawy_Zamowienia().size(); i++)
				{
					if(listaZamowien.get(j).getPotrawy_Zamowienia().get(i).getPotrawa().getCzyPromocja())
					{
						cena = cena + (listaZamowien.get(j).getPotrawy_Zamowienia().get(i).getPotrawa().getCenaPromocyjna() * listaZamowien.get(j).getPotrawy_Zamowienia().get(i).getIlosc());
					}
					else
					{
						cena = cena + (listaZamowien.get(j).getPotrawy_Zamowienia().get(i).getPotrawa().getCena() * listaZamowien.get(j).getPotrawy_Zamowienia().get(i).getIlosc());
					}
				}
				centy = (int) (cena * 100);
				listaZamowien.get(j).setCenaCalkowita(centy); //w centach trzeba wyslac
			}
		}
		return listaZamowien;
	}
	
	public int zwrocId(HttpServletRequest request) 
	{
		int id = 0;
		try
	    {
			id = Integer.parseInt(request.getParameter("par"));
	    }
		catch(Exception e)
		{

		}
		return id;
	}
	
	public Model generujModelAttributeDlaZamowienAktualnych(Model model, String publicKey, Authentication auth)
	{
		model.addAttribute("blad", 1);
		List<Zamowienie> listaZamowien = new ArrayList<Zamowienie>();
		model.addAttribute("uzytkownik", auth.getName());
		listaZamowien =  generujListeZamowien(auth, listaZamowien);
		listaZamowien = zmianaFormatu3(listaZamowien);
		listaZamowien = policzCeneZamowien(listaZamowien, auth);
	    model.addAttribute("stripePublicKey", publicKey);
	    model.addAttribute("currency", "PLN");
		model.addAttribute("iloscRekordow", listaZamowien.size());
		model.addAttribute("listaZamowien", listaZamowien);
		return model;
	}
	
	 public Charge charge(ChargeRequest chargeRequest) throws StripeException 
     {
		Stripe.apiKey = secretKey;
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        chargeParams.put("receipt_email", chargeRequest.getStripeEmail());
        return Charge.create(chargeParams);
     }
}