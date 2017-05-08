package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.GroupItemComment;
import com.cloudage.membercenter.repository.IGroupItemCommentRepository;

@Component
@Service
@Transactional
public class DefaultGroupItemCommentService implements IGroupItemCommentService{

	@Autowired
	IGroupItemCommentRepository groupCommentRepo;
	
	@Override
	public GroupItemComment save(GroupItemComment groupComment) {
		// TODO Auto-generated method stub
		return groupCommentRepo.save(groupComment);
	}

	@Override
	public int countComments(int groupId) {
		// TODO Auto-generated method stub
		return groupCommentRepo.CommentCountsOfGroupItem(groupId);
	}

	@Override
	public GroupItemComment findOne(int commentId) {
		// TODO Auto-generated method stub
		return groupCommentRepo.findOne(commentId);
	}

	@Override
	public Page<GroupItemComment> findGroupItemCommentOfGroup(int group_id,
			int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7,sort);
		return groupCommentRepo.findAllOfGroupItemId(group_id, pageRequest);
	}

	
}
