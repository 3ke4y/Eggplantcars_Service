package com.cloudage.membercenter.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudage.membercenter.entity.News;
import com.cloudage.membercenter.entity.NewsComment;
import com.cloudage.membercenter.entity.NewsLike;
import com.cloudage.membercenter.entity.Noshery;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.service.INewsCommentService;
import com.cloudage.membercenter.service.INewsLikeService;
import com.cloudage.membercenter.service.INewsService;
import com.cloudage.membercenter.service.INosheryService;
import com.cloudage.membercenter.service.IUserService;

@RestController
@RequestMapping("/api")
public class APIController {
	@Autowired
	IUserService userService;

	@Autowired
	INewsService newsService;

	@Autowired
	INewsCommentService newsCommentService;
	
	@Autowired
	INewsLikeService newsLikeService;
	
	@Autowired
	INosheryService nosheryService;

	
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody
	String hello() {
		return "HELLO WORLD";
	}

	// 注册
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User register(@RequestParam String account,
			@RequestParam String passwordHash,
			HttpServletRequest request) {

		User user = new User();
		user.setAccount(account);
		user.setPasswordHash(passwordHash);

		return userService.save(user);
	}

	// 登陆
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(@RequestParam String account,
			@RequestParam String passwordHash, HttpServletRequest request) {
		User user = userService.findByAccount(account);
		if (user != null && user.getPasswordHash().equals(passwordHash)) {
			HttpSession session = request.getSession(true);
			session.setAttribute("uid", user.getId());
			// request.getSession().setAttribute("user", user);
			return user;
		} else {
			return null;
		}
	}

	// 显示用户信息 提供session
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}

	//上传news
	@RequestMapping(value="/addnews",method=RequestMethod.POST)
	public News postNews(
			@RequestParam String title,
			@RequestParam String text,
			MultipartFile newsavatar1,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		News news = new News();
		news.setAuthor(me);
		news.setTitle(title);
		news.setText1(text);
		if (newsavatar1 != null) {
			try {
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/newsload");
				FileUtils.copyInputStreamToFile(newsavatar1.getInputStream(),
						new File(realPath, title +"1"+".png"));
				//news.setAvatar("upload/" + account + ".png");
				news.setAvatar1("newsload/"+title+"1"+".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newsService.save(news);
	}
	
	// 显示news
	@RequestMapping(value = "/shownews", method = RequestMethod.GET)
	public Page<News> getNews() {
		return newsService.getNews(0);
	}

	// 按页数显示news
	@RequestMapping(value = "/shownews/{page}", method = RequestMethod.GET)
	public Page<News> getNews(@PathVariable int page) {
		return newsService.getNews(page);
	}
	
	//在新闻详细页面显示评论
	@RequestMapping("/News/{news_id}/comments") // 显示新闻评论
	public Page<NewsComment> getNewsCommentsOfNews(@PathVariable int news_id) {
		return newsCommentService.findNewsCommentsOfNews(news_id, 0);
	}
	@RequestMapping("/News/{news_id}/comments/{page}") // 评论分页显示
	public Page<NewsComment> getNewsCommentsOfNews(@PathVariable int news_id, @PathVariable int page) {
		return newsCommentService.findNewsCommentsOfNews(news_id, page);
	}
	
	//评论上传
	@RequestMapping(value = "/News/{news_id}/comments", method = RequestMethod.POST)
	public NewsComment postNewsComments(
			@PathVariable int news_id,
			@RequestParam String commentText,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		News news = newsService.findOne(news_id);
		NewsComment newsComment = new NewsComment();
		newsComment.setAuthor(me);
		newsComment.setNews(news);
		newsComment.setCommentText(commentText);
		return newsCommentService.save(newsComment);
	}
	
	//统计评论条数
	@RequestMapping("/News/{news_id}/commentscount")
	public int commentCount(@PathVariable int news_id){
		return newsCommentService.countComments(news_id);
	}
	
	
	//评论我的
	@RequestMapping("/News/author_id/receivedcomment")
	public Page<NewsComment> getNewsCommentOfMe(HttpServletRequest request){
		User currentUser = getCurrentUser(request);
		int author_id = currentUser.getId();
		return newsCommentService.findNewsCommentsOfAuthor(author_id, 0);
	}

	//我的评论
	@RequestMapping("/News/author_id/mycomments") // 显示所有对某人的评论
	public Page<NewsComment> getNewsCommentsOfAuthor(HttpServletRequest request) {
		User currentUser = getCurrentUser(request);
		int author_id = currentUser.getId();
		return newsCommentService.findAllOfMyNewsComment(author_id, 0);
	}
	
	
	
	//新闻  点赞条数统计
	@RequestMapping("/News/{news_id}/likescount")
	public int likesCount(@PathVariable int news_id){
		return newsLikeService.countLikes(news_id);
	}
	//新闻  判断是否点赞
	@RequestMapping("/News/{news_id}/isliked")
	public boolean checkLiked(
			@PathVariable int news_id,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		return newsLikeService.checkLiked(me.getId(),news_id);
	}
	//新闻  点赞提交到服务器
	@RequestMapping(value="/News/{news_id}/likes",method = RequestMethod.POST)
	public int changeLikes(
			@PathVariable int news_id,
			@RequestParam boolean likes,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		News news = newsService.findOne(news_id);
		if (likes) {
			newsLikeService.addLike(me, news);
		} else {
			newsLikeService.removeLike(me, news);
		}
		return newsLikeService.countLikes(news_id);
	}

	
	//快餐店列表显示
	@RequestMapping(value="/shownoshery", method = RequestMethod.GET)
	public Page<Noshery> getNoshery(){
		return nosheryService.getNoshery(0);
	}
	//快餐店投稿上传
	@RequestMapping(value="/addnoshery",method=RequestMethod.POST)
	public Noshery postNoshery(
			@RequestParam String nosheryName,
			@RequestParam String nosheryPhone,
			@RequestParam String nosheryAddress,
			MultipartFile nosheryAvatar,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		Noshery noshery = new Noshery();
		noshery.setShopkeeper(me);
		noshery.setNosheryName(nosheryName);
		noshery.setNosheryPhome(nosheryPhone);
		noshery.setNosheryAddress(nosheryAddress);
		if (nosheryAvatar != null) {
			try {
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/nosheryload");
				FileUtils.copyInputStreamToFile(nosheryAvatar.getInputStream(), new File(realPath,nosheryName+".png"));
				noshery.setNosheryAvatar("nosheryload/"+nosheryName+".png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return nosheryService.save(noshery);
	}
}
