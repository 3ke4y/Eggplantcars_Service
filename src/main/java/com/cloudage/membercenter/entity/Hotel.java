package com.cloudage.membercenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.cloudage.membercenter.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Hotel extends DateRecord {

	User author;
	String hotelName;  //酒店名
	String hotelAddress;  //地址
	String hotelPhone;  //电话 
	String hotelInternet;  //网络服务
	String hotelParking;   //停车
	String hotelFac;         //介绍
	String hotelOpenTime;  //开点时间
	String hotelAvatar;
	String hotelIntroduce; //酒店介绍
	
	@ManyToOne(optional=false)
	@JsonIgnore
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	@Transient
	public String getAuthorName(){
		return author.name;
	}
	@Transient
	public String getAuthorAvatar(){
		return author.avatar;
	}
	@Transient
	public Integer getAuthorId(){
		return author.getId();
	}
	
	
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	@Column(nullable=true)
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	@Column(nullable=true)
	public String getHotelPhone() {
		return hotelPhone;
	}
	public void setHotelPhone(String hotelPhone) {
		this.hotelPhone = hotelPhone;
	}
	@Column(nullable=true)
	public String getHotelInternet() {
		return hotelInternet;
	}
	public void setHotelInternet(String hotelInternet) {
		this.hotelInternet = hotelInternet;
	}
	@Column(nullable=true)
	public String getHotelParking() {
		return hotelParking;
	}
	public void setHotelParking(String hotelParking) {
		this.hotelParking = hotelParking;
	}
	@Column(nullable=true)
	public String getHotelFac() {
		return hotelFac;
	}
	public void setHotelFac(String hotelFac) {
		this.hotelFac = hotelFac;
	}
	@Column(nullable=true)
	public String getHotelOpenTime() {
		return hotelOpenTime;
	}
	public void setHotelOpenTime(String hotelOpenTime) {
		this.hotelOpenTime = hotelOpenTime;
	}
	@Column(nullable=true)
	public String getHotelAvatar() {
		return hotelAvatar;
	}
	public void setHotelAvatar(String hotelAvatar) {
		this.hotelAvatar = hotelAvatar;
	}
	@Column(nullable=true)
	public String getHotelIntroduce() {
		return hotelIntroduce;
	}
	public void setHotelIntroduce(String hotelIntroduce) {
		this.hotelIntroduce = hotelIntroduce;
	}
	
	
}
