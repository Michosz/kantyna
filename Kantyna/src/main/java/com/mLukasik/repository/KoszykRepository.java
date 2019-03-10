package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Koszyk;

public interface KoszykRepository extends CrudRepository<Koszyk, Integer>
{
	List<Koszyk> findById(int id);
	List<Koszyk> findByPotrawaNazwa(String nazwa);
	List<Koszyk> findByUzytkownikLogin(String login);
	List<Koszyk> findByPotrawaNazwaAndUzytkownikLogin(String nazwa, String login);
	List<Koszyk> findByUzytkownikLoginAndPotrawaId(String login, int id);
	List<Koszyk> findAll();
}