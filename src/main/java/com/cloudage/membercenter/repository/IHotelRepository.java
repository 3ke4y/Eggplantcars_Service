package com.cloudage.membercenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.Hotel;
import com.cloudage.membercenter.entity.User;

public interface IHotelRepository extends PagingAndSortingRepository<Hotel, Integer>{

	//从Hotel表查找作者 为 User的用户
	@Query("from Hotel hotel where hotel.author = ?1")
	List<Hotel> findAllByAuthor(User user);
	
	//从Hotel表查找 作者ID 为 userID的用户
	@Query("from Hotel hotel where hotel.author.id = ?1")
	List<Hotel> findAllByAuthorId(Integer userId);
}
