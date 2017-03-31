package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.Noshery;
import com.cloudage.membercenter.entity.User;

public interface INosheryRepository extends PagingAndSortingRepository<Noshery, Integer>{

	@Query("from Noshery no where no.shopkeeper = ?1")
	List<Noshery> findAllByShopkeeper(User user);
	
	@Query("from Noshery no where no.shopkeeper.id = ?1")
	List<Noshery> findAllByShopkeeperId(Integer userId);
}
