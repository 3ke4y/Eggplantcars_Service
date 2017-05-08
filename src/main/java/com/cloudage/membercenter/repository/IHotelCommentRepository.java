package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.HotelComment;

public interface IHotelCommentRepository extends PagingAndSortingRepository<HotelComment, Integer>{

	@Query("from HotelComment hotelComment where hotelComment.hotel.id=?1")
	Page<HotelComment> findAllOfHotelId(int hotel_id,Pageable pageRequest);

	@Query("select count(*) from HotelComment hotelComment where hotelComment.hotel.id=?1")
	int CommentCountsOfHotel(int hotelId);
}
