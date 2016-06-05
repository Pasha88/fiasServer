package com.fias.web.dao;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("usersDao")
public class UsersDao {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		session().save(user);
	}

	public boolean exists(String username) {
		User user = getUser(username);
		return user != null;
	}

	// @SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return session().createQuery("from User").list();
	}

	public void foff() {
		String sql = "update databaseupdate set databasefullupdate=1 where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		query.executeUpdate();
	}

	public List<User> getQueryUsers(String username) {
		Criteria crit = session().createCriteria(User.class);
		// crit.createAlias("user", "u");
		// crit.add(Restrictions.eq("u.username", "Pasha123"));

		// return session().createQuery("from User").list();
		crit.add(Restrictions.idEq(username));
		return crit.list();
	}

	public User getUser(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.idEq(username));
		return (User) crit.uniqueResult();
	}

}
