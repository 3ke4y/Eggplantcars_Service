package com.cloudage.membercenter.service;

import java.util.List;

import com.cloudage.membercenter.entity.Concern;
import com.cloudage.membercenter.entity.User;

public interface IConcernService {

	void addConcern(User user,User group_author);
	
	void removeConcern(User user,User group_author);
	
	boolean checkConcerned(int userId,int groupauthorId);
	
	List<Concern> getConcernByUserId(int userId);
	
}
