package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.Concern;

public interface IConcernRepository extends PagingAndSortingRepository<Concern, Concern.Key>{

	@Query("select count(*) from Concern concern where concern.id.user.id = ?1 and concern.id.group_author.id = ?2")
	int checkConcernExsists(int userId, int groupauthorId);
	
	@Query("from Concern concern where concern.id.user.id=?1")
	List<Concern> getConcernByUserId(int userId);
}
