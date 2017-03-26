package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.cloudage.membercenter.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class NewsComment extends DateRecord {

	User author;
	News news;
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
	public News getNews() {
		return news;
	}
	
	public void setNews(News news) {
		this.news = news;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	
}
