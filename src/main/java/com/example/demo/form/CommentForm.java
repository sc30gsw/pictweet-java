package com.example.demo.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CommentForm {

	@NotBlank
	private String commentText;
	
}
