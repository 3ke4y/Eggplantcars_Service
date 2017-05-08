package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.entity.Visit;

public interface IVisitService {

	List<Visit> findAllByAuthor(User user);
	List<Visit> findAllByAuthorId(Integer userId);
	Visit findOne(int visitId);
	Visit save(Visit visit);
	Page<Visit> getVisit(int page);
	
	Page<Visit> searchByKeyword(String keyword,int page);
}
