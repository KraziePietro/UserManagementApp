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

	private Long phno;

	private LocalDate dob;

	private String gender;

	private Integer cityId;

	private Integer stateId;

	private Integer countryId;

	private String accStatus;
	
}
