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

	// ע��
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User register(@RequestParam String account,
			@RequestParam String passwordHash,
			HttpServletRequest request) {

		User user = new User();
		user.setAccount(account);
		user.setPasswordHash(passwordHash);

		return userService.save(user);
	}

	// ��½
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

	// ��ʾ�û���Ϣ �ṩsession
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer uid = (Integer) session.getAttribute("uid");
		return userService.findById(uid);
	}

	//�ϴ�news
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
	
	// ��ʾnews
	@RequestMapping(value = "/shownews", method = RequestMethod.GET)
	public Page<News> getNews() {
		return newsService.getNews(0);
	}

	// ��ҳ����ʾnews
	@RequestMapping(value = "/shownews/{page}", method = RequestMethod.GET)
	public Page<News> getNews(@PathVariable int page) {
		return newsService.getNews(page);
	}
	
	//��������ϸҳ����ʾ����
	@RequestMapping("/News/{news_id}/comments") // ��ʾ��������
	public Page<NewsComment> getNewsCommentsOfNews(@PathVariable int news_id) {
		return newsCommentService.findNewsCommentsOfNews(news_id, 0);
	}
	@RequestMapping("/News/{news_id}/comments/{page}") // ���۷�ҳ��ʾ
	public Page<NewsComment> getNewsCommentsOfNews(@PathVariable int news_id, @PathVariable int page) {
		return newsCommentService.findNewsCommentsOfNews(news_id, page);
	}
	
	//�����ϴ�
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
	
	//ͳ����������
	@RequestMapping("/News/{news_id}/commentscount")
	public int commentCount(@PathVariable int news_id){
		return newsCommentService.countComments(news_id);
	}
	
	
	//�����ҵ�
	@RequestMapping("/News/author_id/receivedcomment")
	public Page<NewsComment> getNewsCommentOfMe(HttpServletRequest request){
		User currentUser = getCurrentUser(request);
		int author_id = currentUser.getId();
		return newsCommentService.findNewsCommentsOfAuthor(author_id, 0);
	}

	//�ҵ�����
	@RequestMapping("/News/author_id/mycomments") // ��ʾ���ж�ĳ�˵�����
	public Page<NewsComment> getNewsCommentsOfAuthor(HttpServletRequest request) {
		User currentUser = getCurrentUser(request);
		int author_id = currentUser.getId();
		return newsCommentService.findAllOfMyNewsComment(author_id, 0);
	}
	
	
	
	//����  ��������ͳ��
	@RequestMapping("/News/{news_id}/likescount")
	public int likesCount(@PathVariable int news_id){
		return newsLikeService.countLikes(news_id);
	}
	//����  �ж��Ƿ����
	@RequestMapping("/News/{news_id}/isliked")
	public boolean checkLiked(
			@PathVariable int news_id,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		return newsLikeService.checkLiked(me.getId(),news_id);
	}
	//����  �����ύ��������
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

	
	//��͵��б���ʾ
	@RequestMapping(value="/shownoshery", method = RequestMethod.GET)
	public Page<Noshery> getNoshery(){
		return nosheryService.getNoshery(0);
	}
	//��͵�Ͷ���ϴ�
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
	//��ʾ��ʳ������
	@RequestMapping("/Noshery/{noshery_id}/comments")
	public Page<NosheryComment> getNosheryCommentsOfNoshery(@PathVariable int noshery_id){
		return nosheryCommentService.findNosheryCommentsOfNoshery(noshery_id, 0);
	}
	@RequestMapping("/Noshery/{noshery_id}/comments/{page}")
	public Page<NosheryComment> getNosheryCommentsOfNoshery(@PathVariable int noshery_id,@PathVariable int page){
		return nosheryCommentService.findNosheryCommentsOfNoshery(noshery_id, page);
	}
	//������ʳ������
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
	//ͳ����ʳ����������
	@RequestMapping("/Noshery/{noshery_id}/commentscount")
	public int nosherycommentCount(@PathVariable int noshery_id){
		return nosheryCommentService.countComments(noshery_id);
	}
	
	//��ʳ����Ʒ�ϴ�
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
	//��ʾ��ʳ����Ʒ
	@RequestMapping("/Noshery/{noshery_id}/food")
	public Page<NosheryFood> getNosheryFoodOfNoshery(@PathVariable int noshery_id){
		return nosheryFoodService.findNosheryFoodOfNoshery(noshery_id, 0);
	}
	
	
	
	
	// ��ʾhotel
	@RequestMapping(value = "/showhotel", method = RequestMethod.GET)
	public Page<Hotel> getHotel() {
		return hotelService.getHotel(0);
	}
	
	//�ϴ�Hotel
	@RequestMapping(value="/addhotel",method=RequestMethod.POST)
	public Hotel postHotel(
			@RequestParam String hotelName,
			@RequestParam String hotelAddress,
			@RequestParam String hotelPhone,
			@RequestParam String hotelInternet,
			@RequestParam String hotelParking,
			@RequestParam String hotelFac,     //��ʩ
			@RequestParam String hotelOpenTime,
			@RequestParam String hotelIntroduce,//�Ƶ���
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
	
	//hotel����
	@RequestMapping("/Hotel/{hotel_id}/comments")
	public Page<HotelComment> getHotelCommentsOfHotel(@PathVariable int hotel_id){
		return hotelCommentService.findHotelCommentOfHotel(hotel_id, 0);
	}
	//hotel�����ϴ�
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
	//hotel��������ͳ��
	@RequestMapping("/Hotel/{hotel_id}/commentscount")
	public int hotelCommentCount(@PathVariable int hotel_id){
		return hotelCommentService.countComments(hotel_id);
	}
	
	//group(������Ȧ��
	//group�ϴ�
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
	//group ��ʾ
	@RequestMapping(value = "/showgroup", method = RequestMethod.GET)
	public Page<GroupItem> getGroup(){
		return groupService.getGroup(0);
	}
	//group��ҳ��ʾ
	@RequestMapping(value = "/showgroup/{page}", method = RequestMethod.GET)
	public Page<GroupItem> getGroup(@PathVariable int page){
		return groupService.getGroup(page);
	}
	
	//��ʾgroup����
	@RequestMapping("/Group/{group_id}/comments") // ��ʾ��������
	public Page<GroupItemComment> getGroupCommentsOfGroup(@PathVariable int group_id) {
		return groupCommentService.findGroupItemCommentOfGroup(group_id, 0);
	}
	@RequestMapping("/Group/{group_id}/comments/{page}") // ���۷�ҳ��ʾ
	public Page<GroupItemComment> getGroupCommentsOfGroup(@PathVariable int group_id, @PathVariable int page) {
		return groupCommentService.findGroupItemCommentOfGroup(group_id, page);
	}
	
	//group�����ϴ�
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
	
	//ͳ����������
	@RequestMapping("/Group/{group_id}/commentscount")
	public int groupCommentCount(@PathVariable int group_id){
		return groupCommentService.countComments(group_id);
	}
	
	
	//�ж��Ƿ��ע
	@RequestMapping("/{group_author_id}/isConcerned")
	public boolean checkConcern(
			@PathVariable int group_author_id,
			HttpServletRequest request){
		User me = getCurrentUser(request);
		return concernService.checkConcerned(me.getId(), group_author_id);
	}
	//�ύ��ע����(�޸ģ��Թ�עʱȡ����ע)
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
	//��ʾ��ǰ�û���ע����
	@RequestMapping("/Concerns/getMyConcerns")
	public List<Concern> getConcernByUserId(
			HttpServletRequest request){
		User me = getCurrentUser(request);
		return concernService.getConcernByUserId(me.getId());
	}
	//�ӹ�ע�б�����ʾĳ���˷�����group
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
	//visit �б���ʾ
	@RequestMapping(value = "/showvisit", method = RequestMethod.GET)
	public Page<Visit> getVisit() {
		return visitService.getVisit(0);
	}
	//ͨ���ؼ���keyword����visit
	@RequestMapping(value = "/visit/search/{keyword}", method = RequestMethod.GET)
	public Page<Visit> searchByKeyword(
			@PathVariable String keyword){
		System.out.print(keyword);
		return visitService.searchByKeyword(keyword,0);
	}
	//search��������
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
	
	//˽�ţ�����˽��
	@RequestMapping(value = "/privateLatter", method = RequestMethod.POST)
	public PrivateLatter savePrivateLatter(@RequestParam String text,
			@RequestParam String receiverAccount, HttpServletRequest request) {

		// User currentUser=getCurrentUser(request);
		// ͨ�� ���˵�Account(�˺�)��������˽��
		User me = getCurrentUser(request);
		User receiver = userService.findByAccount(receiverAccount);
		PrivateLatter latter = new PrivateLatter();
		latter.setSender(me);
		latter.setReceiver(receiver);
		latter.setLatterText(text);
		return latterService.save(latter);
	}
	// ����˽��(��ǰ��¼�û�Ϊ������)
	@RequestMapping(value = "/getprivateLatter/{receiverId}")
	public Page<PrivateLatter> getLatter(@PathVariable int receiverId, @RequestParam(defaultValue = "0") int page,
			HttpServletRequest request) {
		User me = getCurrentUser(request);
		return latterService.findPrivateLetterByReveiverId(receiverId, me.getId(), page);
	}
	//��ȡδ��˽��
	// ����δ����Ϣ���� get
	@RequestMapping("/unread/{senderId}")
	public int countUnreadMessage(@PathVariable int senderId, HttpServletRequest request) {
		User me = getCurrentUser(request);
		int meId = me.getId();
		return latterService.countUnreadMessages(meId, senderId);
	}
	// �޸�δ����ϢΪ�Ѷ� post
	@RequestMapping(value = "/unread/update", method = RequestMethod.POST)
	public void updateUnread(@RequestParam String senderIdString, HttpServletRequest request) {
		User me = getCurrentUser(request);
		int meId = me.getId();
		int senderId = Integer.parseInt(senderIdString);
		latterService.updateUnread(meId, senderId);
	}
	
}
