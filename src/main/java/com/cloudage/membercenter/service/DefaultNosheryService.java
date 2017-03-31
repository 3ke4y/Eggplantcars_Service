package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Noshery;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.INosheryRepository;

@Component
@Service
@Transactional
public class DefaultNosheryService implements INosheryService{

	@Autowired
	INosheryRepository nsRepo;
	
	@Override
	public List<Noshery> findAllByShopkeeper(User user) {
		// TODO Auto-generated method stub
		return nsRepo.findAllByShopkeeper(user);
	}

	@Override
	public List<Noshery> findAllByShopkeeperId(Integer userId) {
		// TODO Auto-generated method stub
		return nsRepo.findAllByShopkeeperId(userId);
	}

	@Override
	public Noshery findOne(int nosheryId) {
		// TODO Auto-generated method stub
		return nsRepo.findOne(nosheryId);
	}

	@Override
	public Noshery save(Noshery noshery) {
		// TODO Auto-generated method stub
		return nsRepo.save(noshery);
	}

	@Override
	public Page<Noshery> getNoshery(int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page,10,sort);
		return nsRepo.findAll(pageRequest);
	}

}
