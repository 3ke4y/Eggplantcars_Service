package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.NewsComment;

public interface INewsCommentRepository extends PagingAndSortingRepository<NewsComment,Integer> {

	//ͨ��newsID Ѱ������  ���Ϊ     һ�����ŵ���������
	@Query("from NewsComment newsComment where newsComment.news.id = ?1")
	Page<NewsComment> findAllOfNewsId(int news_id, Pageable pageRequest);
	//ͨ��authorID Ѱ������  ���Ϊ ����ĳ�û���������������(���۵�����)
	@Query("from NewsComment newsComment where newsComment.author.id=?1")
	Page<NewsComment> findAllMyComment(int author_id, Pageable pageRequest);
	//ͨ����������ID Ѱ������  ���Ϊ ���������������ߵ�����
	@Query("from NewsComment newsComment where newsComment.news.author.id=?1")
	Page<NewsComment> findAllOfNewsAuthorId(int author_id, Pageable pageRequest);
	
	//ͳ����������
	@Query("select count(*) from NewsComment newsComment where newsComment.news.id=?1")
	int CommentCountsOfNews(int newsId);
	
}
