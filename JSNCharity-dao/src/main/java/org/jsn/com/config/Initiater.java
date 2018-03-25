package org.jsn.com.config;

import org.jsn.com.dao.CharityViewDao;
import org.jsn.com.dao.DrugDao;
import org.jsn.com.dao.DrugDaoImpl;
import org.jsn.com.dao.UserDao;
import org.jsn.com.dao.UserDaoImpl;
import org.jsn.com.datasource.SessionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ResourceLoader;

@org.springframework.context.annotation.Configuration
@ImportResource("classpath:configration.xml")
public class Initiater {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	SessionWrapper session;

	@Bean
	public CharityViewDao charityDao() {
		return new CharityViewDao(this.session);
	}

	@Bean
	public DrugDao drugDao() {
		return new DrugDaoImpl(this.session);
	}

	@Bean
	public SessionWrapper session() {
		return new SessionWrapper(this.resourceLoader);
	}

	@Bean
	public UserDao userDao() {
		return new UserDaoImpl(this.session);
	}
}
