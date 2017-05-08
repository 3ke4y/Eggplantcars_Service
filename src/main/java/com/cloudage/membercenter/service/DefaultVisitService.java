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

import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.entity.Visit;
import com.cloudage.membercenter.repository.IVisitRepository;

@Component
@Service
@Transactional
public class DefaultVisitService implements IVisitService{

	@Autowired
	IVisitRepository visitRepo;
	
	@Override
	public List<Visit> findAllByAuthor(User user) {
		// TODO Auto-generated method stub
		return visitRepo.findAllByAuthor(user);
	}

	@Override
	public List<Visit> findAllByAuthorId(Integer userId) {
		// TODO Auto-generated method stub
		return visitRepo.findAllByAuthorId(userId);
	}

	@Override
	public Visit findOne(int visitId) {
		// TODO Auto-generated method stub
		return visitRepo.findOne(visitId);
	}

	@Override
	public Visit save(Visit visit) {
		// TODO Auto-generated method stub
		return visitRepo.save(visit);
	}

	@Override
	public Page<Visit> getVisit(int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page,7, sort);
		return visitRepo.findAll(pageRequest);
	}

	@Override
	public Page<Visit> searchByKeyword(String keyword, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page,7, sort);
		return visitRepo.searchWithKeyword(keyword, pageRequest);
	}

	
}
