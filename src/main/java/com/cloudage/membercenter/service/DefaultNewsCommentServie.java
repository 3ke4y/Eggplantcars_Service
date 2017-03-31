package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.NewsComment;
import com.cloudage.membercenter.repository.INewsCommentRepository;

@Component
@Service
@Transactional
public class DefaultNewsCommentServie implements INewsCommentService {

	@Autowired
	INewsCommentRepository newsCommentRepo;
	
	@Override
	public NewsComment save(NewsComment newsComment) {
		// TODO Auto-generated method stub
		return newsCommentRepo.save(newsComment);
	}

	@Override
	public Page<NewsComment> findNewsCommentsOfNews(int news_id, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7,sort);
		return newsCommentRepo.findAllOfNewsId(news_id, pageRequest);
	}

	//所有我的评论
	@Override
	public Page<NewsComment> findAllOfMyNewsComment(int author_id, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7,sort);
		return newsCommentRepo.findAllMyComment(author_id, pageRequest);
	}

	//所有评论我的
	@Override
	public Page<NewsComment> findNewsCommentsOfAuthor(int author_id, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7,sort);
		return newsCommentRepo.findAllOfNewsAuthorId(author_id, pageRequest);
	}

	@Override
	public int countComments(int newsId) {
		// TODO Auto-generated method stub
		return newsCommentRepo.CommentCountsOfNews(newsId);
	}

	@Override
	public NewsComment findOne(int commentId) {
		// TODO Auto-generated method stub
		return newsCommentRepo.findOne(commentId);
	}

}
