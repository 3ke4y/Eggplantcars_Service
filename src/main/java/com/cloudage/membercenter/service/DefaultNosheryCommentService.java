package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.NosheryComment;
import com.cloudage.membercenter.repository.INosheryCommentRepository;

@Component
@Service
@Transactional
public class DefaultNosheryCommentService implements INosheryCommentService {

	@Autowired
	INosheryCommentRepository nosheryCommentRepo;
	
	@Override
	public NosheryComment save(NosheryComment newsComment) {
		// TODO Auto-generated method stub
		return nosheryCommentRepo.save(newsComment);
	}

	@Override
	public Page<NosheryComment> findNosheryCommentsOfNoshery(int noshery_id,
			int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7,sort);
		return nosheryCommentRepo.findAllOfNosheryId(noshery_id, pageRequest);
	}

	@Override
	public Page<NosheryComment> findAllOfMyNosheryComment(int author_id,
			int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7,sort);
		return nosheryCommentRepo.findAllMyComment(author_id, pageRequest);
	}

	@Override
	public Page<NosheryComment> findNosheryCommentsOfAuthor(int author_id,
			int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7,sort);
		return nosheryCommentRepo.findAllOfNosheryAuthorId(author_id, pageRequest);
	}

	@Override
	public int countComments(int newsId) {
		// TODO Auto-generated method stub
		return nosheryCommentRepo.CommentCountsOfNoshery(newsId);
	}

	@Override
	public NosheryComment findOne(int commentId) {
		// TODO Auto-generated method stub
		return nosheryCommentRepo.findOne(commentId);
	}



}
