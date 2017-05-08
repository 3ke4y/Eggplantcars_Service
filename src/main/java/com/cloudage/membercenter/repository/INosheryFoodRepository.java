package com.cloudage.membercenter.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.NosheryFood;

public interface INosheryFoodRepository extends
		PagingAndSortingRepository<NosheryFood, Integer> {

	@Query("from NosheryFood nsfood where nsfood.noshery.id=?1")
	Page<NosheryFood> findAllofNosheryId(int noshery_id, Pageable pageRequest);
}
