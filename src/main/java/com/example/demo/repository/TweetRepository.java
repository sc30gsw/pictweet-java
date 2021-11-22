package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MTweet;

@Repository
public interface TweetRepository extends JpaRepository<MTweet, Integer> {

}
