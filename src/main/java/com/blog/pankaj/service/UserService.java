package com.blog.pankaj.service;

import java.util.List;

import com.blog.pankaj.payload.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,Integer userId);
	
	UserDto getUserByUser(Integer userId);
	
	UserDto getUserById(Integer userId);
	
    List<UserDto> getAllUsers();
    
    void deleteUser(Integer userId);
	
	
}
