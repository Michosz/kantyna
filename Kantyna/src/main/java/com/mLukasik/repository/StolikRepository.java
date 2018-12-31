package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Stolik;

public interface StolikRepository extends CrudRepository<Stolik, Integer>
{
	//jedna z 2 metod, sprawdzic ktora bedzie dzialala
	List<Stolik> findByIloscMiejscBetweenOrderByIloscMiejscAsc(int iloscMiejsc, int maxIlosc);
	List<Stolik> findByCzyJestZajety(boolean zajety);
}