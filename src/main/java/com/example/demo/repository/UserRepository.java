package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MUser;

@Repository
public interface UserRepository extends JpaRepository<MUser, Integer> {

	 public Optional<MUser> findByEmail(String email);
	 
}
