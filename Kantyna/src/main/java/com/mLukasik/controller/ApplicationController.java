package com.mLukasik.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mLukasik.model.Rola;
import com.mLukasik.model.Stolik;
import com.mLukasik.model.Komentarz;
import com.mLukasik.model.Parametry;
import com.mLukasik.model.Uzytkownik;
import com.mLukasik.model.Zamowienie;
import com.mLukasik.model.Potrawa;
import com.mLukasik.model.Potrawy_Zamowienia;
import com.mLukasik.model.RodzajPotrawy;
import com.mLukasik.repository.KomentarzRepository;
import com.mLukasik.repository.ParametryRepository;
import com.mLukasik.repository.PotrawaRepository;
import com.mLukasik.repository.RodzajPotrawyRepository;
import com.mLukasik.repository.RolaRepository;
import com.mLukasik.repository.StolikRepository;
import com.mLukasik.repository.UzytkownikRepository;
import com.mLukasik.repository.ZamowienieRepository;
import com.mLukasik.validator.KomentarzValidator;
import com.mLukasik.validator.ParametryValidator;
import com.mLukasik.validator.PotrawaValidator;
import com.mLukasik.validator.StolikValidator;
import com.mLukasik.validator.UzytkownikValidator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
	
	@RequestMapping("/")
	public String Start(Model model, HttpServletRequest request)
	{
		List<Potrawa> potrawy = potrawaRepository.findAll();
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
		model.addAttribute("potrawy", potrawy);
		model.addAttribute("Komentarz", new Komentarz());
		List<RodzajPotrawy> listaRodzajow = new ArrayList<RodzajPotrawy>();
	    listaRodzajow = rodzajPotrawyRepository.findAll();
		List<String> lista = new ArrayList<String>();
		lista.add("wszystkie");
		for(RodzajPotrawy rp: listaRodzajow)
		{
			lista.add(rp.getRodzaj());
		}
		model.addAttribute("lista", lista);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		return "main";
	}
	
	@RequestMapping("/logowanie")
	public String Logowanie(Model model, HttpServletRequest request)
	{
		return "logowanie";
	}
	
	@RequestMapping(value = "/rejestracja", method = RequestMethod.GET)
	public ModelAndView Rejestracja(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    model.addAttribute("user", auth.getName());
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("KLIENT");
		lista.add("MANAGER");
		lista.add("ADMIN");
		model.addAttribute("lista", lista);
		return new ModelAndView("rejestracja", "Uzytkownik", new Uzytkownik());
	}
	
	@PostMapping("/rejestracja")
	public String zarejestruj(@Valid @ModelAttribute("Uzytkownik") Uzytkownik uzytkownik, BindingResult result, RedirectAttributes redir, ModelMap map, Model model)
	{
		List<Uzytkownik> listaU = uzytkownikRepository.findByLogin(uzytkownik.getLogin());
		List<Uzytkownik> listaU2 = uzytkownikRepository.findByTelefon(uzytkownik.getTelefon());
		boolean czyLoginIstnieje = false;
		boolean czyTelefonIstnieje = false;
		if(listaU.size() > 0 )
		{
			czyLoginIstnieje = true;
		}
		if(listaU2.size() > 0 )
		{
			czyTelefonIstnieje = true;
		}
		new UzytkownikValidator(czyLoginIstnieje, czyTelefonIstnieje).validate(uzytkownik, result);
		if(result.hasErrors())
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    model.addAttribute("uzytkownik", auth.getName());
			ArrayList<String> lista = new ArrayList<String>();
			lista.add("KLIENT");
			lista.add("MANAGER");
			model.addAttribute("lista", lista);
			return "rejestracja";
		}
		
		if(uzytkownik.getRola() != null)
		{
			if(uzytkownik.getRola().getRola().equals("MANAGER"))
			{
				Rola rola = rolaRepository.findByRola("ROLE_MANAGER").get(0);
				uzytkownik.setRola(rola);
			}
			else if(uzytkownik.getRola().getRola().equals("KLIENT"))
			{
				Rola rola = rolaRepository.findByRola("ROLE_KLIENT").get(0);
				uzytkownik.setRola(rola);
			}
			
		}
		else
		{
			List<Rola> role = rolaRepository.findByRola("ROLE_KLIENT");
			uzytkownik.setRola(role.get(0));
		}
		uzytkownik.setCzy_aktywny(true);
		uzytkownik.setHaslo(bcp.encode(uzytkownik.getHaslo()));
		uzytkownikRepository.save(uzytkownik);
		redir.addAttribute("regSuccess", 1);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/parametry", method = RequestMethod.GET)
	public ModelAndView Parametry(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		List<Parametry> listaP = parametryRepository.findByIdParametru(1);
		if(listaP.size() > 0)
		{
			model.addAttribute("wartosci", listaP.get(0));
		}
		else
		{
			Parametry parametry = new Parametry();
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			Time godzinaOtwar;
			Time godzinaZam;
			try 
			{
				godzinaOtwar = new Time(formatter.parse("00:00").getTime());
				godzinaZam  = new Time(formatter.parse("00:00").getTime());
				parametry.setGodzinaOtwarcia(godzinaOtwar);
				parametry.setGodzinaZamkniecia(godzinaZam);
				parametry.setSzukanieStolika(0);
			}
			catch (ParseException e) 
			{
				System.out.println("Zly format");
			}
			model.addAttribute("wartosci", parametry);
		}
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
			String godzinaO = parametry.getGodzinaO();
			String godzinaZ = parametry.getGodzinaZ();
			System.out.println(godzinaO);
			System.out.println(godzinaZ);
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			Time godzinaOtwar;
			Time godzinaZam;
			try 
			{
				godzinaOtwar = new Time(formatter.parse(godzinaO).getTime());
				godzinaZam  = new Time(formatter.parse(godzinaZ).getTime());
				parametry.setGodzinaOtwarcia(godzinaOtwar);
				parametry.setGodzinaZamkniecia(godzinaZam);
			}
			catch (ParseException e) 
			{
				System.out.println("Zly format");
			}
			System.out.println(parametry.getGodzinaOtwarcia());
			System.out.println(parametry.getGodzinaZamkniecia());
			parametryRepository.save(parametry);
			return "redirect:/";
		}
	}
	
	//wykrywanie jezyka
	  // Locale locale = LocaleContextHolder.getLocale();
      // sessionLocaleResolver.setDefaultLocale(locale);
	
	//dokonczyc, aktualnie tworzy liste rodzajow potraw, zrobic widok do tego
	@RequestMapping(value = "/potrawa", method = RequestMethod.GET)
	public ModelAndView Potrawa(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		List<RodzajPotrawy> listaRP = rodzajPotrawyRepository.findAll();
		for(RodzajPotrawy rodzaj: listaRP )
		{
			System.out.println(rodzaj.getId());
			System.out.println(rodzaj.getRodzaj());
		}
		List<String> listaRodzajow = new ArrayList<String>();
		listaRodzajow.add(listaRP.get(0).getRodzaj());
		listaRodzajow.add(listaRP.get(1).getRodzaj());
		model.addAttribute("lista", listaRodzajow);
		return new ModelAndView("potrawa", "Potrawa", new Potrawa());
	}
	
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
		listastolikow = stolikRepository.findByNazwa(stolik.getNazwa());
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
		return "/stoliki";
	}
	
	@PostMapping(value = "/zwolnij")
	public String zwolnijStolik(RedirectAttributes redir, HttpServletRequest request)
	{
		boolean parsable = true;
		int id = 0;
		try
	    {
			id = Integer.parseInt(request.getParameter("par"));
	    }
		catch(Exception e)
		{
			parsable = false;
		}
		if(parsable == true)
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
	    List<RodzajPotrawy> listaRodzajow = new ArrayList<RodzajPotrawy>();
	    listaRodzajow = rodzajPotrawyRepository.findAll();
		List<String> lista = new ArrayList<String>();
		for(RodzajPotrawy rp: listaRodzajow)
		{
			lista.add(rp.getRodzaj());
		}
		model.addAttribute("lista", lista);
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
		listaPot = potrawaRepository.findByNazwa(potrawa.getNazwa());
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
			List<RodzajPotrawy> listaRodzajow = new ArrayList<RodzajPotrawy>();
		    listaRodzajow = rodzajPotrawyRepository.findAll();
			List<String> lista = new ArrayList<String>();
			for(RodzajPotrawy rp: listaRodzajow)
			{
				lista.add(rp.getRodzaj());
			}
			model.addAttribute("lista", lista);
			return "nowa-potrawa";
		}
		try 
		{
			potrawa.setZdjecie(potrawa.getObrazek().getBytes());
		} 
		catch (IOException e) 
		{
		}
		//zwraca rozszerzenie pliku
		System.out.println(FilenameUtils.getExtension(potrawa.getObrazek().getOriginalFilename()));
		redir.addAttribute("nowaPot", 1);
		potrawaRepository.save(potrawa);
		return "redirect:/";
	}
	
	@PostMapping(value = "/usunZmenu")
	public String zmienDostepnoscPotrawy(RedirectAttributes redir, HttpServletRequest request)
	{
		boolean parsable = true;
		int id = 0;
		try
	    {
			id = Integer.parseInt(request.getParameter("par"));
	    }
		catch(Exception e)
		{
			parsable = false;
		}
		if(parsable == true)
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
	
	//do zwalniania stolikow, w stolikach/zamowieniach trzeba bedzie dac czas od kiedy do kiedy jest zajety, zeby nie mozna bylo ich zajac i, zeby moglo pozniej zwolnic
	/*@Scheduled(fixedRate =  600000, initialDelay = 600000) //5minut
	public void zwolnijStoliki()
	{
		List<Stolik> stoliki = stolikRepository.findByCzyJestZajety(true);
		for(Stolik stolik: stoliki)
		{
			stolik.setCzyJestZajety(false);
			stolikRepository.save(stolik);
		}
	}*/
	
	//zwalnianie stolikow o polnocy
	@Scheduled(cron = "0 0 0 * * ?")
	public void zwolnijStolikiCo24h()
	{
		List<Stolik> stoliki = stolikRepository.findByCzyJestZajety(true);
		for(Stolik stolik: stoliki)
		{
			stolik.setCzyJestZajety(false);
			stolikRepository.save(stolik);
		}
	}
	
	//komentarz.jsp zastapione modalem na main page
	/*@RequestMapping(value = "/komentarz", method = RequestMethod.GET)
	public ModelAndView nowyKom(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		model.addAttribute("potrawa", 9);
		return new ModelAndView("komentarz", "Komentarz", new Komentarz());
	}*/
	
	//dokonczyc, jesli zadna gwiazdka nie jest zaznaczona to ocena = null
	@PostMapping(value = "/dodajKom")
	public String dodajKomentarz(@ModelAttribute("Komentarz") Komentarz komentarz, BindingResult result, RedirectAttributes redir, Model model)
	{
		new KomentarzValidator().validate(komentarz, result);
		if(result.hasErrors())
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			model.addAttribute("uzytkownik", auth.getName());
			return "komentarz";
		}
		System.out.println("Ocena = " + komentarz.getJakaOcena());
		System.out.println("Kom = " + komentarz.getKomentarz());
		komentarz.setOcena(Integer.parseInt(komentarz.getJakaOcena()));
		List<Uzytkownik> listaU = uzytkownikRepository.findAll();
		komentarz.setUzytkownik(listaU.get(0));
		List<Potrawa> listaP = potrawaRepository.findById(komentarz.getIdPotrawy());
		komentarz.setPotrawa(listaP.get(0));
		System.out.println("ID = " + komentarz.getPotrawa().getId());
		//test Optional
		/*Optional<Uzytkownik> u = uzytkownikRepository.findByLogin("2a");
		Uzytkownik uzyt = u.get();
		System.out.println("LOGIN: " + uzyt.getLogin());*/
		redir.addAttribute("nowyKom", 1);
		komentarzRepository.save(komentarz);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/zamowienie", method = RequestMethod.GET)
	public ModelAndView noweZamowienie(HttpServletRequest request, Model model)
	{
		List<Potrawa> potrawy = potrawaRepository.findAll();
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
		model.addAttribute("potrawy", potrawy);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		return new ModelAndView("zamowienie", "Zamowienie", new Zamowienie());
	}
	
	//wstepnie dziala, dodac wlasciwa rezerwacje stolika i odczyt zalogowanego uzytkownika i validacje
	//uwaga co do braku komentarzy, albo disablowac przycisk, albo dorobic argument w funkcji w javascript i na podstawie tego wyswietlic komunikat, bez javascriptu niestety sie nie da
	@PostMapping(value = "/zamowienie")
	public String dodajZamowienie(@ModelAttribute("Zamowienie") Zamowienie zamowienie, BindingResult result, RedirectAttributes redir, Model model)
	{
		int ilosc = zamowienie.getIloscMiejsc();
		System.out.println("Ilosc miejsc = " + ilosc);
		List<Parametry> listaP = parametryRepository.findByIdParametru(1);
		int maxIlosc = ilosc + listaP.get(0).getSzukanieStolika();
		System.out.println("Ilosc miejsc MAX = " + maxIlosc);
		List<Stolik> listaStolikow = stolikRepository.findByIloscMiejscBetweenAndCzyJestZajetyOrderByIloscMiejscAsc(zamowienie.getIloscMiejsc(), maxIlosc, false);
		if(listaStolikow.size() > 0)
		{
			listaStolikow.get(0).setCzyJestZajety(true);
			stolikRepository.save(listaStolikow.get(0));
			zamowienie.setStolik(listaStolikow.get(0));
		}
		List<Potrawy_Zamowienia> lista = new ArrayList<Potrawy_Zamowienia>();
		Potrawy_Zamowienia pot = new Potrawy_Zamowienia();
		for(int i = 0; i < zamowienie.getListaIlosci().size(); i++)
		{
			if(zamowienie.getListaIlosci().get(i) != 0)
			{
				pot = new Potrawy_Zamowienia();
				int idPotrawy = zamowienie.getListaPotraw().get(i);
				pot.setIlosc(zamowienie.getListaIlosci().get(i));
				pot.setPotrawa(potrawaRepository.findById(idPotrawy).get(0));
				pot.setZamowienie(zamowienie);
				lista.add(pot);
			}
		}
		Date date = new Date();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		zamowienie.setUzytkownik(uzytkownikRepository.findByLogin(auth.getName()).get(0)); // w przyszlosci zastapic linijke ponizej tym
		//zamowienie.setUzytkownik(uzytkownikRepository.findByLogin("203123@edu.p.lodz.pl").get(0));
		zamowienie.setCzyKlientWidzialZatwierdzoneZamowienie(true);
		zamowienie.setCzyManagerJeWidzial(false);
		zamowienie.setCzyJestZatwierdzonePrzezanagera(false);
		zamowienie.setDataZam(date);
		zamowienie.setPotrawy_Zamowienia(lista);
		zamowienieRepository.save(zamowienie);
		return "redirect:/";
	}
}