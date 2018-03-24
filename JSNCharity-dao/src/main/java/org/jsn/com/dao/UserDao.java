package org.jsn.com.dao;

import java.util.List;

import org.jsn.com.entity.UserEntity;

public interface UserDao extends AbstractDao<UserEntity> {

	public UserEntity authernticateUser(String userName, String password);

	public boolean deleteUser(List<String> userNameList);

	public List<UserEntity> getAll();

	public boolean isUserNameAvilable(String userName);

	public boolean registerUser(UserEntity userObjeect);

	public boolean updateUser(UserEntity userObjeect);
}
