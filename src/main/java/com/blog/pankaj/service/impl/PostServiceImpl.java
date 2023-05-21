package com.blog.pankaj.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.pankaj.entity.Category;
import com.blog.pankaj.entity.Post;
import com.blog.pankaj.entity.User;
import com.blog.pankaj.exeption.ResourceNotFoundException;
import com.blog.pankaj.payload.PostDto;
import com.blog.pankaj.payload.PostResponse;
import com.blog.pankaj.repository.CategoryRepo;
import com.blog.pankaj.repository.PostRepo;
import com.blog.pankaj.repository.UserRepo;
import com.blog.pankaj.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		log.info("Starting request for creating post with userId categoryId :{}",categoryId,userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImagename("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		log.info("Completed request for creating post with userId categoryId :{}",categoryId,userId);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		log.info("Starting request for update post with postId :{}",postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImagename(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		log.info("Completed request for update post with postId :{}",postId);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		log.info("Starting request for delete post with postId :{}",postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		this.postRepo.delete(post);
		log.info("Completed request for update post with postId :{}",postId);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
		log.info("Starting request for getting all posts:{}",pageNumber,pageSize);
		Sort sort = (sortDirection.equalsIgnoreCase("ascending") ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending());

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();

		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		log.info("Completed request for getting all posts:{}",pageNumber,pageSize);
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		log.info("Starting request for getting post by postId :{}",postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		log.info("Completed request for getting post by postId :{}",postId);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {

		log.info("Starting request for getting post with category categoryId :{}",categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> PostDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed request for getting post with category categoryId :{}",categoryId);
		return PostDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		log.info("Starting request for getting posts with userId :{}",userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postsDtos = posts.stream().map((post) -> this.modelMapper.map(posts, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed request for getting post with userId :{}",userId);
		return postsDtos;
	}

	@Override
	public List<PostDto> postSearchByTitle(String keyword) {
		log.info("Starting request for search post with keyword");
		List<Post> list = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> collect = list.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed request for search post with keyword");
		return collect;
	}

}
