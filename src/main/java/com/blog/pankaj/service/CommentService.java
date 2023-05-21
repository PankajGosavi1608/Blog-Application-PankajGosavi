package com.blog.pankaj.service;

import com.blog.pankaj.payload.CommentDto;


public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	void deleteComment(Integer commentId);

}
