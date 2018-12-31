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
import com.mLukasik.model.Parametry;
import com.mLukasik.model.Uzytkownik;
import com.mLukasik.model.Potrawa;
import com.mLukasik.model.RodzajPotrawy;
import com.mLukasik.repository.ParametryRepository;
import com.mLukasik.repository.PotrawaRepository;
import com.mLukasik.repository.RodzajPotrawyRepository;
import com.mLukasik.repository.RolaRepository;
import com.mLukasik.repository.StolikRepository;
import com.mLukasik.repository.UzytkownikRepository;
import com.mLukasik.validator.ParametryValidator;
import com.mLukasik.validator.UzytkownikValidator;

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
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class ApplicationController 
{
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
	
	
	@RequestMapping("/main")
	public String Welcome(Model model, HttpServletRequest request)
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("potrawy", potrawy);
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
		uzytkownikRepository.save(uzytkownik);
		redir.addAttribute("regSuccess", 1);
		return "redirect:/main";
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
		parametry.setIdParametru(listaP.get(0).getIdParametru());
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
			return "redirect:/main";
		}
	}
	
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
	
	//do testow i ogarniecia logik irezerwacji stolikow, pozniej wsadzic to do funkcji odpowiedzialnej za zamowienia
	//
	//
	@RequestMapping(value = "/stoliki", method = RequestMethod.GET)
	public ModelAndView Stoliki(HttpServletRequest request, Model model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("uzytkownik", auth.getName());
		return new ModelAndView("stoliki", "Stolik", new Stolik());
	}
	
	@PostMapping(value = "/stoliki")
	public String szukajStolika(@Valid @ModelAttribute("Stolik") Stolik stolik, BindingResult result, RedirectAttributes redir, ModelMap map, Model model)
	{
		int ilosc = stolik.getIloscMiejsc();
		System.out.println("Ilosc miejsc = " + ilosc);
		List<Parametry> listaP = parametryRepository.findByIdParametru(1);
		int maxIlosc = ilosc + listaP.get(0).getSzukanieStolika();
		System.out.println("Ilosc miejsc MAX = " + maxIlosc);
		List<Stolik> listaStolikow = stolikRepository.findByIloscMiejscBetweenOrderByIloscMiejscAsc(stolik.getIloscMiejsc(), maxIlosc);
		if(listaStolikow.size() > 0)
		{
			listaStolikow.get(0).setCzyJestZajety(true);
			stolikRepository.save(listaStolikow.get(0));
			for(Stolik stol: listaStolikow)
			{
				System.out.println(stol.getNazwa());
				System.out.println(stol.getIloscMiejsc());
				System.out.println(stol.getCzyJestZajety());
			}
		}
		else
		{
			System.out.println("Nie znaleziono stolika");
		}
		//najpierw sprawdzic czy dziala, jak nie zadziala poprawic/dokonczyc
		return "redirect:/main";
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
	
	//do zwalniania stolikow, w stolikach/zamowieniach trzeba bedzie dac czas od kiedy do kiedy jest zajety, zeby nie mozna bylo ich zajac i, zeby moglo pozniej zwolnic
	@Scheduled(fixedRate =  600000) //5minut
	public void zwolnijStoliki()
	{
		List<Stolik> stoliki = stolikRepository.findByCzyJestZajety(true);
		for(Stolik stolik: stoliki)
		{
			stolik.setCzyJestZajety(false);
			stolikRepository.save(stolik);
		}
	}
	
	//dokonczyc + sprawdzac czy plik jest obrazem
	@PostMapping(value = "/nowa-potrawa")
	public String dodajPotrawe(@ModelAttribute("Potrawa") Potrawa potrawa, BindingResult result, RedirectAttributes redir, ModelMap map, Model model)
	{
		List<RodzajPotrawy> rodzaj = rodzajPotrawyRepository.findByRodzaj(potrawa.getRodzajPot());
		potrawa.setRodzajPotrawy(rodzaj.get(0));
		potrawa.setCzyJestDostepna(true);
		//zwraca rozszerzenie pliku
		System.out.println(FilenameUtils.getExtension(potrawa.getObrazek().getOriginalFilename()));
		try 
		{
			potrawa.setZdjecie(potrawa.getObrazek().getBytes());
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		potrawaRepository.save(potrawa);
		
		return "redirect:/main";
	}
}