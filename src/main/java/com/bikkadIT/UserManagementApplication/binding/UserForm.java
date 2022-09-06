package com.bikkadIT.UserManagementApplication.binding;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;


@Data
public class UserForm {
	
	private String fname;
	
	private String lname;
	
	private String email;
	
	private String password;
	
	private String phno;
	
	private LocalDate dob;
	
	private String gender;
	
	private Integer countryId;
	
	private Integer stateId;
	
	private Integer cityId;
	
}
