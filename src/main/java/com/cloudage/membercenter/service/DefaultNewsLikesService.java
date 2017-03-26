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

import com.cloudage.membercenter.entity.News;
import com.cloudage.membercenter.entity.NewsLike;
import com.cloudage.membercenter.entity.NewsLike.Key;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.INewsLikeRepository;

@Component
@Service
@Transactional
public class DefaultNewsLikesService implements INewsLikeService{

	@Autowired
	INewsLikeRepository newslikeRepo;
	
	@Override
	public void addLike(User user, News news) {
		NewsLike.Key key = new Key();
		key.setUser(user);
		key.setNews(news);
		NewsLike lk = new NewsLike();
		lk.setIdKey(key);
		newslikeRepo.save(lk);
	}

	@Override
	public void removeLike(User user, News news) {
		// TODO Auto-generated method stub
		NewsLike.Key key = new Key();
		key.setUser(user);
		key.setNews(news);
		newslikeRepo.delete(key);
	}

	@Override
	public int countLikes(int newsId) {
		// TODO Auto-generated method stub
		return newslikeRepo.likeCountsOfNews(newsId);
	}

	@Override
	public boolean checkLiked(int userId, int newsId) {
		// TODO Auto-generated method stub
		return newslikeRepo.checkLikesExsists(userId, newsId)>0;
	}



	
}
