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

import com.cloudage.membercenter.entity.Notify;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.INotifyRepository;

@Component
@Service
@Transactional
public class DefaultNotifyService implements INotifyService {

	@Autowired
	INotifyRepository notifyRepo;
	@Override
	public List<Notify> findAllByAuthor(User user) {
		// TODO Auto-generated method stub
		return notifyRepo.findAllByAuthor(user);
	}

	@Override
	public List<Notify> findAllByAuthorId(Integer userId) {
		// TODO Auto-generated method stub
		return notifyRepo.findAllByAuthorId(userId);
	}

	@Override
	public Notify findOne(int notifyId) {
		// TODO Auto-generated method stub
		return notifyRepo.findOne(notifyId);
	}

	@Override
	public Notify save(Notify notify) {
		// TODO Auto-generated method stub
		return notifyRepo.save(notify);
	}

	@Override
	public Page<Notify> getNotify(int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 5, sort);
		return notifyRepo.findAll(pageRequest);
	}

}
