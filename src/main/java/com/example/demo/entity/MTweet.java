package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name = "tweets")
public class MTweet {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tweetId;
	
	private String text;
	
	@Column(name = "image")
	private String imageUrl;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createTime;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updateTime;

}
