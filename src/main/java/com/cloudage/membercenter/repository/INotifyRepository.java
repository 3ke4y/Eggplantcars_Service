package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.News;
import com.cloudage.membercenter.entity.Notify;
import com.cloudage.membercenter.entity.User;

public interface INotifyRepository extends PagingAndSortingRepository<Notify, Integer> {

	//��NEWS����� �������� Ϊ User���û�
	@Query("from Notify notify where notify.author = ?1")
	List<Notify> findAllByAuthor(User user);
	
	//��NEWS����� ��������ID Ϊ userID���û�
	@Query("from Notify notify where notify.author.id = ?1")
	List<Notify> findAllByAuthorId(Integer userId);
}
