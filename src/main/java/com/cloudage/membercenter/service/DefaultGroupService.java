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

import com.cloudage.membercenter.entity.GroupItem;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.IGroupItemRepository;


@Component
@Service
@Transactional
public class DefaultGroupService implements IGroupService {

	@Autowired
	IGroupItemRepository gRepo;
	
	@Override
	public List<GroupItem> findAllByUser(User user) {
		// TODO Auto-generated method stub
		return gRepo.findAllByAuthor(user);
	}

	@Override
	public List<GroupItem> findAllByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return gRepo.findAllByAuthorId(userId);
	}

	@Override
	public GroupItem findOne(int groupId) {
		// TODO Auto-generated method stub
		return gRepo.findOne(groupId);
	}

	@Override
	public GroupItem save(GroupItem group) {
		// TODO Auto-generated method stub
		return gRepo.save(group);
	}

	@Override
	public Page<GroupItem> getGroup(int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 5, sort);
		return gRepo.findAll(pageRequest);
	}

	

}
