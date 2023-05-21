package com.blog.pankaj.service;

import java.util.List;

import com.blog.pankaj.payload.CategoryDto;

public interface CategoryService {
	
	
	//CREATE 
	
	 CategoryDto createCategory(CategoryDto categoryDto);
	
	//UPDATE
	 CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

	//DELETE
	 void deleteCategory(Integer categoryId);

	//GET by ID
	 CategoryDto getCategory(Integer categoryId);
	
	//GETALL
	 
	 List<CategoryDto> getCategories();
	
	
	
	
	
	
	
	
	
	
	
	

}
