package com.student.project.TurismoDolomiti.formValidation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
	@NotNull
	@NotEmpty
	@Email
	@Size(min=7, max=128)
	private String email;
	
	@NotNull
	@NotEmpty
	@Size(min=8, max=64)
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
