package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.RodzajPotrawy;

public interface RodzajPotrawyRepository extends CrudRepository<RodzajPotrawy, Integer>
{
	List<RodzajPotrawy> findAll();
	List<RodzajPotrawy> findByRodzaj(String rodzaj);
	List<RodzajPotrawy> findByRodzajIgnoreCase(String rodzaj);
}