package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.News;
import com.cloudage.membercenter.entity.NewsLike;
import com.cloudage.membercenter.entity.User;

public interface INewsLikeService {

	void addLike(User user, News news);

	void removeLike(User user, News news);

	int countLikes(int newsId);
	
	boolean checkLiked(int userId, int newsId);
	
}
