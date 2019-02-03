package com.mLukasik.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mLukasik.model.Komentarz;

public interface KomentarzRepository extends CrudRepository<Komentarz, Integer>
{
	List<Komentarz> findById(int id);
	List<Komentarz> findAll();
}