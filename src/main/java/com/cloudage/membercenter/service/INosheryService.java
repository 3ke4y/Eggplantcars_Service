package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Noshery;
import com.cloudage.membercenter.entity.User;

public interface INosheryService {

	List<Noshery> findAllByShopkeeper(User user);
	List<Noshery> findAllByShopkeeperId(Integer userId);
	Noshery findOne(int nosheryId);
	Noshery save(Noshery noshery);
	Page<Noshery> getNoshery(int page);
}
