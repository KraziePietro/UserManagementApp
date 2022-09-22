package com.bikkadIT.UserManagementApplication.repositories;

import com.bikkadIT.UserManagementApplication.entities.UserAccountEntity;

public interface UserAccountRepository {

	public UserAccountEntity findByEmailAndPassword(String email,String password);
	   
	  public UserAccountEntity findByEmail(String email);

}
