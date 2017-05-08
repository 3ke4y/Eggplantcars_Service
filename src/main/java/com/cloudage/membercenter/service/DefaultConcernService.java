package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Concern;
import com.cloudage.membercenter.entity.Concern.Key;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.IConcernRepository;

@Component
@Service
@Transactional
public class DefaultConcernService implements IConcernService{

	@Autowired
	IConcernRepository concernRepo;
	
	@Override
	public void addConcern(User user, User group_author) {
		// TODO Auto-generated method stub
		Concern.Key key = new Key();
		key.setUser(user);
		key.setGroup_author(group_author);
		Concern cc = new Concern();
		cc.setIdKey(key);
		concernRepo.save(cc);
	}

	@Override
	public void removeConcern(User user, User group_author) {
		// TODO Auto-generated method stub
		Concern.Key key = new Key();
		key.setUser(user);
		key.setGroup_author(group_author);
		concernRepo.delete(key);
	}

	@Override
	public boolean checkConcerned(int userId, int groupauthorId) {
		// TODO Auto-generated method stub
		return concernRepo.checkConcernExsists(userId, groupauthorId)>0;
	}

	@Override
	public List<Concern> getConcernByUserId(int userId) {
		// TODO Auto-generated method stub
		return concernRepo.getConcernByUserId(userId);
	}

}
