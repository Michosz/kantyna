package com.mLukasik.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mLukasik.model.Komentarz;
import com.mLukasik.model.Koszyk;
import com.mLukasik.model.Parametry;
import com.mLukasik.model.Potrawa;
import com.mLukasik.model.Stolik;
import com.mLukasik.model.Uzytkownik;
import com.mLukasik.model.Zamowienie;
import com.mLukasik.repository.KomentarzRepository;
import com.mLukasik.repository.KoszykRepository;
import com.mLukasik.repository.ParametryRepository;
import com.mLukasik.repository.PotrawaRepository;
import com.mLukasik.repository.StolikRepository;
import com.mLukasik.repository.UzytkownikRepository;
import com.mLukasik.repository.ZamowienieRepository;
import com.mLukasik.validator.KomentarzValidator;

@RestController
public class MobController
{
	@Autowired
	PotrawaRepository potrawaRepository;
	@Autowired
	UzytkownikRepository uzytkownikRepository;
	@Autowired
	KomentarzRepository komentarzRepository;
	@Autowired
	ParametryRepository parametryRepository;
	@Autowired
	StolikRepository stolikRepository;
	@Autowired
	ZamowienieRepository zamowienieRepository;
	@Autowired
	KoszykRepository koszykRepository;
	@Autowired
	private MessageSource messageSource;
	
	//pobieranie parametrow i stolikow - wstepnie zrobione,
	//trzeba dopisac metody typu get: pobieranie zamowien, koszyka - wstepnie zrobione
	//trzeba dopisac metody typu post: dodawanie do koszyka, tworzenie zamowienia, usuwanie z koszyka wszystkiego i jednej pozycji
	
	//jako tako dziala, przesylanie obrazkow niestety niszczy
	@RequestMapping(value = "/api/menu", method = RequestMethod.GET)
	public ResponseEntity menu()
	{
		List<Potrawa> potrawy = new ArrayList<Potrawa>();
		potrawy = potrawaRepository.findAll();
		return ResponseEntity.ok(potrawy); //mozna zwrocic tylko jedna liste obiektow
	}

	@RequestMapping(value = "/api/parametry", method = RequestMethod.GET)
	public ResponseEntity parametry()
	{
		List<Parametry> parametry = new ArrayList<Parametry>();
		parametry = parametryRepository.findByIdParametru(1);
		return ResponseEntity.ok(parametry.get(0));
	}
	
	@RequestMapping(value = "/api/stoliki", method = RequestMethod.GET)
	public ResponseEntity stoliki()
	{
		List<Stolik> stoliki = new ArrayList<Stolik>();
		stoliki = stolikRepository.findAll();
		return ResponseEntity.ok(stoliki);
	}
	
	@RequestMapping(value = "/api/zamowienia", method = RequestMethod.GET)
	public ResponseEntity zamowieniaKlienta()
	{
		List<Zamowienie> zamowienia = new ArrayList<Zamowienie>();
		zamowienia = zamowienieRepository.findAll();
		return ResponseEntity.ok(zamowienia);
	}
	
	@RequestMapping(value = "/api/koszyk/{login}", method = RequestMethod.GET)
	public ResponseEntity koszykKlienta(@PathVariable("login") String login)
	{
		List<Koszyk> koszyk = new ArrayList<Koszyk>();
		koszyk = koszykRepository.findByUzytkownikLogin(login);
		return ResponseEntity.ok(koszyk);
	}
	
	 @PostMapping(value = "/api/addComment")
	 public ResponseEntity dodajKomentarz(@RequestBody Komentarz komentarz, UriComponentsBuilder ucBuilder, BindingResult result) 
	 {
		 /*System.out.println(komentarz.getKomentarz());
		 System.out.println(komentarz.getOcena());
		 System.out.println(komentarz.getIdUzytkownika());
		 System.out.println(komentarz.getIdPotrawy());*/
		 new KomentarzValidator().validate(komentarz, result);
		 System.out.println(result.getAllErrors().size());
		 if(result.hasErrors())
		 {
			 List<String> bledy = new ArrayList<>();
			 System.out.println("JESTEM");
			 for(int i = 0; i < result.getAllErrors().size(); i++)
			 {
				 bledy.add((messageSource.getMessage(result.getAllErrors().get(i).getCode(), null, new Locale("PL"))));
			 }
			 return ResponseEntity.accepted().body(bledy);
		 }
		 else
		 {
			 List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
			 uzytkownik	= uzytkownikRepository.findById(komentarz.getIdUzytkownika());
			 komentarz.setUzytkownik(uzytkownik.get(0));
			 List<Potrawa> potrawa = new ArrayList<Potrawa>();
			 potrawa = potrawaRepository.findById(komentarz.getIdPotrawy());
			 komentarz.setPotrawa(potrawa.get(0));
			 komentarz.setOcena(Integer.parseInt(komentarz.getJakaOcena()));
			 Komentarz kom = komentarzRepository.save(komentarz);
			 URI location = ucBuilder.path("/api/komentarz/{id}").buildAndExpand(kom.getId()).toUri();
		     return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Udane dodanie komentarza");
		 }
	     //dodaje pole MyResponseHeader z wartoscia MyValue do odpowiedzi, cialo odpowiedzi to Hello World
	 }
}
