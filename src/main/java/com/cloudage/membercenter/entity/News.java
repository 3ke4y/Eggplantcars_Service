package com.cloudage.membercenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.cloudage.membercenter.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class News extends DateRecord{

	User author;   //作者
	String title;  //新闻标题
	String text1,text2,text3;  //新闻内容
	String avatar1,avatar2,avatar3;//新闻图片
	
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
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(nullable=true)
	public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}
	@Column(nullable=true)
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	@Column(nullable=true)
	public String getText3() {
		return text3;
	}
	public void setText3(String text3) {
		this.text3 = text3;
	}
	
	@Column(nullable=true)
	public String getAvatar1() {
		return avatar1;
	}
	public void setAvatar1(String avatar1) {
		this.avatar1 = avatar1;
	}
	@Column(nullable=true)
	public String getAvatar2() {
		return avatar2;
	}
	public void setAvatar2(String avatar2) {
		this.avatar2 = avatar2;
	}
	@Column(nullable=true)
	public String getAvatar3() {
		return avatar3;
	}
	public void setAvatar3(String avatar3) {
		this.avatar3 = avatar3;
	}
	/*
	public void addAvatar(String add){
		this.avatar = this.avatar +add ;
	}*/
}
