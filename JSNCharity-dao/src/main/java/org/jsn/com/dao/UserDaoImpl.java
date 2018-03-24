package org.jsn.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jsn.com.datasource.SessionWrapper;
import org.jsn.com.entity.UserEntity;
import org.jsn.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoImpl implements UserDao {

	private final SessionWrapper session;

	@Autowired
	public UserDaoImpl(SessionWrapper session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserEntity authernticateUser(String userName, String password) {
		UserDto dto = new UserDto();
		dto.setPassword(password);
		Criteria criteria = this.session.createCriteria(UserDto.class);
		Map<String, String> propertyNameValues = new HashMap<>();
		propertyNameValues.put("userName", userName);
		propertyNameValues.put("password", dto.getPassword());
		List<UserDto> list = criteria.add(Restrictions.allEq(propertyNameValues)).list();
		if (list.isEmpty()) {
			return null;
		} else {
			return UserEntity.formEntity(list.get(0));
		}
	}

	@Override
	public boolean deleteUser(List<String> userNameList) {
		Criteria criteria = this.session.createCriteria(UserDto.class);
		criteria.add(Restrictions.in("userName", userNameList));
		List<UserDto> list = criteria.list();
		Transaction transaction = this.session.beginTransaction();
		list.stream().forEach(this.session::delete);
		transaction.commit();
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAll() {
		List<UserDto> dtoList = this.session.createCriteria(UserDto.class).list();
		return dtoList.stream().map(UserEntity::formEntity).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isUserNameAvilable(String userName) {
		Criteria criteria = this.session.createCriteria(UserDto.class);
		Map<String, String> propertyNameValues = new HashMap<>();
		propertyNameValues.put("userName", userName);
		List<UserDto> list = criteria.add(Restrictions.allEq(propertyNameValues)).list();
		if (list.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean registerUser(UserEntity userObjeect) {
		Transaction transaction = this.session.beginTransaction();
		this.session.persist(userObjeect.formDto());
		transaction.commit();
		return false;
	}

	@Override
	public List<UserEntity> search(Map<String, Object> searchCriteriaMap, String SearchText) {
		Criteria criteria = this.session.createCriteria(UserDto.class);
		criteria.add(Restrictions.like("userName", SearchText));
		List<UserDto> list = criteria.add(Restrictions.allEq(searchCriteriaMap)).list();
		return list.stream().map(UserEntity::formEntity).collect(Collectors.toList());
	}

	@Override
	public boolean updateUser(UserEntity userObjeect) {
		Transaction transaction = this.session.beginTransaction();
		UserDto userDto = (UserDto) this.session.load(UserDto.class, userObjeect.getUserName());
		userDto.setAddress(userObjeect.getAddress());
		userDto.setName(userObjeect.getName());
		userDto.setContactNo(userObjeect.getContactNo());
		userDto.setCity(userObjeect.getCity());
		this.session.persist(userDto);
		transaction.commit();
		return false;
	}

}
