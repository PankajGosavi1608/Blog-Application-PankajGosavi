package com.blog.pankaj.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.pankaj.config.AppConstants;
import com.blog.pankaj.payload.ApiResponse;
import com.blog.pankaj.payload.PostDto;
import com.blog.pankaj.payload.PostResponse;
import com.blog.pankaj.service.FileService;
import com.blog.pankaj.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/")
@Slf4j
public class PostController {

	@Autowired
	private PostService postservice;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to create post
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */

	@PostMapping("/userId/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) 
	{
		log.info("Starting request for creating post with userId categoryId :{}",categoryId,userId);
		PostDto createPost = this.postservice.createPost(postDto, userId, categoryId);
		log.info("Completed request for creating post with userId categoryId :",categoryId,userId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to get post by user
	 * @param userId
	 * @return
	 */
	// Get by User

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId

	) {
		log.info("Starting request for getting post with userId :{}",userId);
		List<PostDto> posts = this.postservice.getPostsByUser(userId);
		log.info("Completed request for getting post with userId :{}",userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to get post by category
	 * @param categoryId
	 * @return
	 */
	// Get by Category

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		
		log.info("Starting request for getting post with category categoryId :{}",categoryId);
		List<PostDto> posts = this.postservice.getPostsByUser(categoryId);
		log.info("Completed request for getting post with category categoryId :{}",categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to get all posts
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param sortDirection
	 * @return
	 */
	// get all posts

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection)

	{
		log.info("Starting request for getting all posts:{}",pageNumber,pageSize);
		PostResponse postResponse = this.postservice.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
		log.info("Completed request for getting all posts:{}",pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to get posts by id
	 * @param postId
	 * @return
	 */

	// get post by Id

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		log.info("Starting request for getting post by postId :{}",postId);
		PostDto postDto = this.postservice.getPostById(postId);
		log.info("Completed request for getting post by postId :{}",postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to delete post by id
	 * @param postId
	 * @return
	 */

	// Delete post by Id

	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		log.info("Starting request for deleting post by postId :{}",postId);
		this.postservice.deletePost(postId);
		log.info("Completed request for deleting post by postId :{}",postId);
		return new ApiResponse("Post is deleted Successfully", true);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to update posts by id
	 * @param postDto
	 * @param postId
	 * @return
	 */

	// Update post by Id

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		log.info("Starting request for updating post by postId :{}",postId);
		PostDto updatePost = this.postservice.updatePost(postDto, postId);
		log.info("Completed request for updating post by postId :{}",postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to search post by id
	 * @param keywords
	 * @return
	 */

	// Search Post by Id

	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		log.info("Starting request for search post by keywords :{}",keywords);
		List<PostDto> result = this.postservice.postSearchByTitle(keywords);
		log.info("Completed request for search post by keywords :{}",keywords);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to post image upload
	 * @param image
	 * @param postId
	 * @return
	 * @throws IOException
	 */
	// Post Image upload

	@PostMapping("/api/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException

	{
		log.info("Starting request for post image upload");
		PostDto postDto = this.postservice.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postservice.updatePost(postDto, postId);
		log.info("Completed request for post image upload");
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to serve files
	 * @param imageName
	 * @param response
	 * @throws IOException
	 */

	// Methods to serve files

	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		log.info("Starting request for serving files imageName:{}",imageName);
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		log.info("Completed request for serving files imageName:{}",imageName);
		StreamUtils.copy(resource, response.getOutputStream());

	}

}
