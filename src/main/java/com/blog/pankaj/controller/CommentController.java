package com.blog.pankaj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.pankaj.payload.ApiResponse;
import com.blog.pankaj.payload.CommentDto;
import com.blog.pankaj.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/")
@Slf4j
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * @author Pankaj Gosavi
     * @apiNote This Api is use to create comment with postId
	 * @param comment
	 * @param postId
	 * @return
	 */

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,@PathVariable Integer postId)
	{
		log.info("Starting request for creating comment with postId:{}",postId);
		CommentDto createComment = this.commentService.createComment(comment, postId);
		log.info("Completed request for creating comment with postId:{}",postId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
		
	}
	/**
	 * @author Pankaj Gosavi
	 * @apiNote This Api is use to delete comment with postId
	 * @param commentId
	 * @return
	 */
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
	{
		log.info("Starting request for deleting comment with commentId:{}",commentId);
		this.commentService.deleteComment(commentId);
		log.info("Completed request for deleting comment with commentId:{}",commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Successfully",true),HttpStatus.OK);
		
	}
}
