package com.blog.pankaj.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.pankaj.entity.Category;
import com.blog.pankaj.exeption.ResourceNotFoundException;
import com.blog.pankaj.payload.CategoryDto;
import com.blog.pankaj.repository.CategoryRepo;
import com.blog.pankaj.service.CategoryService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
    private ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		log.info("Starting request for creating category");
		Category cat=this.modelMapper.map(categoryDto,Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		log.info("Completed request for creating category");
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		log.info("Starting request for updating category with categoryId :{}",categoryId );
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(() ->new ResourceNotFoundException("Category","Category Id",categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedcat=this.categoryRepo.save(cat);
		log.info("Completed request for updating category with categoryId :{}",categoryId );
		return this.modelMapper.map(updatedcat,CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		log.info("Starting request for delete category with categoryId :{}",categoryId );
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
		this.categoryRepo.delete(cat);
		log.info("Completed request for delete category with categoryId :{}",categoryId );
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		log.info("Starting request for getting category with categoryId :{}",categoryId );
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
		log.info("Completed request for getting category with categoryId :{}",categoryId );
		return this.modelMapper.map(cat,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		log.info("Starting request for getting all categories with categoryId in list format");
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> catDto = categories.stream().map((cat)-> this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
        log.info("Completed request for getting all categories with categoryId in list format");
        return catDto;
	}

}
