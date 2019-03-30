package com.mLukasik.controller;

import java.net.URI;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.mLukasik.model.Potrawy_Zamowienia;
import com.mLukasik.model.RodzajPotrawy;
import com.mLukasik.model.Rola;
import com.mLukasik.model.Stolik;
import com.mLukasik.model.Uzytkownik;
import com.mLukasik.model.Zamowienie;
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
import com.mLukasik.validator.UzytkownikValidator;
import com.mLukasik.validator.ZamowienieValidator;

@RestController
public class MobController
{
	private BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
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
	RolaRepository rolaRepository;
	@Autowired
	RodzajPotrawyRepository rodzajPotrawyRepository;
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	ZbiorczyService zbiorczyService;
	
	//pobieranie parametrow i stolikow - wstepnie zrobione,
	//trzeba dopisac metody typu get: pobieranie zamowien, koszyka - wstepnie zrobione
	//pobieranie informacji o koncie typu get do zrobienia
	
	//wstepnie zrobione metody post: dodawanie do koszyka, usuwanie z koszyka wszystkiego i jednej pozycji
	//trzeba dopisac metody typu post: tworzenie zamowienia, modyfikowanie telefonu i hasla
	
	@RequestMapping(value = "/api/menu", method = RequestMethod.GET)
	public ResponseEntity menu()
	{
		List<Potrawa> potrawy = new ArrayList<Potrawa>();
		potrawy = potrawaRepository.findAll();
		for(int i = 0; i < potrawy.size(); i++)
		{
			if(potrawy.get(i).getListaKomentarzy().size() > 0)
			{
				potrawy.get(i).setCzySaKomentarze(true);
			}
			else
			{
				potrawy.get(i).setCzySaKomentarze(false);
			}
		}
		return ResponseEntity.ok(potrawy); //mozna zwrocic tylko jedna liste obiektow
	}
	
	@RequestMapping(value = "/api/rodzaje", method = RequestMethod.GET)
	public ResponseEntity listaRodzajow()
	{
		List<RodzajPotrawy> rodzaje = new ArrayList<RodzajPotrawy>();
		rodzaje = rodzajPotrawyRepository.findAll();
		return ResponseEntity.ok(rodzaje);
	}
	
	@RequestMapping(value = "/api/komentarze/{idPot}", method = RequestMethod.GET)
	public ResponseEntity pokazKomentarze(@PathVariable("idPot") int idPot)
	{
		List<Komentarz> komentarze = new ArrayList<Komentarz>();
		komentarze = komentarzRepository.findByPotrawaId(idPot);
		return ResponseEntity.ok(komentarze);
	}
	
	@RequestMapping(value = "/api/menu2", method = RequestMethod.GET)
	public ResponseEntity menu2()
	{
		List<Potrawa> potrawy = new ArrayList<Potrawa>();
		potrawy = potrawaRepository.findAll();
		List<Potrawa> potrawy2 = new ArrayList<Potrawa>();
		for(int i = 0; i < 2; i++)
		{
			potrawy2.add(potrawy.get(i));
		}
		return ResponseEntity.ok(potrawy2); //mozna zwrocic tylko jedna liste obiektow
	}
	
	@RequestMapping(value = "/api/filtrowanieMenu/{nazwa}/{rodzaj}", method = RequestMethod.GET)
	public ResponseEntity filtrowanieMenu(@PathVariable("nazwa") String nazwa, @PathVariable("rodzaj") String rodzaj)
	{
		//przetestowac
		List<Potrawa> potrawy = new ArrayList<Potrawa>();
		if(rodzaj != null)
		{
			if(rodzaj.equalsIgnoreCase("wszystkie"))
			{
				potrawy = potrawaRepository.findAll();
			}
			else
			{
				potrawy = potrawaRepository.findByRodzajPotrawyRodzaj(rodzaj);
			}
		}
		else
		{
			potrawy = potrawaRepository.findAll();
		}
		List<Potrawa> potrawy2 = new ArrayList<Potrawa>();
		
		if(nazwa != null && !nazwa.equals(" "))
		{
			for(int i = 0; i < potrawy.size(); i++)
			{
				if((potrawy.get(i).getNazwa().toLowerCase().contains(nazwa)))
				{
					potrawy2.add(potrawy.get(i));
				}
			}
		}
		else
		{
			potrawy2 = potrawy;
		}
		return ResponseEntity.ok(potrawy2);
	}

	@RequestMapping(value = "/api/parametry", method = RequestMethod.GET)
	public ResponseEntity parametry()
	{
		List<Parametry> parametry = new ArrayList<Parametry>();
		parametry = parametryRepository.findByIdParametru(1);
		return ResponseEntity.ok(parametry.get(0));
	}
	
	@RequestMapping(value = "/api/konto", method = RequestMethod.GET)
	public ResponseEntity konto()
	{
		List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
		uzytkownik = uzytkownikRepository.findAll();
		
		List<Uzytkownik> dane = new ArrayList<Uzytkownik>();
		dane = uzytkownikRepository.findByLogin(uzytkownik.get(0).getLogin());
		return ResponseEntity.ok(dane.get(0));
	}
	
	@PostMapping(value = "/api/edytuj/{jezyk}")
	public ResponseEntity edytuj(@PathVariable("jezyk") String jezyk, @RequestBody Uzytkownik uzytkownik, UriComponentsBuilder ucBuilder, BindingResult result)
	{
		List<Uzytkownik> uzytkownik12 = new ArrayList<Uzytkownik>();
		uzytkownik12 = uzytkownikRepository.findAll();
		
		List<Uzytkownik> uzytk = uzytkownikRepository.findByLogin(uzytkownik12.get(0).getLogin());
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
			List<String> bledy = new ArrayList<>();
			for(int i = 0; i < result.getAllErrors().size(); i++)
			{
				bledy.add((messageSource.getMessage(result.getAllErrors().get(i).getCode(), null, new Locale(jezyk))));
			}
			return ResponseEntity.accepted().body(bledy);
		}
		else
		{
			if(uzytkownik.getHaslo().length() > 0)
			{
				uzytk.get(0).setHaslo(bcp.encode(uzytkownik.getHaslo()));
			}
			uzytk.get(0).setTelefon(uzytkownik.getTelefon());
			uzytkownikRepository.save(uzytk.get(0));
			URI location = ucBuilder.path("/api/komentarz/{id}").buildAndExpand(1).toUri();
			return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body(messageSource.getMessage("api.UdanaEdycja", null, new Locale(jezyk)));
		}
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
	
	@PostMapping(value = "/api/zlozZamowienie/{jezyk}")
	public ResponseEntity zlozZamowienie(@PathVariable("jezyk") String jezyk, @RequestBody Zamowienie zamowienie, UriComponentsBuilder ucBuilder, BindingResult result) 
	{
		List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
		uzytkownik = uzytkownikRepository.findAll();
		
		boolean pusteZamowienie = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		int ilosc = zamowienie.getIloscMiejsc();
		List<Parametry> listaP = parametryRepository.findByIdParametru(1);
		List<Parametry> param = parametryRepository.findAll();
		int maxIlosc = ilosc + listaP.get(0).getSzukanieStolika();
		int iloscStolikow = 0;
		List<Stolik> listaStolikow = stolikRepository.findByIloscMiejscBetweenAndCzyJestZajetyOrderByIloscMiejscAsc(zamowienie.getIloscMiejsc(), maxIlosc, false);
		List<Koszyk> koszyk = koszykRepository.findByUzytkownikLogin(uzytkownik.get(0).getLogin());
		if(koszyk.size() == 0)
		{
			pusteZamowienie = true;
		}
		iloscStolikow = listaStolikow.size();

		if(listaStolikow.size() == 0 && param.get(0).getCzyAutoZwalniac() == true)
		{
			List<Zamowienie> zamowienia = new ArrayList<Zamowienie>();
			zamowienia = zamowienieRepository.findByCzyZrealizowaneFalseAndStolikIloscMiejscBetweenOrderByStolikIloscMiejscAsc(ilosc, maxIlosc);

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

		new ZamowienieValidator(iloscStolikow, pusteZamowienie, listaP.get(0), koszyk).validate(zamowienie, result);
		if(result.hasErrors())
		{
			List<String> bledy = new ArrayList<>();
			for(int i = 0; i < result.getAllErrors().size(); i++)
			{
				bledy.add((messageSource.getMessage(result.getAllErrors().get(i).getCode(), null, new Locale(jezyk))));
			}
			return ResponseEntity.accepted().body(bledy);
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
			LocalTime czas = LocalTime.parse(zamowienie.getCzasReal());
			zamowienie.setCzasRealizacji(czas);
			Date date = new Date();
			zamowienie.setUzytkownik(uzytkownikRepository.findByLogin(uzytkownik.get(0).getLogin()).get(0));
			zamowienie.setCzyManagerJeWidzial(false);
			zamowienie.setDataZam(date);
			zamowienie.setPotrawy_Zamowienia(lista);
			zamowienieRepository.save(zamowienie);
			URI location = ucBuilder.path("/api/komentarz/{id}").buildAndExpand(zamowienie.getIloscMiejsc()).toUri();
			return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body(messageSource.getMessage("api.ZamowienieZlozone", null, new Locale(jezyk)));
		}
	}
	
	@RequestMapping(value = "/api/koszyk/{login}", method = RequestMethod.GET)
	public ResponseEntity koszykKlienta(@PathVariable("login") String login)
	{
		List<Koszyk> koszyk = new ArrayList<Koszyk>();
		koszyk = koszykRepository.findByUzytkownikLogin(login);
		return ResponseEntity.ok(koszyk);
	}
	
	@PostMapping(value = "/api/dodajDoKoszyka/{jezyk}")
	public ResponseEntity dodajDoKoszyka(@PathVariable("jezyk") String jezyk, @RequestBody Koszyk koszyk, UriComponentsBuilder ucBuilder, BindingResult result) 
	{
		List<Potrawa> potrawa = new ArrayList<Potrawa>();
		potrawa = potrawaRepository.findById(koszyk.getIdPotrawy());
		new KoszykValidator().validate(koszyk, result);
		if(result.hasErrors())
		{
			List<String> bledy = new ArrayList<>();
			for(int i = 0; i < result.getAllErrors().size(); i++)
			{
				bledy.add((messageSource.getMessage(result.getAllErrors().get(i).getCode(), null, new Locale(jezyk))));
			}
			return ResponseEntity.accepted().body(bledy);
		}
		else
		{
			Koszyk kosz;
			List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
			uzytkownik = uzytkownikRepository.findAll();
			List<Koszyk> czyJuzMaPotraweWkoszyku = new ArrayList<Koszyk>();
			czyJuzMaPotraweWkoszyku = koszykRepository.findByPotrawaNazwaAndUzytkownikLogin(potrawa.get(0).getNazwa(), uzytkownik.get(0).getLogin());
			if(czyJuzMaPotraweWkoszyku.size() > 0)
			{
				int nowaIlosc = czyJuzMaPotraweWkoszyku.get(0).getIlosc() + koszyk.getIlosc();
				czyJuzMaPotraweWkoszyku.get(0).setIlosc(nowaIlosc);
				kosz = koszykRepository.save(czyJuzMaPotraweWkoszyku.get(0));
			}
			else
			{
				koszyk.setUzytkownik(uzytkownik.get(0));
				koszyk.setPotrawa(potrawa.get(0));
				kosz = koszykRepository.save(koszyk);
			}
			URI location = ucBuilder.path("/api/komentarz/{id}").buildAndExpand(koszyk.getId()).toUri();
			return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body(messageSource.getMessage("api.UdaneDodanieDoKoszyka", null, new Locale(jezyk)));
		}
	}
	
	@PostMapping(value = "/api/usunZkoszyka/{jezyk}")
	public ResponseEntity usunZKoszyka(@PathVariable("jezyk") String jezyk, @RequestBody Koszyk koszyk, UriComponentsBuilder ucBuilder, BindingResult result) 
	{
		List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
		uzytkownik = uzytkownikRepository.findAll(); //pierwszy jest uzytkownik o id = 1
		System.out.println(uzytkownik.get(0).getLogin());
		List<Koszyk> koszyk2 = koszykRepository.findByUzytkownikLoginAndPotrawaId(uzytkownik.get(0).getLogin(), koszyk.getIdPotrawy());
		koszykRepository.delete(koszyk2.get(0));
		URI location = ucBuilder.path("/api/komentarz/{id}").buildAndExpand(koszyk.getIdPotrawy()).toUri();
		return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body(messageSource.getMessage("api.UdaneUsuniecieZkoszyka", null, new Locale(jezyk)));
	}
	
	@PostMapping(value = "/api/usunWszystko/{jezyk}")
	public ResponseEntity usunZKoszyka(@PathVariable("jezyk") String jezyk, UriComponentsBuilder ucBuilder) 
	{
		List<Uzytkownik> uzytkownik = new ArrayList<Uzytkownik>();
		uzytkownik = uzytkownikRepository.findAll();
		List<Koszyk> koszyk = koszykRepository.findByUzytkownikLogin(uzytkownik.get(0).getLogin());
		koszykRepository.deleteAll(koszyk);
		URI location = ucBuilder.path("/api/komentarz/{id}").buildAndExpand(1).toUri();
		return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body(messageSource.getMessage("api.UdaneOproznienieKoszyka", null, new Locale(jezyk)));
	
	}

	 @PostMapping(value = "/api/dodajKomentarz/{jezyk}")
	 public ResponseEntity dodajKomentarz(@PathVariable("jezyk") String jezyk, @RequestBody Komentarz komentarz, UriComponentsBuilder ucBuilder, BindingResult result) 
	 {
		 /*System.out.println(komentarz.getKomentarz());
		 System.out.println(komentarz.getOcena());
		 System.out.println(komentarz.getIdUzytkownika());
		 System.out.println(komentarz.getIdPotrawy());*/
		 new KomentarzValidator().validate(komentarz, result);
		 if(result.hasErrors())
		 {
			 List<String> bledy = new ArrayList<>();
			 for(int i = 0; i < result.getAllErrors().size(); i++)
			 {
				 bledy.add((messageSource.getMessage(result.getAllErrors().get(i).getCode(), null, new Locale(jezyk))));
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
		     return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body(messageSource.getMessage("api.UdaneDodanieKomentarza", null, new Locale(jezyk)));
		 }
	     //dodaje pole MyResponseHeader z wartoscia MyValue do odpowiedzi, cialo odpowiedzi to Hello World
	 }
	 
	 @PostMapping(value = "/api/rejestracja/{jezyk}") //dokonczyc
	 public ResponseEntity zarejestruj(@PathVariable("jezyk") String jezyk, @RequestBody Uzytkownik uzytkownik, UriComponentsBuilder ucBuilder, BindingResult result) 
	 {
		 List<Uzytkownik> listaU = uzytkownikRepository.findByLoginIgnoreCase(uzytkownik.getLogin());
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
		 System.out.println(result.getAllErrors().size());
		 if(result.hasErrors())
		 {
			 List<String> bledy = new ArrayList<>();
			 for(int i = 0; i < result.getAllErrors().size(); i++)
			 {
				 bledy.add((messageSource.getMessage(result.getAllErrors().get(i).getCode(), null, new Locale(jezyk))));
			 }
			 return ResponseEntity.accepted().body(bledy);
		 }
		 else
		 {
			 URI location = ucBuilder.path("/api/komentarz/{id}").buildAndExpand(uzytkownik.getId()).toUri();
			 List<Rola> rola = rolaRepository.findByRola("ROLE_KLIENT");
			 uzytkownik.setRola(rola.get(0));
			 uzytkownikRepository.save(uzytkownik);
			 return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Udana rejestracja");
		 }
	 }
}