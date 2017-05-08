package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.cloudage.membercenter.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class NosheryFood extends DateRecord {

	User author;
	Noshery noshery;
	
	String foodname;//食物名字；
	String foodprice;//食物价格
	String foodavatar;//图片
	
	@ManyToOne(optional=false)
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	@ManyToOne(optional=false)
	@JsonIgnore
	public Noshery getNoshery() {
		return noshery;
	}
	public void setNoshery(Noshery noshery) {
		this.noshery = noshery;
	}
	public String getFoodname() {
		return foodname;
	}
	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}
	public String getFoodprice() {
		return foodprice;
	}
	public void setFoodprice(String foodprice) {
		this.foodprice = foodprice;
	}
	public String getFoodavatar() {
		return foodavatar;
	}
	public void setFoodavatar(String foodavatar) {
		this.foodavatar = foodavatar;
	}
	
	
}
