package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.GroupItemComment;

public interface IGroupItemCommentRepository extends PagingAndSortingRepository<GroupItemComment,Integer>{

	//统计评论数量
	@Query("select count(*) from GroupItemComment groupComment where groupComment.group.id=?1")
	int CommentCountsOfGroupItem(int groupId);
	
	@Query("from GroupItemComment groupComment where groupComment.group.id = ?1")
	Page<GroupItemComment> findAllOfGroupItemId(int group_id,Pageable pageRequest);
}
