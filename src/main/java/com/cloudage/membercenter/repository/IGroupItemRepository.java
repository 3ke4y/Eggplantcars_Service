package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.GroupItem;
import com.cloudage.membercenter.entity.User;

public interface IGroupItemRepository extends PagingAndSortingRepository<GroupItem, Integer>{

	@Query("from GroupItem g where g.author = ?1")
	List<GroupItem> findAllByAuthor(User user);
	
	@Query("from GroupItem g where g.author.id = ?1")
	List<GroupItem> findAllByAuthorId(Integer userId);
}
