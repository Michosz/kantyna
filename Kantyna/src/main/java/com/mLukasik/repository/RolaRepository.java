package com.mLukasik.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Rola;

public interface RolaRepository extends CrudRepository<Rola, Integer>
{
	List<Rola> findByRola(String Rola);
}
