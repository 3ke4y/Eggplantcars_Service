package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.GroupItem;
import com.cloudage.membercenter.entity.User;

public interface IGroupService {

	List<GroupItem> findAllByUser(User user);
	List<GroupItem> findAllByUserId(Integer userId);
	GroupItem findOne(int groupId);
	GroupItem save(GroupItem group);
	Page<GroupItem> getGroup(int page);
}
