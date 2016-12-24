package com.cloudage.membercenter.entity;


import java.util.Date;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;


import com.cloudage.membercenter.util.BaseEntity;

@Entity
public class PrivateLatter extends BaseEntity{

	User sender; // ˽�ŷ�����
	User receiver; // ˽�Ž�����
	String sendtype; //��Ϣ���� send ���� receive
	String latterText; // ˽������
	Date createDate; // ����ʱ��

	
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

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	@Column(nullable = false)
	public String getLatterText() {
		return latterText;
	}

	public void setLatterText(String latterText) {
		this.latterText = latterText;
	}

	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@PrePersist
	void onPreRersist() {
		createDate = new Date();
	}

}