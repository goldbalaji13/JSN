package org.jsn.com.dao;

import org.jsn.com.entity.UserEntity;

public interface UserDao {

	public boolean isUserNameAvilable(String userName);
	
	public boolean registerUser(UserEntity userObjeect);
	
	public boolean updateUser(UserEntity userObjeect);
	
	public boolean deleteUser(UserEntity userObjeect);
	
	public UserEntity authernticateUser(String userName,String password);
}
