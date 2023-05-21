package com.blog.pankaj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.pankaj.entity.User;

public interface UserRepo extends JpaRepository <User, Integer>{
	
	Optional<User> findByEmail(String email);

}
