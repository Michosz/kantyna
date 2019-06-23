package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Stolik;

public interface StolikRepository extends CrudRepository<Stolik, Integer>
{
	//ponizsze szuka stolika odpowiednio patrzac na ilosc osob, ktore beda w zamowieniu i parametr systemowy
	List<Stolik> findByIloscMiejscBetweenAndCzyJestZajetyOrderByIloscMiejscAsc(int iloscMiejsc, int maxIlosc, boolean czyJestZajety);
	List<Stolik> findByIdStolika(int idStolika);
	List<Stolik> findByNazwa(String nazwa);
	List<Stolik> findByNazwaIgnoreCase(String nazwa);
	List<Stolik> findByCzyJestZajety(boolean zajety);
	List<Stolik> findAll();
}