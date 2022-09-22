package com.bikkadIT.UserManagementApplication.service;

import java.util.Map;

import com.bikkadIT.UserManagementApplication.binding.LoginForm;
import com.bikkadIT.UserManagementApplication.binding.UnlockAccountForm;
import com.bikkadIT.UserManagementApplication.binding.UserForm;

public interface UserServiceI {

	public String loginCheck(LoginForm loginForm);

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public boolean saveUser(UserForm userForm);

	public boolean unlockeAccount(UnlockAccountForm unlockAccountForm);
	
	public String forgotPwd(String email);
}
