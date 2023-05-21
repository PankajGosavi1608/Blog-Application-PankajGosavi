package com.blog.pankaj.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.pankaj.entity.Comment;
import com.blog.pankaj.entity.Post;
import com.blog.pankaj.exeption.ResourceNotFoundException;
import com.blog.pankaj.payload.CommentDto;
import com.blog.pankaj.repository.CommentRepo;
import com.blog.pankaj.repository.PostRepo;
import com.blog.pankaj.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		log.info("Starting request for creating comment with postId:{}",postId);
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
         
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		log.info("Completed request for creating comment with postId:{}",postId);
		return this.modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		log.info("Starting request for deleting comment with commentId:{}",commentId);
		Comment com = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","commentId",commentId));
		
		this.commentRepo.delete(com);
		log.info("Completed request for deleting comment with commentId:{}",commentId);
	}

}
