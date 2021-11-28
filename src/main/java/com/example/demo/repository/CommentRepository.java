package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TComment;

@Repository
public interface CommentRepository extends JpaRepository<TComment, Integer> {

}
