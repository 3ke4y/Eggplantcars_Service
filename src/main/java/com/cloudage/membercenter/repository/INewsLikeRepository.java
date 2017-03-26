package com.cloudage.membercenter.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.NewsLike;

public interface INewsLikeRepository extends PagingAndSortingRepository<NewsLike,NewsLike.Key > {

	//统计Like的数量
	@Query("select count(*) from NewsLike likes where likes.id.news.id = ?1")
	int likeCountsOfNews(int newsId);
	
	
	//检查是否点赞
	@Query("select count(*) from NewsLike likes where likes.id.user.id = ?1 and likes.id.news.id = ?2")
	int checkLikesExsists(int authorId, int newsId);
	

}
