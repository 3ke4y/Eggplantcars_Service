package com.cloudage.membercenter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cloudage.membercenter.util.BaseEntity;

@Entity
public class PrivateLatter extends BaseEntity{

	User sender; //˽�ŷ�����
	User receiver; //������
	String latterText; //˽������
	Date createDate; //����ʱ��
	boolean unread; //�ж��Ƿ��Ѷ�  Ĭ��unread=true
	@ManyToOne(optional=false)
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	@ManyToOne(optional=false)
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	@Column(nullable = false)
	public String getLatterText() {
		return latterText;
	}
	public void setLatterText(String latterText) {
		this.latterText = latterText;
	}
	@Column(nullable = false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public boolean isUnread() {
		return unread;
	}
	public void setUnread(boolean unread) {
		this.unread = unread;
	}
	@PrePersist
	void onPreRersist() {
		//ÿ��������ʱ��
		createDate = new Date();
		unread = true;
	}
	
}
