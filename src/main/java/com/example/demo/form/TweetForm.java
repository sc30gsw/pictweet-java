package com.example.demo.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TweetForm {

	@NotBlank
	private String text;
	
	private String imageUrl;
}
