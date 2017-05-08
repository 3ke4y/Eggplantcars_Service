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

import com.cloudage.membercenter.entity.DayFelling;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.IDayFellingRepository;

@Component
@Service
@Transactional
public class DefaultDayFellingService implements IDayFellingService{

	@Autowired
	IDayFellingRepository fellingRepo;
	


	@Override
	public DayFelling findOne(int fellingId) {
		// TODO Auto-generated method stub
		return fellingRepo.findOne(fellingId);
	}

	@Override
	public DayFelling save(DayFelling felling) {
		// TODO Auto-generated method stub
		return fellingRepo.save(felling);
	}

	@Override
	public Page<DayFelling> getFelling(int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7, sort);
		return fellingRepo.findAll(pageRequest);
	}

	@Override
	public Page<DayFelling> findAllByAuthor(User user, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7, sort);
		return fellingRepo.findAllByAuthor(user, pageRequest);
	}

	@Override
	public Page<DayFelling> findAllByAuthorId(Integer userId, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7, sort);
		return fellingRepo.findAllByAuthorId(userId, pageRequest);
	}

}
