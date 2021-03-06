package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.cloudage.membercenter.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DayFelling extends DateRecord {

	User author;
	String text;

	@ManyToOne(optional = false)
	@JsonIgnore
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Transient
	public Integer getAuthorId() {
		return author.getId();
	}

}
