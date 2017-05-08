package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.NosheryFood;
import com.cloudage.membercenter.repository.INosheryFoodRepository;

@Component
@Service
@Transactional
public class DefaultNosheryFoodService implements INosheryFoodService {

	@Autowired
	INosheryFoodRepository nosheryfoodRepo;
	
	@Override
	public NosheryFood save(NosheryFood nosheryFood) {
		// TODO Auto-generated method stub
		return nosheryfoodRepo.save(nosheryFood);
	}

	@Override
	public Page<NosheryFood> findNosheryFoodOfNoshery(int noshery_id, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7,sort);
		return nosheryfoodRepo.findAllofNosheryId(noshery_id, pageRequest);
	}

	@Override
	public NosheryFood findOne(int foodId) {
		// TODO Auto-generated method stub
		return nosheryfoodRepo.findOne(foodId);
	}

}
