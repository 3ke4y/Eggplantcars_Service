package com.cloudage.membercenter.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cloudage.membercenter.util.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Address extends BaseEntity {

	User user;
	Date createDate;
	String text;
	@ManyToOne(optional = false)
	@JsonIgnore
	public User getUser() {
		return user;
	}
	@Transient
	public String getAuthorName(){
		return user.name;
	}
	
	@Transient
	public String getAuthorAvatar(){
		return user.avatar;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@PrePersist
	void onPrePersist() {
		createDate = new Date();
	}
	
	
}
