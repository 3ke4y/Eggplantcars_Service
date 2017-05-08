package com.cloudage.membercenter.service;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.GroupItemComment;

public interface IGroupItemCommentService {

	GroupItemComment save(GroupItemComment groupComment);
	
	int countComments(int groupId);
	
	GroupItemComment findOne(int commentId);
	
	Page<GroupItemComment> findGroupItemCommentOfGroup(int group_id, int page);
}
