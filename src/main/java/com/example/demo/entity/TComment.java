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

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name= "comments")
public class TComment {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;
	
	@Column(name = "text")
	private  String commentText;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "tweet_id")
	private Integer tweetId;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createTime;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MUser user;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "tweet_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MTweet tweet;
}
