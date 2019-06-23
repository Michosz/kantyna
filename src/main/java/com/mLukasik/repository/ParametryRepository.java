package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Parametry;

public interface ParametryRepository extends CrudRepository<Parametry, Integer>
{
	List<Parametry> findByIdParametru(int id);
	List<Parametry> findAll();
}