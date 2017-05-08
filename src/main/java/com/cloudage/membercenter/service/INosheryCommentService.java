package com.cloudage.membercenter.service;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.NosheryComment;

public interface INosheryCommentService {

	NosheryComment save(NosheryComment newsComment);
	
	Page<NosheryComment> findNosheryCommentsOfNoshery(int noshery_id, int page);

	Page<NosheryComment> findAllOfMyNosheryComment(int author_id, int page);

	Page<NosheryComment> findNosheryCommentsOfAuthor(int author_id, int page);
	
	int countComments(int newsId);
	
	NosheryComment findOne(int commentId);
}
