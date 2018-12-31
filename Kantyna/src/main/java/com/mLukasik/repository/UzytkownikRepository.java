package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Uzytkownik;

public interface UzytkownikRepository extends CrudRepository<Uzytkownik, Integer>
{
	List<Uzytkownik> findByNazwisko(String Nazwisko);
	List<Uzytkownik> findByLogin(String Login);
	List<Uzytkownik> findByTelefon(String Telefon);
	List<Uzytkownik> findAll();
}
