package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.mLukasik.model.Uzytkownik;
import com.mLukasik.model.Zamowienie;

public interface ZamowienieRepository extends CrudRepository<Zamowienie, Integer>
{
	List<Zamowienie> findById(int id);
	List<Zamowienie> findByUzytkownikLogin(String login);
	List<Zamowienie> findByCzyManagerJeWidzialFalse();
	List<Zamowienie> findByCzyKlientWidzialZatwierdzoneZamowienieFalseAndCzyJestZatwierdzonePrzezManageraTrue();
	List<Zamowienie> findByUzytkownikLoginAndCzyJestZatwierdzonePrzezManageraTrue(String login);
	List<Zamowienie> findByCzyKlientWidzialZatwierdzoneZamowienieFalseAndCzyJestZatwierdzonePrzezManageraTrueAndUzytkownikLogin(String login);
	List<Zamowienie> findAll();
}