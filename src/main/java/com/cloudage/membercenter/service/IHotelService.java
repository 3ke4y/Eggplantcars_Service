package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Hotel;
import com.cloudage.membercenter.entity.User;

public interface IHotelService {

	List<Hotel> findAllByAuthor(User user);
	List<Hotel> findAllByAuthorId(Integer userId);
	Hotel findOne(int hotelId);
	Hotel save(Hotel hotel);
	Page<Hotel> getHotel(int page);
}
