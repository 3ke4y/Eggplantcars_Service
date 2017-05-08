package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.NosheryComment;

public interface INosheryCommentRepository extends PagingAndSortingRepository<NosheryComment,Integer> {

	//ͨ��newsID Ѱ������  ���Ϊ     һ�����ŵ���������
	@Query("from NosheryComment nosheryComment where nosheryComment.noshery.id = ?1")
	Page<NosheryComment> findAllOfNosheryId(int news_id, Pageable pageRequest);
	//ͨ��authorID Ѱ������  ���Ϊ ����ĳ�û���������������(���۵�����)
	@Query("from NosheryComment nosheryComment where nosheryComment.author.id=?1")
	Page<NosheryComment> findAllMyComment(int author_id, Pageable pageRequest);
	//ͨ�� ����ID Ѱ������  ���Ϊ ���������������ߵ�����
	@Query("from NosheryComment nosheryComment where nosheryComment.noshery.shopkeeper.id=?1")
	Page<NosheryComment> findAllOfNosheryAuthorId(int author_id, Pageable pageRequest);
	
	//ͳ����������
	@Query("select count(*) from NosheryComment nosheryComment where nosheryComment.noshery.id=?1")
	int CommentCountsOfNoshery(int newsId);
}
