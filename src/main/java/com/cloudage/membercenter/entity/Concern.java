package com.cloudage.membercenter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;



@Entity
public class Concern {

	@Embeddable
	public static class Key implements Serializable {
		User user;
		User group_author;
		@ManyToOne(optional= false)
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		@ManyToOne(optional=false)
		public User getGroup_author() {
			return group_author;
		}
		public void setGroup_author(User group_author) {
			this.group_author = group_author;
		}
		
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			if (obj instanceof Key) {
				Key other = (Key) obj;
				return group_author.getId() == other.group_author.getId() && user.getId() == other.user.getId();
			} else {
				return false;
			}
		}
		
	}
	
	Key idKey;
	Date createDate;
	
	@EmbeddedId
	public Key getIdKey() {
		return idKey;
	}

	public void setIdKey(Key idKey) {
		this.idKey = idKey;
	}

	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@PrePersist
	void onPrePersist() {
		createDate = new Date();
	}
	
}
