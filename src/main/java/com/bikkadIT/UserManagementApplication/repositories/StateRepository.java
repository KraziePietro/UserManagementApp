package com.bikkadIT.UserManagementApplication.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bikkadIT.UserManagementApplication.entities.StateMasterEntity;

@Repository
public interface StateRepository {

	public List<StateMasterEntity> findByCountryId(Integer countryId);
}
