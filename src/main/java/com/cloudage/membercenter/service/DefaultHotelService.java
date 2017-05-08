package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudage.membercenter.entity.Hotel;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.repository.IHotelRepository;

@Component
@Service
@Transactional
public class DefaultHotelService implements IHotelService {

	@Autowired
	IHotelRepository hotelRepo;
	
	@Override
	public List<Hotel> findAllByAuthor(User user) {
		// TODO Auto-generated method stub
		return hotelRepo.findAllByAuthor(user);
	}

	@Override
	public List<Hotel> findAllByAuthorId(Integer userId) {
		// TODO Auto-generated method stub
		return hotelRepo.findAllByAuthorId(userId);
	}

	@Override
	public Hotel findOne(int hotelId) {
		// TODO Auto-generated method stub
		return hotelRepo.findOne(hotelId);
	}

	@Override
	public Hotel save(Hotel hotel) {
		// TODO Auto-generated method stub
		return hotelRepo.save(hotel);
	}

	@Override
	public Page<Hotel> getHotel(int page) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(page,17, sort);
		return hotelRepo.findAll(pageRequest);
	}

}
