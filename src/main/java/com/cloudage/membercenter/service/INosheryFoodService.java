package com.cloudage.membercenter.service;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.NosheryFood;

public interface INosheryFoodService {

	NosheryFood save(NosheryFood nosheryFood);
	
	Page<NosheryFood> findNosheryFoodOfNoshery(int noshery_id,int page);
	
	NosheryFood findOne(int foodId);
}
