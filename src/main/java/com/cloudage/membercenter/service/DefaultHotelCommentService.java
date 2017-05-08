package com.cloudage.membercenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.HotelComment;
import com.cloudage.membercenter.repository.IHotelCommentRepository;

@Component
@Service
@Transactional
public class DefaultHotelCommentService implements IHotelCommentService {

	@Autowired
	IHotelCommentRepository hotelCommentRepo;
	
	@Override
	public HotelComment save(HotelComment hotelComment) {
		// TODO Auto-generated method stub
		return hotelCommentRepo.save(hotelComment);
	}

	@Override
	public Page<HotelComment> findHotelCommentOfHotel(int hotel_id, int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page, 7,sort);
		return hotelCommentRepo.findAllOfHotelId(hotel_id, pageRequest);
	}

	@Override
	public int countComments(int hotelId) {
		// TODO Auto-generated method stub
		return hotelCommentRepo.CommentCountsOfHotel(hotelId);
	}

	@Override
	public HotelComment findOne(int commentId) {
		// TODO Auto-generated method stub
		return hotelCommentRepo.findOne(commentId);
	}

}
