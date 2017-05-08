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

import com.cloudage.membercenter.entity.Concern;
import com.cloudage.membercenter.entity.GroupItem;
import com.cloudage.membercenter.entity.GroupItemComment;
import com.cloudage.membercenter.entity.Hotel;
import com.cloudage.membercenter.entity.HotelComment;
import com.cloudage.membercenter.entity.News;
import com.cloudage.membercenter.entity.NewsComment;
import com.cloudage.membercenter.entity.NewsLike;
import com.cloudage.membercenter.entity.Noshery;
import com.cloudage.membercenter.entity.NosheryComment;
import com.cloudage.membercenter.entity.NosheryFood;
import com.cloudage.membercenter.entity.PrivateLatter;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.entity.Visit;
import com.cloudage.membercenter.service.IConcernService;
import com.cloudage.membercenter.service.IGroupItemCommentService;
import com.cloudage.membercenter.service.IGroupService;
import com.cloudage.membercenter.service.IHotelCommentService;
import com.cloudage.membercenter.service.IHotelService;
import com.cloudage.membercenter.service.INewsCommentService;
import com.cloudage.membercenter.service.INewsLikeService;
import com.cloudage.membercenter.service.INewsService;
import com.cloudage.membercenter.service.INosheryCommentService;
import com.cloudage.membercenter.service.INosheryFoodService;
import com.cloudage.membercenter.service.INosheryService;
import com.cloudage.membercenter.service.IPrivateLatterService;
import com.cloudage.membercenter.service.IUserService;
import com.cloudage.membercenter.service.IVisitService;

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
	@Autowired
	INosheryCommentService nosheryCommentService;
	@Autowired
	INosheryFoodService nosheryFoodService;
	
	@Autowired
	IHotelService hotelService;
	@Autowired
	IHotelCommentService hotelCommentService;
	
	@Autowired
	IGroupService groupService;
	@Autowired
	IGroupItemCommentService groupCommentService;
	
	@Autowired
	IConcernService concernService;
	
	@Autowired
	IVisitService visitService;
	
	@Autowired
	IPrivateLatterService latterService;
	
	
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
	//显示美食店评论
	@RequestMapping("/Noshery/{noshery_id}/comments")
	public Page<NosheryComment> getNosheryCommentsOfNoshery(@PathVariable int noshery_id){
		return nosheryCommentService.findNosheryCommentsOfNoshery(noshery_id, 0);
	}
	@RequestMapping("/Noshery/{noshery_id}/comments/{page}")
	public Page<NosheryComment> getNosheryCommentsOfNoshery(@PathVariable int noshery_id,@PathVariable int page){
		return nosheryCommentService.findNosheryCommentsOfNoshery(noshery_id, page);
	}
	//发表美食店评论
	@RequestMapping(value = "/Noshery/{noshery_id}/comments",method = RequestMethod.POST)
	public NosheryComment postNosheryComments(
			@PathVariable int noshery_id,
			@RequestParam String commentText,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		Noshery noshery = nosheryService.findOne(noshery_id);
		NosheryComment nosheryComment = new NosheryComment();
		nosheryComment.setAuthor(me);
		nosheryComment.setNoshery(noshery);
		nosheryComment.setCommentText(commentText);
		return nosheryCommentService.save(nosheryComment);
	}
	//统计美食店评论条数
	@RequestMapping("/Noshery/{noshery_id}/commentscount")
	public int nosherycommentCount(@PathVariable int noshery_id){
		return nosheryCommentService.countComments(noshery_id);
	}
	
	//美食店商品上传
	@RequestMapping(value = "/Noshery/{noshery_id}/food",method = RequestMethod.POST)
	public NosheryFood postNosheryFood(
			@PathVariable int noshery_id,
			@RequestParam String foodName,
			@RequestParam String foodPrice,
			MultipartFile foodAvatar,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		Noshery noshery = nosheryService.findOne(noshery_id);
		NosheryFood nosheryFood = new NosheryFood();
		nosheryFood.setAuthor(me);
		nosheryFood.setNoshery(noshery);
		nosheryFood.setFoodname(foodName);
		nosheryFood.setFoodprice(foodPrice);
		if (foodAvatar != null) {
			try {
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/nosheryfoodload");
				FileUtils.copyInputStreamToFile(foodAvatar.getInputStream(), new File(realPath,foodName+".png"));
				noshery.setNosheryAvatar("nosheryfoodload/"+foodName+".png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return nosheryFoodService.save(nosheryFood);
	}
	//显示美食店商品
	@RequestMapping("/Noshery/{noshery_id}/food")
	public Page<NosheryFood> getNosheryFoodOfNoshery(@PathVariable int noshery_id){
		return nosheryFoodService.findNosheryFoodOfNoshery(noshery_id, 0);
	}
	
	
	
	
	// 显示hotel
	@RequestMapping(value = "/showhotel", method = RequestMethod.GET)
	public Page<Hotel> getHotel() {
		return hotelService.getHotel(0);
	}
	
	//上传Hotel
	@RequestMapping(value="/addhotel",method=RequestMethod.POST)
	public Hotel postHotel(
			@RequestParam String hotelName,
			@RequestParam String hotelAddress,
			@RequestParam String hotelPhone,
			@RequestParam String hotelInternet,
			@RequestParam String hotelParking,
			@RequestParam String hotelFac,     //设施
			@RequestParam String hotelOpenTime,
			@RequestParam String hotelIntroduce,//酒店简介
			MultipartFile hotelAvatar,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		Hotel hotel = new Hotel();
		hotel.setAuthor(me);
		hotel.setHotelName(hotelName);
		hotel.setHotelAddress(hotelAddress);
		hotel.setHotelPhone(hotelPhone);
		hotel.setHotelInternet(hotelInternet);
		hotel.setHotelParking(hotelParking);
		hotel.setHotelFac(hotelFac);
		hotel.setHotelOpenTime(hotelOpenTime);
		hotel.setHotelIntroduce(hotelIntroduce);
		if (hotelAvatar != null) {
			try {
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/hotelload");
				FileUtils.copyInputStreamToFile(hotelAvatar.getInputStream(),
						new File(realPath, hotelName +".png"));
				//news.setAvatar("upload/" + account + ".png");
				hotel.setHotelAvatar("hotelload/"+hotelName+".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return hotelService.save(hotel);
	}
	
	//hotel评论
	@RequestMapping("/Hotel/{hotel_id}/comments")
	public Page<HotelComment> getHotelCommentsOfHotel(@PathVariable int hotel_id){
		return hotelCommentService.findHotelCommentOfHotel(hotel_id, 0);
	}
	//hotel评论上传
	@RequestMapping(value = "/Hotel/{hotel_id}/comments", method = RequestMethod.POST)
	public HotelComment postHotelComments(
			@PathVariable int hotel_id,
			@RequestParam String commentText,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		Hotel hotel = hotelService.findOne(hotel_id);
		HotelComment hotelComment = new HotelComment();
		hotelComment.setAuthor(me);
		hotelComment.setHotel(hotel);
		hotelComment.setCommentText(commentText);
		return hotelCommentService.save(hotelComment);
	}
	//hotel评论条数统计
	@RequestMapping("/Hotel/{hotel_id}/commentscount")
	public int hotelCommentCount(@PathVariable int hotel_id){
		return hotelCommentService.countComments(hotel_id);
	}
	
	//group(类朋友圈）
	//group上传
	@RequestMapping(value = "/group", method = RequestMethod.POST)
	public GroupItem postGroup(
			@RequestParam String text,
			@RequestParam String avatarName,
			MultipartFile[] avatar,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		GroupItem group = new GroupItem();
		group.setAuthor(me);
		group.setText(text);
		if (avatar != null) {
			for (int i = 0; i < avatar.length; i++) {
				try {
					String realPath=request.getSession().getServletContext().getRealPath("/WEB-INF/groupload");
					FileUtils.copyInputStreamToFile(avatar[i].getInputStream(),
							new File(realPath, avatarName + "_" + i + ".png"));
					if (i==0) {
						group.setAvatar("groupload/" + avatarName + "_" + i + ".png|");
					}else {
						group.addAvatar("groupload/" + avatarName + "_" + i + ".png|");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return groupService.save(group);
	}
	//group 显示
	@RequestMapping(value = "/showgroup", method = RequestMethod.GET)
	public Page<GroupItem> getGroup(){
		return groupService.getGroup(0);
	}
	//group分页显示
	@RequestMapping(value = "/showgroup/{page}", method = RequestMethod.GET)
	public Page<GroupItem> getGroup(@PathVariable int page){
		return groupService.getGroup(page);
	}
	
	//显示group评论
	@RequestMapping("/Group/{group_id}/comments") // 显示新闻评论
	public Page<GroupItemComment> getGroupCommentsOfGroup(@PathVariable int group_id) {
		return groupCommentService.findGroupItemCommentOfGroup(group_id, 0);
	}
	@RequestMapping("/Group/{group_id}/comments/{page}") // 评论分页显示
	public Page<GroupItemComment> getGroupCommentsOfGroup(@PathVariable int group_id, @PathVariable int page) {
		return groupCommentService.findGroupItemCommentOfGroup(group_id, page);
	}
	
	//group评论上传
	@RequestMapping(value = "/Group/{group_id}/comments", method = RequestMethod.POST)
	public GroupItemComment postGroupComments(
			@PathVariable int group_id,
			@RequestParam String commentText,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		GroupItem group = groupService.findOne(group_id);
		GroupItemComment groupComment = new GroupItemComment();
		groupComment.setAuthor(me);
		groupComment.setGroup(group);
		groupComment.setCommentText(commentText);
		return groupCommentService.save(groupComment);
	}
	
	//统计评论条数
	@RequestMapping("/Group/{group_id}/commentscount")
	public int groupCommentCount(@PathVariable int group_id){
		return groupCommentService.countComments(group_id);
	}
	
	
	//判断是否关注
	@RequestMapping("/{group_author_id}/isConcerned")
	public boolean checkConcern(
			@PathVariable int group_author_id,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		return concernService.checkConcerned(me.getId(), group_author_id);
	}
	//提交关注请求(修改，以关注时取消关注)
	@RequestMapping(value="/{group_author_id}/Concerns",method = RequestMethod.POST)
	public boolean changeConcern(
			@PathVariable int group_author_id,
			@RequestParam(name="Concern") boolean Concern,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		User group_author = userService.findById(group_author_id);
		if (Concern) {
			concernService.addConcern(me, group_author);
		} else {
			concernService.removeConcern(me, group_author);
		}
		return Concern;
	}
	//显示当前用户关注的人
	@RequestMapping("/Concerns/getMyConcerns")
	public List<Concern> getConcernByUserId(
			HttpServletRequest request){
		User me = getCurrentUser(request);
		return concernService.getConcernByUserId(me.getId());
	}
	//从关注列表中显示某个人发过的group
	@RequestMapping("/{group_author_id}/Group")
	public List<GroupItem> getGroupByConcern(
			@PathVariable int group_author_id){
		return groupService.findAllByUserId(group_author_id);
	}
	
	//visit post 
	@RequestMapping(value = "/visit", method = RequestMethod.POST)
	public Visit postVisit(
			@RequestParam String title,
			@RequestParam String text1,
			@RequestParam String text2,
			@RequestParam String text3,
			@RequestParam String address,
			@RequestParam String avatarName,
			MultipartFile[] avatar,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		Visit visit = new Visit();
		visit.setAuthor(me);
		visit.setText1(text1);
		visit.setText2(text2);
		visit.setText3(text3);
		visit.setTitle(title);
		visit.setAddress(address);
		if (avatar != null) {
			for (int i = 0; i < avatar.length; i++) {
				try {
					String realPath=request.getSession().getServletContext().getRealPath("/WEB-INF/visitload");
					FileUtils.copyInputStreamToFile(avatar[i].getInputStream(),
							new File(realPath, avatarName + "_" + i + ".png"));
					if (i == 0) {
						visit.setAvatar("visitload/" + avatarName + "_" + i + ".png|");
					}else {
						visit.addAvatar("visitload/" + avatarName + "_" + i + ".png|");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			
			}
		}
		return visitService.save(visit);
	}
	//visit 列表显示
	@RequestMapping(value = "/showvisit", method = RequestMethod.GET)
	public Page<Visit> getVisit() {
		return visitService.getVisit(0);
	}
	//通过关键字keyword搜索visit
	@RequestMapping(value = "/visit/search/{keyword}", method = RequestMethod.GET)
	public Page<Visit> searchByKeyword(
			@PathVariable String keyword){
		System.out.print(keyword);
		return visitService.searchByKeyword(keyword,0);
	}
	//search修正尝试
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody Page<Visit> search(
			@RequestParam(name = "keyword") String keyword){
		return visitService.searchByKeyword(keyword,0);
	}
	//text Search
	@RequestMapping(value = "/text/{str}", method = RequestMethod.GET)
	public @ResponseBody String getStr (
			@PathVariable String str){
		return str;
	}
	
	//私信，发送私信
	@RequestMapping(value = "/privateLatter", method = RequestMethod.POST)
	public PrivateLatter savePrivateLatter(@RequestParam String text,
			@RequestParam String receiverAccount, HttpServletRequest request) {

		// User currentUser=getCurrentUser(request);
		// 通过 别人的Account(账号)给他发送私信
		User me = getCurrentUser(request);
		User receiver = userService.findByAccount(receiverAccount);
		PrivateLatter latter = new PrivateLatter();
		latter.setSender(me);
		latter.setReceiver(receiver);
		latter.setLatterText(text);
		return latterService.save(latter);
	}
	// 接受私信(当前登录用户为接收者)
	@RequestMapping(value = "/getprivateLatter/{receiverId}")
	public Page<PrivateLatter> getLatter(@PathVariable int receiverId, @RequestParam(defaultValue = "0") int page,
			HttpServletRequest request) {
		User me = getCurrentUser(request);
		return latterService.findPrivateLetterByReveiverId(receiverId, me.getId(), page);
	}
	//获取未读私信
	// 或许未读消息条数 get
	@RequestMapping("/unread/{senderId}")
	public int countUnreadMessage(@PathVariable int senderId, HttpServletRequest request) {
		User me = getCurrentUser(request);
		int meId = me.getId();
		return latterService.countUnreadMessages(meId, senderId);
	}
	// 修改未读消息为已读 post
	@RequestMapping(value = "/unread/update", method = RequestMethod.POST)
	public void updateUnread(@RequestParam String senderIdString, HttpServletRequest request) {
		User me = getCurrentUser(request);
		int meId = me.getId();
		int senderId = Integer.parseInt(senderIdString);
		latterService.updateUnread(meId, senderId);
	}
	
}
