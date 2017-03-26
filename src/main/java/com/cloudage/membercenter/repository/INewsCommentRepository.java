package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.NewsComment;

public interface INewsCommentRepository extends PagingAndSortingRepository<NewsComment,Integer> {

	//通过newsID 寻找评论  结果为     一个新闻的所有评论
	@Query("from NewsComment newsComment where newsComment.news.id = ?1")
	Page<NewsComment> findAllOfNewsId(int news_id, Pageable pageRequest);
	//通过authorID 寻找评论  结果为 所有某用户发出的所有评论(评论的作者)
	@Query("from NewsComment newsComment where newsComment.author.id=?1")
	Page<NewsComment> findAllMyComment(int author_id, Pageable pageRequest);
	//通过新闻作者ID 寻找评论  结果为 评论所属新闻作者的评论
	@Query("from NewsComment newsComment where newsComment.news.author.id=?1")
	Page<NewsComment> findAllOfNewsAuthorId(int author_id, Pageable pageRequest);
	
	//统计评论数量
	@Query("select count(*) from NewsComment newsComment where newsComment.news.id=?1")
	int CommentCountsOfNews(int newsId);
	
}
