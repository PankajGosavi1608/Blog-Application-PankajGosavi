package com.blog.pankaj.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.pankaj.exeption.ApiException;
import com.blog.pankaj.payload.JwtAuthRequest;
import com.blog.pankaj.payload.JwtAuthResponse;
import com.blog.pankaj.payload.UserDto;
import com.blog.pankaj.repository.UserRepo;
import com.blog.pankaj.security.JwtTokenHelper;
import com.blog.pankaj.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper mapper;

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is to create Token
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		log.info("Starting request for login activities");
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.mapper.map((User) userDetails, UserDto.class));

		log.info("Completed request for login activities");
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is for Authentication purpose
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	private void authenticate(String username, String password) throws Exception {

		log.info("Starting request for user authentication activities");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}

	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is to Register new user
	 * @param userDto
	 * @return
	 */

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {

		log.info("Starting request for user register activities");
		UserDto registeredUser = this.userService.registerNewUser(userDto);

		log.info("Completed request for user register activities");
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to get current/logged user data
	 * @param principal
	 * @return
	 */

	@GetMapping("/current-user/")
	public ResponseEntity<UserDto> getUser(Principal principal) {

		log.info("Starting request for get user activities");
		com.blog.pankaj.entity.User user = this.userRepo.findByEmail(principal.getName()).get();

		log.info("Complete request for get user activities");
		return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
	}

}