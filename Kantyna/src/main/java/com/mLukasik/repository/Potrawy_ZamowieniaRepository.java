package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Potrawy_Zamowienia;

public interface Potrawy_ZamowieniaRepository extends CrudRepository<Potrawy_Zamowienia, Integer>
{
	List<Potrawy_Zamowienia> findByZamowienieId(int id);
	List<Potrawy_Zamowienia> findAll();
}