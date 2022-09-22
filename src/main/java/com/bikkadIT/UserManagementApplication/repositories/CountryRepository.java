package com.bikkadIT.UserManagementApplication.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bikkadIT.UserManagementApplication.entities.CountryMasterEntity;

@Repository
public interface CountryRepository extends JpaRepository<CountryMasterEntity, Serializable> {

	
}
