package com.mLukasik.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mLukasik.model.Komentarz;
import com.mLukasik.model.Parametry;
import com.mLukasik.model.Potrawa;
import com.mLukasik.model.Uzytkownik;
import com.mLukasik.repository.KomentarzRepository;
import com.mLukasik.repository.ParametryRepository;
import com.mLukasik.repository.PotrawaRepository;
import com.mLukasik.repository.UzytkownikRepository;

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
	
	
	//trzeba dopisac metody typu get: pobieranie parametrow, stolikow, zamowien, koszyka
	//trzeba dopisac metody typu post: dodawanie do koszyka, tworzenie zamowienia, usuwanie z koszyka wszystkiego i jednej pozycji
	@RequestMapping(value = "/api/hello", method = RequestMethod.GET)
	public String hello()
	{
		return "This is Home page";
	}
	
	//jako tako dziala, przesylanie obrazkow niestety niszczy
	@RequestMapping(value = "/api/menu", method = RequestMethod.GET)
	public ResponseEntity test()
	{
		List<Potrawa> potrawy = new ArrayList<Potrawa>();
		potrawy = potrawaRepository.findAll();
		return ResponseEntity.ok(potrawy); //mozna zwrocic tylko jedna liste obiektow
	}
	
	@RequestMapping(value = "/api/menu22", method = RequestMethod.GET)
	public ResponseEntity test2()
	{
		List<Potrawa> potrawy = new ArrayList<Potrawa>();
		potrawy = potrawaRepository.findAll();
		return ResponseEntity.ok(potrawy.get(0));
	}
	
	 @PostMapping(value = "/api/addComment")
	 public ResponseEntity createUser(@RequestBody Komentarz komentarz, UriComponentsBuilder ucBuilder) 
	 {
		 /*System.out.println(komentarz.getKomentarz());
		 System.out.println(komentarz.getOcena());
		 System.out.println(komentarz.getIdUzytkownika());
		 System.out.println(komentarz.getIdPotrawy());*/
		 List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
		 uzytkownik	= uzytkownikRepository.findById(komentarz.getIdUzytkownika());
		 komentarz.setUzytkownik(uzytkownik.get(0));
		 List<Potrawa> potrawa = new ArrayList<Potrawa>();
		 potrawa = potrawaRepository.findById(komentarz.getIdPotrawy());
		 komentarz.setPotrawa(potrawa.get(0));
		 Komentarz kom = komentarzRepository.save(komentarz);
		 URI location = ucBuilder.path("/api/komentarz/{id}").buildAndExpand(kom.getId()).toUri();
	     return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Udane dodanie komentarza");
	     //dodaje pole MyResponseHeader z wartoscia MyValue do odpowiedzi, cialo odpowiedzi to Hello World
	 }
}