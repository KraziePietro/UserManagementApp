package com.bikkadIT.UserManagementApplication.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bikkadIT.UserManagementApplication.binding.LoginForm;
import com.bikkadIT.UserManagementApplication.binding.UnlockAccountForm;
import com.bikkadIT.UserManagementApplication.binding.UserForm;
import com.bikkadIT.UserManagementApplication.entities.CityMasterEntity;
import com.bikkadIT.UserManagementApplication.entities.CountryMasterEntity;
import com.bikkadIT.UserManagementApplication.entities.StateMasterEntity;
import com.bikkadIT.UserManagementApplication.entities.UserAcccountEntity;
import com.bikkadIT.UserManagementApplication.props.AppConstant;
import com.bikkadIT.UserManagementApplication.props.AppProps;
import com.bikkadIT.UserManagementApplication.repositories.CityRepository;
import com.bikkadIT.UserManagementApplication.repositories.CountryRepository;
import com.bikkadIT.UserManagementApplication.repositories.StateRepository;
import com.bikkadIT.UserManagementApplication.repositories.UserAccountRepository;
import com.bikkadIT.UserManagementApplication.util.EmailUtils;

@Service
public class UserServiceImpl implements UserServiceI{

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private AppProps appProps;
	@Override
	public String loginCheck(LoginForm loginForm) {
		UserAcccountEntity userAcccountEntity = userAccountRepository.findByEmailAndPassword(loginForm.getEmail(),
				loginForm.getPassword());
Map<String,String> messages = appProps.getMessages();
		  
		if (userAcccountEntity != null) {
			String accStatus = userAcccountEntity.getAccStatus();
			if (accStatus.equals(AppConstant.LOCKED)) {
				return messages.get(AppConstant.ACCOUNT_LOCKED);
			} else {
				return messages.get(AppConstant.LOGIN_SUCCESS);
			}

		} else {
			return messages.get(AppConstant.INVALID_CREDITIONALS);
		}

	}

	@Override
	public Map<Integer, String> getCountries() {

		List<CountryMasterEntity> findAll = countryRepository.findAll();
		Map<Integer, String> countryMap = new HashMap<Integer, String>();
		for (CountryMasterEntity u : findAll) {
			countryMap.put(u.getCountryId(), u.getCountryName());
		}

		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateMasterEntity> states = stateRepository.findByCountryId(countryId);

		Map<Integer, String> statemap = new HashMap<Integer, String>();
		for (StateMasterEntity u : states) {
			statemap.put(u.getStateId(), u.getStateName());
		}
		return statemap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityMasterEntity> findByStateId = cityRepository.findByStateId(stateId);

		Map<Integer, String> cityMap = new HashMap<Integer, String>();

		for (CityMasterEntity u : findByStateId) {
			cityMap.put(u.getCityId(), u.getCityname());
		}

		return cityMap;
	}

	@Override
	public boolean saveUser(UserForm userForm) {

		userForm.setAccStatus(AppConstant.LOCKED);
		userForm.setPassword(generateRandomPassword());
		UserAcccountEntity userAcccountEntity = new UserAcccountEntity();
		BeanUtils.copyProperties(userForm, userAcccountEntity);

		UserAcccountEntity save = userAccountRepository.save(userAcccountEntity);

		if (save != null) {
			String Subject = "Please Check Your mail to unlock account";
			String body = getUserRegEmailBody(userForm);
			emailUtils.sendMail(userForm.getEmail(), Subject, body);
			return true;
		}
		return false;
	}

	private String getUserRegEmailBody(UserForm userForm) {

		StringBuffer sb = new StringBuffer();

		String fileName = "UNLOCK-ACC-EMAIL-BODY-TEMPLETE.txt";

		List<String> lines = new ArrayList();

		BufferedReader br;
		try {
			br = Files.newBufferedReader(Paths.get(fileName));
			lines = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lines.forEach(line -> {
			if (line.contains("{FNAME}")) {
				line = line.replace("{FNAME}", userForm.getFname());
			}

			if (line.contains("{LNAME}")) {
				line = line.replace("{LNAME}", userForm.getLname());
			}

			if (line.contains("{TEMP-PWD}")) {
				line = line.replace("{TEMP-PWD}", userForm.getPassword());
			}

			sb.append(line);

		});

		return sb.toString();

	}

	private String generateRandomPassword() {

		String randomPassword = RandomStringUtils.randomAlphanumeric(6);

		return randomPassword;

	}

	@Override
	public boolean unlockeAccount(UnlockAccountForm unlockAccountForm) {
		String email = unlockAccountForm.getEmail();
		String tmpPwd = unlockAccountForm.getTempPwd();

		UserAcccountEntity user = userAccountRepository.findByEmailAndPassword(email, tmpPwd);

		if (user != null) {
			user.setAccStatus("UNLOCKED");
			user.setPassword(unlockAccountForm.getNewPwd());
			userAccountRepository.save(user);
			return true;

		}

		return false;
	}

	@Override
	public String forgotPwd(String email) {
		UserAcccountEntity user = userAccountRepository.findByEmail(email);

		if (user != null) {
			String subject = "Password is sent to your mail id.Check your mail";
			String body = getForgotpassEmail(user);
			emailUtils.sendMail(email, subject, body);
			return "SUCCESS";
		}

		return "FAIL";
	}

	private String getForgotpassEmail(UserAcccountEntity userForm) {

		StringBuffer sb = new StringBuffer();

		String fileName = "RECOVER-PASS-EMAIL-BODY-TEMPLETE.txt";

		List<String> lines = new ArrayList();

		BufferedReader br;
		try {
			br = Files.newBufferedReader(Paths.get(fileName));
			lines = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lines.forEach(line -> {
			if (line.contains("{FNAME}")) {
				line = line.replace("{FNAME}", userForm.getFname());
			}

			if (line.contains("{LNAME}")) {
				line = line.replace("{LNAME}", userForm.getLname());
			}

			if (line.contains("{TEMP-PWD}")) {
				line = line.replace("{TEMP-PWD}", userForm.getPassword());
			}

			sb.append(line);

		});

		return sb.toString();
		

	}

}
