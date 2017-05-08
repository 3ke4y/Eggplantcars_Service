package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.PrivateLatter;
import com.cloudage.membercenter.repository.IPrivateLatterRepository;

@Component
@Service
@Transactional
public class DefaultPrivateLatterService implements IPrivateLatterService{

	@Autowired
	IPrivateLatterRepository latterRepo;
	
	@Override
	public PrivateLatter save(PrivateLatter latter) {
		// TODO Auto-generated method stub
		return latterRepo.save(latter);
	}

	@Override
	public Page<PrivateLatter> findPrivateLetterByReveiverId(int receiverId,
			int senderId, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.ASC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 40, sort);
		return latterRepo.findPrivateLatterByReceiverId(receiverId, senderId, pageRequest);
	}

	@Override
	public int countUnreadMessages(int receiverId, int senderId) {
		// TODO Auto-generated method stub
		return latterRepo.countUnreadMessages(receiverId, senderId);
	}

	@Override
	public void updateUnread(int receiverId, int senderId) {
		// TODO Auto-generated method stub
		latterRepo.updateUnread(receiverId, senderId);
	}

}
