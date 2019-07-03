package com.mLukasik.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mLukasik.model.Rola;
import com.mLukasik.model.Stolik;
import com.mLukasik.model.Komentarz;
import com.mLukasik.model.Koszyk;
import com.mLukasik.model.Oplata;
import com.mLukasik.model.Parametry;
import com.mLukasik.model.Uzytkownik;
import com.mLukasik.model.Zamowienie;
import com.mLukasik.model.Potrawa;
import com.mLukasik.model.Potrawy_Zamowienia;
import com.mLukasik.model.RodzajPotrawy;
import com.mLukasik.repository.KomentarzRepository;
import com.mLukasik.repository.KoszykRepository;
import com.mLukasik.repository.ParametryRepository;
import com.mLukasik.repository.PotrawaRepository;
import com.mLukasik.repository.RodzajPotrawyRepository;
import com.mLukasik.repository.RolaRepository;
import com.mLukasik.repository.StolikRepository;
import com.mLukasik.repository.UzytkownikRepository;
import com.mLukasik.repository.ZamowienieRepository;
import com.mLukasik.service.ZbiorczyService;
import com.mLukasik.validator.KomentarzValidator;
import com.mLukasik.validator.KoszykValidator;
import com.mLukasik.validator.ParametryValidator;
import com.mLukasik.validator.PotrawaValidator;
import com.mLukasik.validator.RodzajPotrawyValidator;
import com.mLukasik.validator.StolikValidator;
import com.mLukasik.validator.UzytkownikValidator;
import com.mLukasik.validator.ZamowienieValidator;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Transactional
@Controller
public class ApplicationController 
{
	private BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
	@Autowired
	private UzytkownikRepository uzytkownikRepository;
	@Autowired
	private RolaRepository rolaRepository;
	@Autowired
	private ParametryRepository parametryRepository;
	@Autowired
	private StolikRepository stolikRepository;
	@Autowired
	private PotrawaRepository potrawaRepository;
	@Autowired
	private RodzajPotrawyRepository rodzajPotrawyRepository;
	@Autowired
	private KomentarzRepository komentarzRepository;
	@Autowired
	private ZamowienieRepository zamowienieRepository;
	@Autowired
	private KoszykRepository koszykRepository;
	
	@Autowired
	ZbiorczyService zbiorczyService;
	@Value("${stripe.public.key}")
	String publicKey;
	
	@RequestMapping({"/", "/a", "/b", "/c"})
	public String Start(Model model, HttpServletRequest request)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean czyPrzypomnienie = false;
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) auth.getAuthorities();
		if(authorities.get(0).getAuthority().contains("ROLE_KLIENT"))
		{
			czyPrzypomnienie = zbiorczyService.czyPrzypomnienie();
			model.addAttribute("przypomnienie", czyPrzypomnienie);
		}
		List<Potrawa> potrawy = new ArrayList<Potrawa>();
		potrawy = zbiorczyService.szukaniePotraw(request);
		model.addAttribute("potrawy", potrawy);
		model.addAttribute("Komentarz", new Komentarz());
		model.addAttribute("numerPotrawy", 0);
		model.addAttribute("Koszyk", new Koszyk());
		model.addAttribute("numerPotrawyKoszyk", 0);
		model.addAttribute("Potrawa", new Potrawa());
		model.addAttribute("numerPotrawyPromocja", 0);
		List<String> listaRodzajow = zbiorczyService.stworzListeRodzajow();
		listaRodzajow.add("wszystkie");
		if(request.getParameter("rodzaj") != null)
		{
			Collections.swap(listaRodzajow, 0, listaRodzajow.indexOf(request.getParameter("rodzaj")));
		}
		else
		{
			Collections.swap(listaRodzajow, 0, listaRodzajow.size() - 1);
		}
		model.addAttribute("lista", listaRodzajow);
		model.addAttribute("uzytkownik", auth.getName());
		model.addAttribute("iloscRekordow", potrawy.size());
		if(authorities.get(0).getAuthority().contains("ROLE_MANAGER"))
		{
			List<Zamowienie> niezatwierdzoneZamowienia = zamowienieRepository.findByCzyManagerJeWidzialFalse();
			if(niezatwierdzoneZamowienia.size() > 0)
			{
				model.addAttribute("noweZamowienia", true);
			}
		}
		return "main";
	}
	
	@RequestMapping(value = "/logowanie", method = RequestMethod.GET)
	public String Logowanie(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		if(request != null)
		{
			model.addAttribute("koment", request.getParameter("kom"));
		}
		return "logowanie";
	}
	
	
	@RequestMapping(value = "/rejestracja", method = RequestMethod.GET)
	public ModelAndView Rejestracja(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    model.addAttribute("uzytkownik", auth.getName());
	    List<String> role = zbiorczyService.stworzListeRol();
		model.addAttribute("lista", role);
		return new ModelAndView("rejestracja", "Uzytkownik", new Uzytkownik());
	}
	
	@PostMapping("/rejestracja")
	public String zarejestruj(@Valid @ModelAttribute("Uzytkownik") Uzytkownik uzytkownik, BindingResult result, RedirectAttributes redir, ModelMap map, Model model)
	{
		boolean czyLoginIstnieje = zbiorczyService.czyLoginIstnieje(uzytkownik.getLogin());
		boolean czyTelefonIstnieje = zbiorczyService.czyTelefonIstnieje(uzytkownik.getTelefon());
		new UzytkownikValidator(czyLoginIstnieje, czyTelefonIstnieje).validate(uzytkownik, result);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(result.hasErrors())
		{
		    model.addAttribute("uzytkownik", auth.getName());
		    List<String> role = zbiorczyService.stworzListeRol();
			model.addAttribute("lista", role);
			return "rejestracja";
		}
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) auth.getAuthorities();
		if(authorities.get(0).getAuthority().contains("ROLE_MANAGER"))
		{
			String pelnaRola = "ROLE_" + uzytkownik.getRolaa();
			Rola rola = rolaRepository.findByRola(pelnaRola).get(0);
			uzytkownik.setRola(rola);
		}
		else
		{
			List<Rola> role = rolaRepository.findByRola("ROLE_KLIENT");
			uzytkownik.setRola(role.get(0));
		}
		uzytkownik.setCzyAktywny(true);
		uzytkownik.setKomentarz(" ");
		uzytkownik.setHaslo(bcp.encode(uzytkownik.getHaslo()));
		uzytkownikRepository.save(uzytkownik);
		redir.addAttribute("UdanaRejestracja", 1);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView informacje(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		List<Parametry> listaP = parametryRepository.findByIdParametru(1);
		model.addAttribute("wartosci", listaP.get(0));
		return new ModelAndView("info", "Parametry", new Parametry());
	}
	
	@RequestMapping(value = "/parametry", method = RequestMethod.GET)
	public ModelAndView Parametry(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		List<Parametry> listaP = parametryRepository.findByIdParametru(1);
		model.addAttribute("wartosci", listaP.get(0));
		return new ModelAndView("parametry", "Parametry", new Parametry());
	}
	
	@PostMapping("/parametry")
	public String zmienParametry(@Valid @ModelAttribute("Parametry") Parametry parametry, BindingResult result, RedirectAttributes redir, ModelMap map, Model model)
	{
		List<Parametry> listaP = parametryRepository.findByIdParametru(1);
		if(listaP.size() > 0)
		{
			parametry.setIdParametru(listaP.get(0).getIdParametru());
		}
		new ParametryValidator().validate(parametry, result);
		if(result.hasErrors())
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			model.addAttribute("uzytkownik", auth.getName());
			List<Parametry> listaP1 = parametryRepository.findByIdParametru(1);
			model.addAttribute("wartosci", listaP1.get(0));
			return "parametry";
		}
		else
		{
			LocalTime godzinaOtwar = LocalTime.parse(parametry.getGodzinaO());
			LocalTime godzinaZam = LocalTime.parse(parametry.getGodzinaZ());
			LocalTime zwalnianie = LocalTime.parse(parametry.getZwalnianie());
			parametry.setGodzinaOtwarcia(godzinaOtwar);
			parametry.setGodzinaZamkniecia(godzinaZam);
			parametry.setCoIleZwalniac(zwalnianie);
			parametryRepository.save(parametry);
			redir.addAttribute("ParamZmienione", 1);
			return "redirect:/";
		}
	}
	
	//wykrywanie jezyka
	  // Locale locale = LocaleContextHolder.getLocale();
      // sessionLocaleResolver.setDefaultLocale(locale);
	
	@RequestMapping(value = "/nowy-stolik", method = RequestMethod.GET)
	public ModelAndView Stoliki(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		return new ModelAndView("nowy-stolik", "Stolik", new Stolik());
	}
	
	@PostMapping(value = "/nowy-stolik")
	public String szukajStolika(@Valid @ModelAttribute("Stolik") Stolik stolik, BindingResult result, RedirectAttributes redir, ModelMap map, Model model)
	{
		List<Stolik> listastolikow = new ArrayList<Stolik>();
		listastolikow = stolikRepository.findByNazwaIgnoreCase(stolik.getNazwa());
		boolean czyNazwaIstnieje = false;
		if(listastolikow.size() > 0 )
		{
			czyNazwaIstnieje = true;
		}
		new StolikValidator(czyNazwaIstnieje).validate(stolik, result);
		if(result.hasErrors())
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			model.addAttribute("uzytkownik", auth.getName());
			return "nowy-stolik";
		}
		stolik.setCzyJestZajety(false);
		stolikRepository.save(stolik);
		redir.addAttribute("nowyStol", 1);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/stoliki", method = RequestMethod.GET)
	public String pokazStoliki(HttpServletRequest request, Model model)
	{
		List<Stolik> listaStolikow = new ArrayList<Stolik>();
		listaStolikow = stolikRepository.findAll();
		model.addAttribute("listaStolikow", listaStolikow);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		model.addAttribute("iloscRekordow", listaStolikow.size());
		return "/stoliki";
	}
	
	@PostMapping(value = "/zwolnij")
	public String zwolnijStolik(RedirectAttributes redir, HttpServletRequest request)
	{
		int id = zbiorczyService.zwrocId(request);
		if(id != 0)
		{
			List<Stolik> listaStolikow = stolikRepository.findByIdStolika(id);
			listaStolikow.get(0).setCzyJestZajety(false);
			redir.addAttribute("zwolniony", 1);
			stolikRepository.save(listaStolikow.get(0));
		}
		return "redirect:/stoliki";
	}
	
	@RequestMapping(value = "/nowa-potrawa", method = RequestMethod.GET)
	public ModelAndView nowaPotraw(HttpServletRequest request, Model model)
	{
	    List<String> listaRodzajow = zbiorczyService.stworzListeRodzajow();
		model.addAttribute("lista", listaRodzajow);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		return new ModelAndView("nowa-potrawa", "Potrawa", new Potrawa());
	}
	
	@PostMapping(value = "/nowa-potrawa")
	public String dodajPotrawe(@ModelAttribute("Potrawa") Potrawa potrawa, BindingResult result, RedirectAttributes redir, ModelMap map, Model model)
	{
		List<RodzajPotrawy> rodzaj = rodzajPotrawyRepository.findByRodzaj(potrawa.getRodzajPot());
		potrawa.setRodzajPotrawy(rodzaj.get(0));
		potrawa.setCzyJestDostepna(true);
		List<Potrawa> listaPot = new ArrayList<Potrawa>();
		listaPot = potrawaRepository.findByNazwaIgnoreCase(potrawa.getNazwa());
		boolean czyNazwaIstnieje = false;
		if(listaPot.size() > 0 )
		{
			czyNazwaIstnieje = true;
		}
		new PotrawaValidator(czyNazwaIstnieje).validate(potrawa, result);
		if(result.hasErrors())
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			model.addAttribute("uzytkownik", auth.getName());
			List<String> listaRodzajow = zbiorczyService.stworzListeRodzajow();
			model.addAttribute("lista", listaRodzajow);
			return "nowa-potrawa";
		}
		try 
		{
			potrawa.setZdjecie(potrawa.getObrazek().getBytes());
		} 
		catch (IOException e) 
		{
		}
		redir.addAttribute("nowaPot", 1);
		potrawaRepository.save(potrawa);
		return "redirect:/";
	}
	
	@PostMapping(value = "/usunZmenu")
	public String zmienDostepnoscPotrawy(RedirectAttributes redir, HttpServletRequest request)
	{
		int id = zbiorczyService.zwrocId(request);
		if(id != 0)
		{
			List<Potrawa> listaP = potrawaRepository.findById(id);
			if(listaP.get(0).getCzyJestDostepna() == true)
			{
				listaP.get(0).setCzyJestDostepna(false);
				redir.addAttribute("potUsunieta", 1);
			}
			else
			{
				listaP.get(0).setCzyJestDostepna(true);
				redir.addAttribute("potDodana", 1);
			}
			potrawaRepository.save(listaP.get(0));
		}
		return "redirect:/";
	}
	
	//dzialajaca metoda zwalniania w zaleznosci od parametrow
	@Scheduled(fixedRate =  300000, initialDelay = 300000) //5minut
	public void zwolnijStoliki()
	{
		List<Parametry> parametry = parametryRepository.findByIdParametru(1);
		if(parametry.get(0).getCzyAutoZwalniac() == true)
		{
			LocalTime czas = parametry.get(0).getCoIleZwalniac();
			List<Zamowienie> zamowienia = zamowienieRepository.findByCzyZrealizowaneFalse();
			for(Zamowienie zamowienie: zamowienia)
			{
				Stolik stolik = new Stolik();
				if(LocalTime.now().minusSeconds(czas.toSecondOfDay()).isAfter(zamowienie.getCzasRealizacji()))
				{
					stolik = zamowienie.getStolik();
					List<Zamowienie> zamow = zamowienieRepository.findByCzyZrealizowaneFalseAndStolikNazwa(stolik.getNazwa());
					if(zamow.size() > 1)
					{
						zamowienie.setCzyZrealizowane(true);
						zamowienieRepository.save(zamowienie);
					}
					else
					{
						stolik.setCzyJestZajety(false);
						stolikRepository.save(stolik);
						zamowienie.setCzyZrealizowane(true);
						zamowienieRepository.save(zamowienie);
					}
				}
			}
		}
	}
	
	//zwalnianie stolikow o polnocy
	//@Scheduled(cron = "0 0 0 * * ?")
	/*@Scheduled(fixedRate = parametryRepository.findByIdParametru(1).get(0).getCoIleZwalniac().get)
	public void zwolnijStolikiCo24h()
	{
		parametryRepository.findByIdParametru(1).get(0).getCoIleZwalniac().ge
		List<Parametry> parametry = parametryRepository.findByIdParametru(1);
		if(parametry.get(0).getCzyAutoZwalniac() == true)
		{
			List<Stolik> stoliki = stolikRepository.findByCzyJestZajety(true);
			for(Stolik stolik: stoliki)
			{
				stolik.setCzyJestZajety(false);
				stolikRepository.save(stolik);
			}
		}
	}*/
	
	@PostMapping(value = "/c")
	public String dodajKomentarz(@ModelAttribute("Komentarz") Komentarz komentarz, HttpServletRequest request, BindingResult result, RedirectAttributes redir, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		new KomentarzValidator().validate(komentarz, result);
		if(result.hasErrors())
		{
			List<Potrawa> potrawy = new ArrayList<Potrawa>();
			potrawy = zbiorczyService.szukaniePotraw(request);
			model.addAttribute("potrawy", potrawy);
			List<String> listaRodzajow = zbiorczyService.stworzListeRodzajow();
			listaRodzajow.add("wszystkie");
			Collections.swap(listaRodzajow, 0, listaRodzajow.size() - 1);
			model.addAttribute("lista", listaRodzajow);
			model.addAttribute("uzytkownik", auth.getName());
			model.addAttribute("numerPotrawy", komentarz.getIdPotrawy());
			model.addAttribute("Koszyk", new Koszyk());
			model.addAttribute("numerPotrawyKoszyk", 0);
			model.addAttribute("Potrawa", new Potrawa());
			model.addAttribute("numerPotrawyPromocja", 0);
			List<Zamowienie> niezatwierdzoneZamowienia = zamowienieRepository.findByCzyManagerJeWidzialFalse();
			if(niezatwierdzoneZamowienia.size() > 0)
			{
				model.addAttribute("noweZamowienia", true);
			}
			model.addAttribute("iloscRekordow", potrawy.size());
			return "main";
		}
		else
		{
			komentarz.setOcena(Integer.parseInt(komentarz.getJakaOcena()));
			komentarz.setUzytkownik(uzytkownikRepository.findByLogin(auth.getName()).get(0));
			List<Potrawa> listaP = potrawaRepository.findById(komentarz.getIdPotrawy());
			komentarz.setPotrawa(listaP.get(0));
			redir.addAttribute("nowyKom", 1);
			komentarzRepository.save(komentarz);
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/zamowienie", method = RequestMethod.GET)
	public ModelAndView noweZamowienie(HttpServletRequest request, Model model)
	{
		List<Potrawa> potrawy = potrawaRepository.findByCzyJestDostepna(true);
		potrawy = zbiorczyService.zmianaFormatu(potrawy);
		model.addAttribute("potrawy", potrawy);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		return new ModelAndView("zamowienie", "Zamowienie", new Zamowienie());
	}
	
	//uwaga co do braku komentarzy, albo disablowac przycisk, albo dorobic argument w funkcji w javascript i na podstawie tego wyswietlic komunikat, bez javascriptu niestety sie nie da
	@PostMapping(value = "/zamowienie")
	public String dodajZamowienie(@ModelAttribute("Zamowienie") Zamowienie zamowienie, BindingResult result, RedirectAttributes redir, Model model, HttpServletRequest request)
	{
		boolean pusteZamowienie = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int ilosc = zamowienie.getIloscMiejsc();
		List<Parametry> listaP = parametryRepository.findByIdParametru(1);
		List<Parametry> param = parametryRepository.findAll();
		int maxIlosc = ilosc + listaP.get(0).getSzukanieStolika();
		int iloscStolikow = 0;
		List<Stolik> listaStolikow = stolikRepository.findByIloscMiejscBetweenAndCzyJestZajetyOrderByIloscMiejscAsc(zamowienie.getIloscMiejsc(), maxIlosc, false);
		List<Koszyk> koszyk = koszykRepository.findByUzytkownikLogin(auth.getName());
		if(koszyk.size() == 0)
		{
			pusteZamowienie = true;
		}
		iloscStolikow = listaStolikow.size();
		//poczatek szukania stolika, ktory jest zajety, ale zwolni sie do okreslonej godziny
		if(listaStolikow.size() == 0 && param.get(0).getCzyAutoZwalniac() == true)
		{
			List<Zamowienie> zamowienia = new ArrayList<Zamowienie>();
			zamowienia = zamowienieRepository.findByCzyZrealizowaneFalseAndStolikIloscMiejscBetweenOrderByStolikIloscMiejscAsc(ilosc, maxIlosc);
			//teraz trzeba sprawdzac czy sie zwolni na czas
			for(int i = 0; i < zamowienia.size(); i++)
			{
				int czasTrwania = param.get(0).getCoIleZwalniac().toSecondOfDay();
				int czasReal = zamowienia.get(i).getCzasRealizacji().toSecondOfDay();
				int koniec = czasReal + czasTrwania;
				LocalTime sparsowane = LocalTime.now();
				boolean czyWyjatek = false;
				try
				{
					sparsowane = LocalTime.parse(zamowienie.getCzasReal());
				}
				catch(Exception ex)
				{
					czyWyjatek = true;
				}
				if((koniec <= sparsowane.toSecondOfDay() && czyWyjatek == false) || (czasReal >= (sparsowane.toSecondOfDay() + czasTrwania) && czyWyjatek == false))
				{
					iloscStolikow = 1;
					if(zamowienie.getStolik() == null)
					{
						zamowienie.setStolik(zamowienia.get(i).getStolik());
						break;
					}
				}
				else
				{
					iloscStolikow = 0;
				}
			}
		}
		//koniec szukania 
		new ZamowienieValidator(iloscStolikow, pusteZamowienie, listaP.get(0), koszyk).validate(zamowienie, result);
		if(result.hasErrors())
		{
			model.addAttribute("uzytkownik", auth.getName());
			model.addAttribute("Zamowienie", zamowienie);
			return "zamowienie";
		}
		else
		{
			if(listaStolikow.size() > 0)
			{
				listaStolikow.get(0).setCzyJestZajety(true);
				stolikRepository.save(listaStolikow.get(0));
				zamowienie.setStolik(listaStolikow.get(0));
			}
			List<Potrawy_Zamowienia> lista = new ArrayList<Potrawy_Zamowienia>();
			Potrawy_Zamowienia pot = new Potrawy_Zamowienia();
			for(int i = 0; i < koszyk.size(); i++)
			{
				pot = new Potrawy_Zamowienia();
				pot.setIlosc(koszyk.get(i).getIlosc());
				pot.setPotrawa(koszyk.get(i).getPotrawa());
				pot.setZamowienie(zamowienie);
				lista.add(pot);
			}
			koszykRepository.deleteAll(koszyk);
			LocalTime czas = LocalTime.parse(zamowienie.getCzasReal()); // rzuca datetimeparseexception jesli nie uda sie sparsowac
			zamowienie.setCzasRealizacji(czas);
			Date date = new Date();
			zamowienie.setUzytkownik(uzytkownikRepository.findByLogin(auth.getName()).get(0));
			zamowienie.setCzyManagerJeWidzial(false);
			zamowienie.setDataZam(date);
			zamowienie.setPotrawy_Zamowienia(lista);
			int cena = zbiorczyService.policzCeneZamowienia(zamowienie);
			zamowienie.setCenaCalkowita(cena);
			Zamowienie zam = zamowienieRepository.save(zamowienie);
			if(zamowienie.getCzyPlaciOdRazu())
			{
				HttpSession session = request.getSession(false);
				session.setAttribute("idZamowienia", zam.getId());
				return "redirect:/zamowieniaAkt";
			}
			else
			{
				redir.addAttribute("zamowienieZlozone", 1);
				return "redirect:/";
			}
		}
	}
	
	@RequestMapping(value = "/zamowienia", method = RequestMethod.GET)
	public String pokazZamowienia(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession(false);
		int id = 0;
		try
		{
			id = (int)session.getAttribute("idZamowienia");
		}
		catch(Exception ex)
		{
			session.setAttribute("idZamowienia", 0);
		}
		List<Zamowienie> listaZamowien = new ArrayList<Zamowienie>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) auth.getAuthorities();
		model.addAttribute("uzytkownik", auth.getName());
		if(authorities.get(0).getAuthority().contains("ROLE_KLIENT"))
		{
			listaZamowien = zamowienieRepository.findByUzytkownikLoginAndCzyZrealizowaneTrue(auth.getName());
		}
		if(authorities.get(0).getAuthority().contains("ROLE_MANAGER"))
		{
			listaZamowien = zamowienieRepository.findByCzyZrealizowaneTrue();
			List<Zamowienie> listaZamowienNowych = zamowienieRepository.findByCzyManagerJeWidzialFalse();
			for(Zamowienie z: listaZamowienNowych)
			{
				z.setCzyManagerJeWidzial(true);
				zamowienieRepository.save(z);
			}
		}
		listaZamowien = zbiorczyService.zmianaFormatu3(listaZamowien);
	    model.addAttribute("stripePublicKey", publicKey);
	    model.addAttribute("currency", "PLN");
		model.addAttribute("iloscRekordow", listaZamowien.size());
		model.addAttribute("listaZamowien", listaZamowien);
		return "/zamowienia";
	}
	
	@RequestMapping(value = "/zamowieniaAkt", method = RequestMethod.GET)
	public String pokazZamowieniaAktualne(HttpServletRequest request, Model model)
	{
		//<c:remove var="variableName"/>//usuniecie zmiennej z sesji
		HttpSession session = request.getSession(false);
		int id = 0;
		try
		{
			id = (int)session.getAttribute("idZamowienia");
		}
		catch(Exception ex)
		{
			session.setAttribute("idZamowienia", 0);
		}
		List<Zamowienie> listaZamowien = new ArrayList<Zamowienie>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		listaZamowien =  zbiorczyService.generujListeZamowien(auth, listaZamowien);
		listaZamowien = zbiorczyService.zmianaFormatu3(listaZamowien);
	    model.addAttribute("stripePublicKey", publicKey);
	    model.addAttribute("currency", "PLN");
		model.addAttribute("iloscRekordow", listaZamowien.size());
		model.addAttribute("listaZamowien", listaZamowien);
		return "/zamowienia";
	}
 
    @PostMapping("/zaplac")
    public String charge(Oplata oplata, Model model, HttpServletRequest request, RedirectAttributes redir) throws StripeException
    {
    	boolean zrealizowane = false;
    	int id = oplata.getStripeNum();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(id != 0)
		{	    	
			List<Zamowienie> zamowienie = zamowienieRepository.findById(id);
			zrealizowane = zamowienie.get(0).getCzyZrealizowane();
			//zamowienie = zbiorczyService.policzCeneZamowien(zamowienie, auth);
			oplata.setOpis("Money for order with id = " + id);
			oplata.setWaluta("PLN");
			oplata.setStripeEmail(auth.getName());
			oplata.setSuma(zamowienie.get(0).getCenaCalkowita());
	        try
	        {
	        	Charge charge = zbiorczyService.oplac(oplata);
	        }
	        catch(StripeException ex)
	        {
	        	if(zrealizowane)
	        	{
		        	redir.addAttribute("blad", 1);
					return "redirect:/zamowienia";
	        	}
	        	else
	        	{
		        	redir.addAttribute("blad", 1);
					return "redirect:/zamowieniaAkt";
	        	}
	        }
	        zamowienie.get(0).setCzyZaplacone(true);
	        zamowienieRepository.save(zamowienie.get(0));
	        redir.addAttribute("sukces", 1);
	        if(zrealizowane)
        	{
	        	return "redirect:/zamowienia";
        	}
	    	else
        	{
				return "redirect:/zamowieniaAkt";
        	}
		}
		else
		{
			if(zrealizowane)
			{
		     	redir.addAttribute("blad", 1);
				return "redirect:/zamowienia";
			}
			else
			{
	        	redir.addAttribute("blad", 1);
				return "redirect:/zamowieniaAkt";
			}
		}
    }
    
    @PostMapping("/potwierdzOplate")
    public String potwierdzOplate(RedirectAttributes redir, HttpServletRequest request)
    {
    	List<Zamowienie> zamowienie = new ArrayList<Zamowienie>();
    	int id = zbiorczyService.zwrocId(request);
		if(id != 0)
		{
			zamowienie = zamowienieRepository.findById(id);
	    	zamowienie.get(0).setCzyZaplacone(true);
	    	zamowienieRepository.save(zamowienie.get(0));
	    	redir.addAttribute("potwierdzenieOplaty", 1);
		}
		if(zamowienie.size() > 0)
		{
			if(zamowienie.get(0).getCzyZrealizowane())
			{
		    	return "redirect:/zamowienia";
			}
			else
			{
		    	return "redirect:/zamowieniaAkt";
			}
		}
    	return "redirect:/zamowienia";
    }
 
   /* @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) 
    {
        //model.addAttribute("error", ex.getMessage());
        //return "result";
    	model.addAttribute("blad", 1);
		List<Zamowienie> listaZamowien = new ArrayList<Zamowienie>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		listaZamowien =  zbiorczyService.generujListeZamowien(auth, listaZamowien);
		listaZamowien = zbiorczyService.zmianaFormatu3(listaZamowien);
		listaZamowien = zbiorczyService.policzCeneZamowien(listaZamowien, auth);
	    model.addAttribute("stripePublicKey", publicKey);
	    model.addAttribute("currency", "PLN");
		model.addAttribute("iloscRekordow", listaZamowien.size());
		model.addAttribute("listaZamowien", listaZamowien);
		return "/zamowienia";
    }*/
	
	@PostMapping(value = "/zatwierdzZamowienie")
	public String zatwierdz(RedirectAttributes redir, HttpServletRequest request)
	{
		int id = zbiorczyService.zwrocId(request);
		if(id != 0)
		{
			List<Zamowienie> zamowienie = zamowienieRepository.findById(id);
			zamowienie.get(0).setCzyZrealizowane(true);
			zamowienieRepository.save(zamowienie.get(0));
			Stolik stolik = zamowienie.get(0).getStolik();
			List<Zamowienie> zamowienia = zamowienieRepository.findByCzyZrealizowaneFalseAndStolikNazwa(stolik.getNazwa());
			if(zamowienia.size() > 0)
			{
				
			}
			else
			{
				stolik.setCzyJestZajety(false);
				stolikRepository.save(stolik);
			}
			redir.addAttribute("zamZatwierdzone", 1);
		}
		return "redirect:/zamowieniaAkt";
	}
	
	@RequestMapping(value = "/nowy-rodzaj", method = RequestMethod.GET)
	public ModelAndView nowyRodzaj(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		return new ModelAndView("nowy-rodzaj", "RodzajPot", new RodzajPotrawy());
	}
	
	@PostMapping(value = "/nowy-rodzaj")
	public String dodajRodzaj(@ModelAttribute("RodzajPot") RodzajPotrawy rodzajPot, BindingResult result, RedirectAttributes redir, Model model)
	{
		List<RodzajPotrawy> rodzaje = new ArrayList<RodzajPotrawy>();
		rodzaje = rodzajPotrawyRepository.findByRodzajIgnoreCase(rodzajPot.getRodzaj());
		boolean czyRodzajIstnieje = false;
		if(rodzaje.size() > 0)
		{
			czyRodzajIstnieje = true;
		}
		new RodzajPotrawyValidator(czyRodzajIstnieje).validate(rodzajPot, result);
		if(result.hasErrors())
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			model.addAttribute("uzytkownik", auth.getName());
			return "nowy-rodzaj";
		}
		else
		{
			rodzajPotrawyRepository.save(rodzajPot);
			redir.addAttribute("nowyRodzaj", 1);
			return "redirect:/";
		}
	}
	
	@PostMapping(value = "/a")
	public String dodajDoKoszyka(@ModelAttribute("Koszyk") Koszyk koszyk, HttpServletRequest request, BindingResult result, RedirectAttributes redir, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Potrawa> potrawa = new ArrayList<Potrawa>();
		potrawa = potrawaRepository.findById(koszyk.getIdPotrawy());
		new KoszykValidator().validate(koszyk, result);
		if(result.hasErrors())
		{
			List<Potrawa> potrawy = new ArrayList<Potrawa>();
			potrawy = zbiorczyService.szukaniePotraw(request);
			model.addAttribute("potrawy", potrawy);
			List<String> listaRodzajow = zbiorczyService.stworzListeRodzajow();
			listaRodzajow.add("wszystkie");
			Collections.swap(listaRodzajow, 0, listaRodzajow.size() - 1);
			model.addAttribute("lista", listaRodzajow);
			model.addAttribute("uzytkownik", auth.getName());
			model.addAttribute("numerPotrawyKoszyk", koszyk.getIdPotrawy());
			model.addAttribute("Komentarz", new Komentarz());
			model.addAttribute("numerPotrawy", 0);
			model.addAttribute("numerPotrawyPromocja", 0);
			model.addAttribute("Potrawa", new Potrawa());
			List<Zamowienie> niezatwierdzoneZamowienia = zamowienieRepository.findByCzyManagerJeWidzialFalse();
			if(niezatwierdzoneZamowienia.size() > 0)
			{
				model.addAttribute("noweZamowienia", true);
			}
			model.addAttribute("iloscRekordow", potrawy.size());
			return "main";
		}
		else
		{
			List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
			uzytkownik = uzytkownikRepository.findByLogin(auth.getName());
			List<Koszyk> czyJuzMaPotraweWkoszyku = new ArrayList<Koszyk>();
			czyJuzMaPotraweWkoszyku = koszykRepository.findByPotrawaNazwaAndUzytkownikLogin(potrawa.get(0).getNazwa(), uzytkownik.get(0).getLogin());
			if(czyJuzMaPotraweWkoszyku.size() > 0)
			{
				int nowaIlosc = czyJuzMaPotraweWkoszyku.get(0).getIlosc() + koszyk.getIlosc();
				czyJuzMaPotraweWkoszyku.get(0).setIlosc(nowaIlosc);
				koszykRepository.save(czyJuzMaPotraweWkoszyku.get(0));
			}
			else
			{
				koszyk.setUzytkownik(uzytkownik.get(0));
				koszyk.setPotrawa(potrawa.get(0));
				koszykRepository.save(koszyk);
			}
			redir.addAttribute("wKoszyku", 1);
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/koszyk", method = RequestMethod.GET)
	public String pokazKoszyk(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		List<Koszyk> koszyk = new ArrayList<Koszyk>();
		koszyk = koszykRepository.findByUzytkownikLogin(auth.getName());
		koszyk = zbiorczyService.zmianaFormatu2(koszyk);
		model.addAttribute("koszyk", koszyk);
		model.addAttribute("iloscRekordow", koszyk.size());
		return "/koszyk";
	}
	
	@PostMapping(value = "/usunZkoszyka")
	public String usunZKoszyka(HttpServletRequest request, RedirectAttributes redir)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int id = zbiorczyService.zwrocId(request);
		if(id != 0)
		{
			List<Koszyk> koszyk = koszykRepository.findByUzytkownikLoginAndPotrawaId(auth.getName(), id);
			koszykRepository.delete(koszyk.get(0));
			redir.addAttribute("potUsunieta", 1);
		}
		return "redirect:/koszyk";
	}
	
	@PostMapping(value = "/usunWszystko")
	public String oproznijKoszyk(HttpServletRequest request, RedirectAttributes redir)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Koszyk> koszyk = koszykRepository.findByUzytkownikLogin(auth.getName());
		koszykRepository.deleteAll(koszyk);
		redir.addAttribute("koszykPusty", 1);
		return "redirect:/koszyk";
	}
	
	@PostMapping(value = "/b")
	public String przeniesNaPromocje(@ModelAttribute("Potrawa") Potrawa potrawa, HttpServletRequest request, BindingResult result, RedirectAttributes redir, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Potrawa> potrawaWlasciwa = new ArrayList<Potrawa>();
		potrawaWlasciwa = potrawaRepository.findById(potrawa.getId());
		new PotrawaValidator(1).validate(potrawa, result);
		if(result.hasErrors())
		{
			List<Potrawa> potrawy = new ArrayList<Potrawa>();
			potrawy = zbiorczyService.szukaniePotraw(request);
			model.addAttribute("potrawy", potrawy);
			List<String> listaRodzajow = zbiorczyService.stworzListeRodzajow();
			listaRodzajow.add("wszystkie");
			Collections.swap(listaRodzajow, 0, listaRodzajow.size() - 1);
			model.addAttribute("lista", listaRodzajow);
			model.addAttribute("uzytkownik", auth.getName());
			model.addAttribute("Komentarz", new Komentarz());
			model.addAttribute("numerPotrawy", 0);
			model.addAttribute("Koszyk", new Koszyk());
			model.addAttribute("numerPotrawyKoszyk", 0);
			model.addAttribute("numerPotrawyPromocja", potrawa.getId());
			List<Zamowienie> niezatwierdzoneZamowienia = zamowienieRepository.findByCzyManagerJeWidzialFalse();
			if(niezatwierdzoneZamowienia.size() > 0)
			{
				model.addAttribute("noweZamowienia", true);
			}
			model.addAttribute("iloscRekordow", potrawy.size());
			return "main";
		}
		else
		{
			potrawaWlasciwa.get(0).setCenaPromocyjna(potrawa.getCenaPromocyjna());
			potrawaWlasciwa.get(0).setCzyPromocja(true);
			potrawaRepository.save(potrawaWlasciwa.get(0));
			redir.addAttribute("promocja", 1);
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/listaUzytk", method = RequestMethod.GET) //dodac to do navbara
	public String pokazUzytkownikow(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Uzytkownik> uzytkownicy = uzytkownikRepository.findByLoginNot(auth.getName());
		for(int i = 0; i < uzytkownicy.size(); i++)
		{
			String rola = uzytkownicy.get(i).getRola().getRola();
			uzytkownicy.get(i).getRola().setRola(rola.replace("ROLE_", ""));
		}
		model.addAttribute("Uzytkownik", new Uzytkownik());
		model.addAttribute("numerUzytk", 0);
		model.addAttribute("uzytkownik", auth.getName());
		model.addAttribute("uzytkownicy", uzytkownicy);
		model.addAttribute("iloscRekordow", uzytkownicy.size());
		return "/listaUzytkownikow";
	}
	
	@RequestMapping(value = "/konto", method = RequestMethod.GET)
	public String konto(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		List<Uzytkownik> dane = uzytkownikRepository.findByLogin(auth.getName());	
		model.addAttribute("konto", dane.get(0));
		return "/konto";
	}
	
	@RequestMapping(value = "/edytuj", method = RequestMethod.GET)
	public ModelAndView edytujKonto(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		List<Uzytkownik> uzyt = uzytkownikRepository.findByLogin(auth.getName());
		model.addAttribute("wartosci", uzyt.get(0));
		return new ModelAndView("edytuj", "dane", new Uzytkownik());
	}
	
	@PostMapping(value = "/edytuj")
	public String zmienDane(@Valid @ModelAttribute("dane") Uzytkownik uzytkownik, HttpServletRequest request, BindingResult result, RedirectAttributes redir, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Uzytkownik> uzytk = uzytkownikRepository.findByLogin(auth.getName());
		boolean czyTelefonIstnieje = false;
		if(uzytk.get(0).getTelefon().equals(uzytkownik.getTelefon()))
		{
			czyTelefonIstnieje = false;
		}
		else
		{
			czyTelefonIstnieje = zbiorczyService.czyTelefonIstnieje(uzytkownik.getTelefon());
		}
		boolean czyHasloTakieSamo = bcp.matches(uzytkownik.getHaslo(), uzytk.get(0).getHaslo());
		new UzytkownikValidator(false, czyTelefonIstnieje, czyHasloTakieSamo).validate2(uzytkownik, result);
		if(result.hasErrors())
		{
			model.addAttribute("uzytkownik", auth.getName());
			List<Uzytkownik> uzyt = uzytkownikRepository.findByLogin(auth.getName());
			model.addAttribute("wartosci", uzyt.get(0));
			return "edytuj";
		}
		else
		{
			if(uzytkownik.getHaslo().length() > 0)
			{
				uzytk.get(0).setHaslo(bcp.encode(uzytkownik.getHaslo()));
			}
			uzytk.get(0).setTelefon(uzytkownik.getTelefon());
			redir.addAttribute("udanaEdycja", 1);
			uzytkownikRepository.save(uzytk.get(0));
		}
		return "redirect:/";
	}
	
	@PostMapping(value = "/zbanuj")
	public String zbanuj(@Valid @ModelAttribute("Uzytkownik") Uzytkownik uzytkownik, HttpServletRequest request, BindingResult result, RedirectAttributes redir, Model model)
	{
		List<Uzytkownik> uzytkownik2 = uzytkownikRepository.findById(uzytkownik.getId());
		new UzytkownikValidator().walidacjaKomentarza(uzytkownik, result);
		if(result.hasErrors())
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			model.addAttribute("uzytkownik", auth.getName());
			List<Uzytkownik> uzytkownicy = uzytkownikRepository.findByLoginNot(auth.getName());
			for(int i = 0; i < uzytkownicy.size(); i++)
			{
				String rola = uzytkownicy.get(i).getRola().getRola();
				uzytkownicy.get(i).getRola().setRola(rola.replace("ROLE_", ""));
			}
			model.addAttribute("Uzytkownik", uzytkownik);
			model.addAttribute("numerUzytk", uzytkownik.getId());
			model.addAttribute("uzytkownicy", uzytkownicy);
			model.addAttribute("iloscRekordow", uzytkownicy.size());
			return "listaUzytkownikow";
		}
		else
		{
			uzytkownik2.get(0).setCzyAktywny(false);
			uzytkownik2.get(0).setKomentarz(uzytkownik.getKomentarz());
			redir.addAttribute("zbanowany", 1);
			uzytkownikRepository.save(uzytkownik2.get(0));
		}
		return "redirect:/listaUzytk";
	}
	
	@PostMapping(value = "/odbanuj")
	public String odbanuj(HttpServletRequest request, RedirectAttributes redir, Model model)
	{
		int id = zbiorczyService.zwrocId(request);
		if(id != 0)
		{
			List<Uzytkownik> uzytkownik2 = uzytkownikRepository.findById(id);
			uzytkownik2.get(0).setCzyAktywny(true);
			uzytkownik2.get(0).setKomentarz(" ");
			redir.addAttribute("odbanowany", 1);
			uzytkownikRepository.save(uzytkownik2.get(0));
		}
		return "redirect:/listaUzytk";
	}
	
	//usuwanie promocji o polnocz
	@Scheduled(cron = "0 0 0 * * ?")
	public void usunPromocjeCo24h()
	{
		List<Potrawa> potrawy = new ArrayList<Potrawa>();
		potrawy = potrawaRepository.findByCzyPromocjaTrue();
		for(Potrawa p: potrawy)
		{
			p.setCzyPromocja(false);
			p.setCenaPromocyjna(0);
		}
		potrawaRepository.saveAll(potrawy);
	}
}