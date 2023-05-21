package com.blog.pankaj.service;

import java.util.List;

import com.blog.pankaj.payload.PostDto;
import com.blog.pankaj.payload.PostResponse;

public interface PostService {
	
	
	// create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all post 

    PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDirection);
    
    //get single post 
    PostDto getPostById(Integer postId);
    
    //get All Post by category
    
    List<PostDto> getPostsByCategory(Integer categoryId);
    
   //get All Post by user
    List<PostDto> getPostsByUser(Integer userId);
    
    // search post
    
    List<PostDto> postSearchByTitle(String keyword);
	

}
