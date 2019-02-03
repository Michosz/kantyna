package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Potrawa;

public interface PotrawaRepository extends CrudRepository<Potrawa, Integer>
{
	List<Potrawa> findById(int id);
	List<Potrawa> findByNazwa(String nazwa);
	List<Potrawa> findByCzyJestDostepna(boolean czyJestDostepna);
	List<Potrawa> findAll();
}