package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class MUser {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(unique = true)
	private String email;
	
	@Column(name = "name")
	private String username;
	
	private String password;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createTime;
	
	private String role;
	
	@OneToMany
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private List<MTweet> tweetList;
	
	@OneToMany
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private List<TComment> commentList;
}