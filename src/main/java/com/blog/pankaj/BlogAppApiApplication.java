package com.blog.pankaj;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.pankaj.config.AppConstants;
import com.blog.pankaj.entity.Role;
import com.blog.pankaj.repository.RoleRepo;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
		
		System.out.println("User Blog Application Up and Running");
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
   
}
	@Override
	public void run(String... args) throws Exception {
     System.out.println(this.passwordEncoder.encode("Pankaj"));	
     
     try {
			Role role = new Role();

			role.setId(AppConstants.USER_ADMIN);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();

			role1.setId(AppConstants.USER_NORMAL);
			role1.setName("ROLE_NORMAL");

			List<Role> list = List.of(role, role1);

			List<Role> list2 = this.roleRepo.saveAll(list);

			list2.forEach(l -> {
				System.out.println(l.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
