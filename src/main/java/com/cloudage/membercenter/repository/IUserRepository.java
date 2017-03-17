package com.cloudage.membercenter.repository;

import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, Integer>{

	//��user����� �û�Ϊ account
	@Query("from User user where user.account = ?1")
	User findUserByAccount(String account);
	//��user�����  �û�ID �� ���� 
	@Query("from User user where user.id = ?1 and user.passwordHash = ?2")
	User findUserByPasswordHash(Integer userId,String passwordHash);
	//��user����� �û�����
	@Query("from User user where user.email = ?1")
	User findUserByEmail(String email);
	//ͨ���ֻ�����
	@Query("from User user where user.phone = ?1")
	User findUserByPhone(String phone);
	////��user����� �û�Ϊaccount �ع�
	@Query("from User user where user.account = ?1")
	User findUserByAccount(int account);
}
