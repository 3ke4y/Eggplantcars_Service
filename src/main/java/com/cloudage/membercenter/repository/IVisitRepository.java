package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.entity.Visit;

public interface IVisitRepository extends PagingAndSortingRepository<Visit, Integer>{

	@Query("from Visit visit where visit.author = ?1")
	List<Visit> findAllByAuthor(User user);
	
	@Query("from Visit visit where visit.author.id = ?1")
	List<Visit> findAllByAuthorId(Integer userId);
	
	@Query("from Visit visit where visit.title like %?1% or visit.text1 like %?1%")
	Page<Visit> searchWithKeyword(String keyword,Pageable pageRequest);
}
