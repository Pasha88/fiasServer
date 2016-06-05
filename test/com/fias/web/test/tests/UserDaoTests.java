package com.fias.web.test.tests;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fias.web.dao.User;
import com.fias.web.dao.UsersDao;

@ActiveProfiles("dev")

@ContextConfiguration(locations = {
		"classpath:com/fias/web/config/dao-context.xml",
		"classpath:com/fias/web/config/security-context.xml",
		"classpath:com/fias/web/test/config/datasource.xml" })

@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private DataSource dataSource;	
	
	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
	}
	
	@Test public void doSomething() {
		List<User> users2 = usersDao.getAllUsers();
		//List<Offer> offer3 = offersDao.getOffers();
		System.out.println(users2);
		User user2 = new User("gooooglemoogle", "Goooogle Moogle", "doitbropls",
				"nomoneynofunny@gmail.com", 1, "ROLE_ADMIN");
		user2.setPassword("etoparol");
		usersDao.create(user2);
	}	
}
