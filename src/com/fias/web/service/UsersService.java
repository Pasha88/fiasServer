package com.fias.web.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.fias.web.dao.User;
import com.fias.web.dao.UsersDao;

@Service("usersService")
public class UsersService {

	@Autowired
	private UsersDao usersDao;

	public void create(User user) {
		usersDao.create(user);
	}

	public boolean exists(String username) {
		return usersDao.exists(username);
	}

	// @Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		return usersDao.getAllUsers();
	}

	public List<User> getQueryUsers(String current) {
		return usersDao.getQueryUsers(current);
	}

	public User getUser(String username) {
		return usersDao.getUser(username);
	}

	public void doit() {
		usersDao.foff();
	}

	// @Autowired
	/*
	 * @Scheduled(fixedRate = 5000) public void reportCurrentTime() {
	 * 
	 * String verCheck =
	 * "http://fias.nalog.ru/Public/Downloads/Actual/VerDate.txt"; Document doc
	 * = Jsoup.parse(verCheck); //String text = doc.body().text();
	 * System.out.println(doc.toString()); System.out.println("sdada");
	 * 
	 * //System.out.println("The time is now " + dateFormat.format(new Date()));
	 * }
	 */

}
