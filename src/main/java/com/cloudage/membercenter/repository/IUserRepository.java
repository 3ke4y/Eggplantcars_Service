package com.cloudage.membercenter.repository;

import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, Integer>{

	//从user表查找 用户为 account
	@Query("from User user where user.account = ?1")
	User findUserByAccount(String account);
	//从user表查找  用户ID 且 密码 
	@Query("from User user where user.id = ?1 and user.passwordHash = ?2")
	User findUserByPasswordHash(Integer userId,String passwordHash);
	//从user表查找 用户邮箱
	@Query("from User user where user.email = ?1")
	User findUserByEmail(String email);
	//通过手机查找
	@Query("from User user where user.phone = ?1")
	User findUserByPhone(String phone);
	////从user表查找 用户为account 重构
	@Query("from User user where user.account = ?1")
	User findUserByAccount(int account);
}
