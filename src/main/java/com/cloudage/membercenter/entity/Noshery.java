package com.cloudage.membercenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.cloudage.membercenter.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

/*快餐店属性*/
@Entity
public class Noshery extends DateRecord {

	User shopkeeper; //店长
	String nosheryName; //快餐店名称
	String nosheryAvatar; //快餐店图片头像
	String nosheryPhome; //快餐店电话
	String nosheryAddress;//快餐店地址
	@ManyToOne(optional=false)
	@JsonIgnore
	public User getShopkeeper() {
		return shopkeeper;
	}
	public void setShopkeeper(User shopkeeper) {
		this.shopkeeper = shopkeeper;
	}
	
	
	@Transient
	public String getShopkeeperName(){
		return shopkeeper.name;
	}
	@Transient
	public String getShopkeeperAvatar(){
		return shopkeeper.avatar;
	}
	@Transient
	public Integer getShopkeeperId(){
		return shopkeeper.getId();
	}
	
	
	
	public String getNosheryName() {
		return nosheryName;
	}
	public void setNosheryName(String nosheryName) {
		this.nosheryName = nosheryName;
	}
	@Column(nullable=true)
	public String getNosheryAvatar() {
		return nosheryAvatar;
	}
	public void setNosheryAvatar(String nosheryAvatar) {
		this.nosheryAvatar = nosheryAvatar;
	}
	@Column(nullable=true)
	public String getNosheryPhome() {
		return nosheryPhome;
	}
	public void setNosheryPhome(String nosheryPhome) {
		this.nosheryPhome = nosheryPhome;
	}
	@Column(nullable=true)
	public String getNosheryAddress() {
		return nosheryAddress;
	}
	public void setNosheryAddress(String nosheryAddress) {
		this.nosheryAddress = nosheryAddress;
	}
	
	
}
