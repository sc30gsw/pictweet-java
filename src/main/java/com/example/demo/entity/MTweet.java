package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Entity
@Data
@Table(name = "tweets")
public class MTweet {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tweetId;
	
	@NotBlank
	private String text;
	
	@Column(name = "image")
	@URL
	private String imageUrl;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createTime;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updateTime;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MUser user;

}
