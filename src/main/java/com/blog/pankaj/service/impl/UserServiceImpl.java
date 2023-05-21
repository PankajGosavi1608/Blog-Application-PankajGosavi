package com.blog.pankaj.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.pankaj.config.AppConstants;
import com.blog.pankaj.entity.Role;
import com.blog.pankaj.entity.User;
import com.blog.pankaj.exeption.ResourceNotFoundException;
import com.blog.pankaj.payload.UserDto;
import com.blog.pankaj.repository.RoleRepo;
import com.blog.pankaj.repository.UserRepo;
import com.blog.pankaj.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;


	@Override
	public UserDto createUser(UserDto userDto) {
		log.info("Starting request to create user");
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		log.info("Completed request to create user");
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		log.info("Starting request to update user with userId:{}", userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		log.info("Completed request to update user with userId:{}", userId);
		return userDto1;
	}

	@Override
	public UserDto getUserByUser(Integer userId) {

		log.info("Starting request to update user with userId:{}", userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		log.info("Completed request to update user with userId:{}", userId);
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		log.info("Starting request to get all users list");
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		log.info("Completed request to get all users list");
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		log.info("Starting request to delete user with userId:{}", userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);
		log.info("Completed request to delete user with userId:{}", userId);
	}

	private User dtoToUser(UserDto userDto) {
		log.info("Starting request from dto to User with userDto:{}",userDto);
		User user =this.modelMapper.map(userDto,User.class);
		
		/*model mappper saved coding below just above line is enough
		User user = new User();
	    user.setId(userDto.getId());        //for using model mapper we cmnt this
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());*/
		log.info("Completed request from dto to User with userDto:{}",userDto);
		return user;
		
		}

	public UserDto userToDto(User user) {

		log.info("Starting request from user to dto with user:{}", user);
		UserDto userDto = this.modelMapper.map(user, UserDto.class);

		/*
		 * UserDto userDto=new UserDto(); userDto.setId(user.getId());
		 * userDto.setName(user.getName()); userDto.setEmail(user.getEmail());
		 * userDto.setAbout(user.getAbout()); userDto.setPassword(user.getPassword());
		 */
		log.info("Completed request from user to dto with user:{}", user);
		return userDto;

	}
	
	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.USER_NORMAL).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
