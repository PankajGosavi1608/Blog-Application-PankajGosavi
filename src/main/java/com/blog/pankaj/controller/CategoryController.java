package com.blog.pankaj.controller;

import java.util.List;

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

import com.blog.pankaj.config.AppConstants;
import com.blog.pankaj.payload.ApiResponse;
import com.blog.pankaj.payload.CategoryDto;
import com.blog.pankaj.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/categorie")
@Slf4j
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
/**
 * @author Pankaj Gosavi
 * @apiNote This Api is use to create category
 * @param categDto
 * @return
 */
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categDto){
		log.info("Starting request for creating category");
		CategoryDto createCategory = this.categoryService.createCategory(categDto);
		log.info("Completed request for creating category");
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
		
	}
	
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to update category with categoryId
	 * @param categoryDto
	 * @param catId
	 * @return
	 */
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		log.info("Starting request for updating category with categoryId :{}",categoryId );
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,categoryId);
		log.info("Completed request for updating category with categoryId :{}",categoryId );
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to delete category with categoryId
	 * @param categoryDto
	 * @param catId
	 * @return
	 */
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		log.info("Starting request for deleting category with categoryId :{}",categoryId );
		this.categoryService.deleteCategory(categoryId);
		log.info("Completed request for deteting category with categoryId :{}",categoryId );
		return new ResponseEntity<ApiResponse>(new ApiResponse (AppConstants.DELETE_CATEGORY,true),HttpStatus.OK);

}
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to get category by categoryId
	 * @param categoryDto
	 * @param catId
	 * @return
	 */
	
	//get category by id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
		log.info("Starting request for getting category with categoryId :{}",categoryId );
		CategoryDto Singlecategory=this.categoryService.getCategory(categoryId);
		log.info("Completed request for getting category with categoryId :{}",categoryId );
		return new ResponseEntity<CategoryDto>(Singlecategory,HttpStatus.OK);
		
}
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to get list of categories with categoryId
	 * @param categoryDto
	 * @param catId
	 * @return
	 */

	//get all posts
		@GetMapping("/{catId}")
		public ResponseEntity<List<CategoryDto>> getCategories(){
			log.info("Starting request for getting all categories with categoryId in list format");
			List<CategoryDto> categories=this.categoryService.getCategories();
			log.info("Completed request for getting all categories with categoryId in list format");
			return ResponseEntity.ok(categories);
				
	}
		//get post details by id
		
}