package com.blog.pankaj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.pankaj.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
