package org.jsn.com.config;

import java.io.File;
import java.io.IOException;
import javax.annotation.PreDestroy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jsn.com.dao.UserDao;
import org.jsn.com.dao.UserDaoImpl;
import org.jsn.com.datasource.SessionWrapper;
import org.jsn.dto.UserDto;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;

@org.springframework.context.annotation.Configuration
@ImportResource("classpath:configration.xml")
@ComponentScan("org.jsn.com")
public class Initiater implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	SessionWrapper session;
	
	@Bean
	public UserDao userDao() {
		return new UserDaoImpl(session);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
