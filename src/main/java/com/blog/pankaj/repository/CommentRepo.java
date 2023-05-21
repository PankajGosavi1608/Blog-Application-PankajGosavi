package com.blog.pankaj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.pankaj.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
 