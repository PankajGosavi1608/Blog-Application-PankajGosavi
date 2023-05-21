package com.blog.pankaj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.pankaj.entity.Role;

public interface RoleRepo  extends JpaRepository<Role, Integer>{

}