package com.bikkadIT.UserManagementApplication.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bikkadIT.UserManagementApplication.entities.UserAcccountEntity;
@Repository
public interface UserAccountRepository extends JpaRepository<UserAcccountEntity, Serializable> {

	public UserAcccountEntity findByEmailAndPassword(String email,String password);
   
	  public UserAcccountEntity findByEmail(String email);


}

