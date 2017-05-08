package com.cloudage.membercenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.cloudage.membercenter.util.BaseEntity;

@Entity
public class User extends BaseEntity{
	String account;  //�˺�ID
	String passwordHash; //����
	String name; //�˻�����
	String avatar;  //�˻�ͷ��
	String sex ;//�Ա�
	String sign;//�ֻ�
	String address; //��ַ(eg.��ݸ��
	String email; //email��ַ

	@Column(unique=true)
	public String getAccount() {
		return account;
	}
	@Column(unique=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	@Column(nullable=true)
	public String getName() {
		return name;
	}
	@Column(nullable=true)
	public String getAvatar() {
		return avatar;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	@Column(nullable=true)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(nullable=true)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(nullable=true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(nullable=true)
	public String getSign() {
		return sign;
	}
	public void setSign(String phone) {
		this.sign = phone;
	}
	
}
