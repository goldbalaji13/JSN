package org.jsn.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jsn.com.datasource.SessionWrapper;
import org.jsn.com.entity.UserEntity;
import org.jsn.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
public class UserDaoImpl implements UserDao {

	
	private final SessionWrapper session;
	
	@Autowired
	public  UserDaoImpl(SessionWrapper session) {
		this.session = session;
	}

	@Override
	public boolean isUserNameAvilable(String userName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerUser(UserEntity userObjeect) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(UserEntity userObjeect) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(UserEntity userObjeect) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserEntity authernticateUser(String userName, String password) {
		UserDto dto = new UserDto();
		dto.setPassword(password);
		Criteria criteria = session.createCriteria(UserDto.class);
		Map<String,String> propertyNameValues = new HashMap<>();
		propertyNameValues.put("userName", userName);
		propertyNameValues.put("password", dto.getPassword());
		List<UserDto> list = criteria.add(Restrictions.allEq(propertyNameValues)).list();
		if(list.isEmpty()) {
			return null;
		}else {
			return UserEntity.formEntity(list.get(0));
		}
	}
	
	
	
}
