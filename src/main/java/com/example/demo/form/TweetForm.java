package com.example.demo.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class TweetForm {

	@NotBlank
	private String text;
	
	@URL
	private String imageUrl;
}
