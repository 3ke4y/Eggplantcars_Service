package com.cloudage.membercenter.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudage.membercenter.entity.Notify;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.service.INotifyService;
import com.cloudage.membercenter.service.IUserService;

@RestController
@RequestMapping("/infoapi")
public class UserInfoChangeController {

	@Autowired
	IUserService userService;
	@Autowired
	INotifyService notifyService;
	
	@RequestMapping(value = "/hi", method=RequestMethod.GET)
	public @ResponseBody String hi(){	
		return "HELLO WORLD";
	}
	
	//��ʾ�û���Ϣ �ṩsession
	@RequestMapping(value="/me",method=RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request)
	{
		HttpSession session=request.getSession(true);
		Integer uid=(Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}
	
	// �޸��ǳ�
	@RequestMapping(value = "/userNameChange", method = RequestMethod.POST)
	public boolean userNameChange(@RequestParam String name, HttpServletRequest request) {
		User currentUser = getCurrentUser(request);
		if (currentUser == null) {
			return false;
		} else {
			currentUser.setName(name);
			userService.save(currentUser);
			return true;
		}
	}
	//�޸��Ա�
	@RequestMapping(value = "/userSexChange", method = RequestMethod.POST)
	public boolean userSexChange(@RequestParam String sex, HttpServletRequest request) {
		User currentUser = getCurrentUser(request);
		if (currentUser == null) {
			return false;
		} else {
			currentUser.setSex(sex);
			userService.save(currentUser);
			return true;
		}
	}
	
	//�޸��ֻ�
	@RequestMapping(value = "/userPhoneChange", method = RequestMethod.POST)
	public boolean userPhoneChange(@RequestParam String phone, HttpServletRequest request) {
		User currentUser = getCurrentUser(request);
		if (currentUser == null) {
			return false;
		} else {
			currentUser.setPhone(phone);
			userService.save(currentUser);
			return true;
		}
	}
	
	//�޸�����
	@RequestMapping(value = "/userEmailChange", method = RequestMethod.POST)
	public boolean userEmailChange(@RequestParam String email, HttpServletRequest request) {
		User currentUser = getCurrentUser(request);
		if (currentUser == null) {
			return false;
		} else {
			currentUser.setEmail(email);
			userService.save(currentUser);
			return true;
		}
	}
	
	//�޸ĵ�ַ
	@RequestMapping(value = "/userAddressChange", method = RequestMethod.POST)
	public boolean userAddressChange(@RequestParam String address, HttpServletRequest request) {
		User currentUser = getCurrentUser(request);
		if (currentUser == null) {
			return false;
		} else {
			currentUser.setAddress(address);
			userService.save(currentUser);
			return true;
		}
	}
	
	//�޸�ͷ��
	@RequestMapping(value = "/userAvatarChange", method = RequestMethod.POST)
	public boolean avatarchange(MultipartFile avatar, HttpServletRequest request) {
		User currentUser = getCurrentUser(request);
		if (currentUser == null) {
			return false;
		} else {
			try {
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
				FileUtils.copyInputStreamToFile(avatar.getInputStream(),
						new File(realPath, currentUser.getAccount() + ".png"));
				currentUser.setAvatar("upload/" + currentUser.getAccount() + ".png");
				userService.save(currentUser);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return true;
		}
	}
	
	//��ʾMessage��֪ͨ��
	@RequestMapping(value = "/shownotify",method = RequestMethod.GET)
	public Page<Notify> getNotify(){
		return notifyService.getNotify(0);
	}
	
}
