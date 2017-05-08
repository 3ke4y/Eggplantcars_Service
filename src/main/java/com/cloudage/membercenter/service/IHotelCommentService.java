package com.cloudage.membercenter.service;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.HotelComment;

public interface IHotelCommentService {

	HotelComment save(HotelComment hotelComment);
	
	Page<HotelComment> findHotelCommentOfHotel(int hotel_id,int page);
	
	int countComments(int hotelId);
	
	HotelComment findOne(int commentId);
}
