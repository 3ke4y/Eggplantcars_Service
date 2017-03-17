package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.News;
import com.cloudage.membercenter.entity.User;

public interface INewsRepository extends PagingAndSortingRepository<News, Integer>{

	//从NEWS表查找 新闻作者 为 User的用户
	@Query("from News news where news.author = ?1")
	List<News> findAllByAuthor(User user);
	
	//从NEWS表查找 新闻作者ID 为 userID的用户
	@Query("from News news where news.author.id = ?1")
	List<News> findAllByAuthorId(Integer userId);
}
