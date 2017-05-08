package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.DayFelling;
import com.cloudage.membercenter.entity.User;

public interface IDayFellingService {

	Page<DayFelling> findAllByAuthor(User user,int page);
	Page<DayFelling> findAllByAuthorId(Integer userId,int page);
	DayFelling findOne(int fellingId);
	DayFelling save(DayFelling felling);
	Page<DayFelling> getFelling(int page);
}
