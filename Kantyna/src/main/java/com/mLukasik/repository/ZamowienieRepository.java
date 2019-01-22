package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Zamowienie;

public interface ZamowienieRepository extends CrudRepository<Zamowienie, Integer>
{
	List<Zamowienie> findById(int id);
	List<Zamowienie> findByUzytkownikLogin(String login); //nie mam pewnosci czy zadziala
	List<Zamowienie> findAll();
}