package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.Hotel;
import com.cloudage.membercenter.entity.User;

public interface IHotelRepository extends PagingAndSortingRepository<Hotel, Integer>{

	//��Hotel��������� Ϊ User���û�
	@Query("from Hotel hotel where hotel.author = ?1")
	List<Hotel> findAllByAuthor(User user);
	
	//��Hotel����� ����ID Ϊ userID���û�
	@Query("from Hotel hotel where hotel.author.id = ?1")
	List<Hotel> findAllByAuthorId(Integer userId);
}
