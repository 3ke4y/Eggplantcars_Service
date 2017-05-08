package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.NosheryComment;

public interface INosheryCommentRepository extends PagingAndSortingRepository<NosheryComment,Integer> {

	//通过newsID 寻找评论  结果为     一个新闻的所有评论
	@Query("from NosheryComment nosheryComment where nosheryComment.noshery.id = ?1")
	Page<NosheryComment> findAllOfNosheryId(int news_id, Pageable pageRequest);
	//通过authorID 寻找评论  结果为 所有某用户发出的所有评论(评论的作者)
	@Query("from NosheryComment nosheryComment where nosheryComment.author.id=?1")
	Page<NosheryComment> findAllMyComment(int author_id, Pageable pageRequest);
	//通过 作者ID 寻找评论  结果为 评论所属新闻作者的评论
	@Query("from NosheryComment nosheryComment where nosheryComment.noshery.shopkeeper.id=?1")
	Page<NosheryComment> findAllOfNosheryAuthorId(int author_id, Pageable pageRequest);
	
	//统计评论数量
	@Query("select count(*) from NosheryComment nosheryComment where nosheryComment.noshery.id=?1")
	int CommentCountsOfNoshery(int newsId);
}
