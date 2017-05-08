package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.cloudage.membercenter.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class NosheryComment extends DateRecord {

	User author;
	Noshery noshery;
	String commentText;
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
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
}
