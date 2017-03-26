package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Notify;
import com.cloudage.membercenter.entity.User;

public interface INotifyService {

	List<Notify> findAllByAuthor(User user);
	List<Notify> findAllByAuthorId(Integer userId);
	Notify findOne(int newsId);
	Notify save(Notify news);
	Page<Notify> getNotify(int page);
}
