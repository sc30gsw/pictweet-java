package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.validate.Unused;

import lombok.Data;

@Data
public class SignupForm {

	@NotBlank
	@Email
	@Unused
	private String email;
	
	@NotBlank
	@Size(min = 4, max = 64)
	private String username;
	
	@NotBlank
	@Size(min = 8, max = 128)
	@Pattern(regexp = "^[A-Za-z0-9]+$")
	private String password;
}
