package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.DayFelling;
import com.cloudage.membercenter.entity.User;

public interface IDayFellingRepository extends PagingAndSortingRepository<DayFelling, Integer>{

	@Query("from DayFelling felling where felling.author = ?1")
	Page<DayFelling> findAllByAuthor(User user,Pageable pageRequest);
	
	@Query("from DayFelling felling where felling.author.id = ?1")
	Page<DayFelling> findAllByAuthorId(Integer userId,Pageable pageRequest);
}
