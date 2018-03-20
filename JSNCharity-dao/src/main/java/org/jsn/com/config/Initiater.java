package org.jsn.com.config;

import org.jsn.com.dao.UserDao;
import org.jsn.com.dao.UserDaoImpl;
import org.jsn.com.datasource.SessionWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@org.springframework.context.annotation.Configuration
@ImportResource("classpath:configration.xml")
@ComponentScan("org.jsn.com")
public class Initiater implements ApplicationContextAware {

	// public static void main(String[] args) {
	// ApplicationContext cont = new
	// AnnotationConfigApplicationContext(Initiater.class);
	// UserDao dao = cont.getBean(UserDao.class);
	// UserEntity user =
	// UserEntity.builder().userName("JsnAdmin").password("JsnAdmin").role(Role.ADMIN)
	// .name("Balaji
	// Rajan").city("Chennai").contactNo("8903177053").address("Shoulinganalur").build();
	// dao.updateUser(user);
	// }

	private ApplicationContext applicationContext;

	@Autowired
	SessionWrapper session;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean
	public UserDao userDao() {
		return new UserDaoImpl(this.session);
	}
}
