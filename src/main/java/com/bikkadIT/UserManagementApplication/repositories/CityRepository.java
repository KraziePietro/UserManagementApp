package com.bikkadIT.UserManagementApplication.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bikkadIT.UserManagementApplication.entities.CityMasterEntity;

@Repository
public interface CityRepository {

	public List<CityMasterEntity> findByStateId(Integer stateId);
}
