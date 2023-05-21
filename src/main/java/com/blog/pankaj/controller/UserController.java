package com.blog.pankaj.controller;

import java.util.List;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.pankaj.payload.ApiResponse;
import com.blog.pankaj.payload.UserDto;
import com.blog.pankaj.service.UserService;

import lombok.extern.slf4j.Slf4j;



@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is to create user
	 * @param userDto
	 * @return
	 */
	
	@PostMapping("/") //http://localhost:9292/api/users/ -use this url to create user
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		log.info("Starting request to craete user");
		UserDto createUserDto = this.userService.createUser(userDto);
		log.info("Completed request to craete user");
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is to update user
	 * @param userDto
	 * @param userId
	 * @return
	 */
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId)
	{
		log.info("Starting request to update user with userId:{}",userId);
		UserDto updatedUser =this.userService.updateUser(userDto, userId);
		log.info("Completed request to update user with userId:{}",userId);
		return ResponseEntity.ok(updatedUser);
	}
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is to delete user
	 * @param uid
	 * @return
	 */
	
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		log.info("Starting request to delete user with userId:{}",uid);
        this.userService.deleteUser(uid);
        log.info("Completed request to delete user with userId:{}",uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfuly",true),HttpStatus.OK);
		}
	
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is to get all users
	 * @return
	 */
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		log.info("Starting request to get all users");
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is to get single user
	 * @param userId
	 * @return
	 */
	
	@GetMapping("/{userId}")	
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		log.info("Starting request to get single user with userId:{}",userId);
		return ResponseEntity.ok(this.userService.getUserByUser(userId));
	}
}

