package com.blog.pankaj.config;

public class AppConstants {
	
	
	public static final String PAGE_NUMBER="1";

	public static final String PAGE_SIZE="2";
	
	public static final String SORT_BY="postId";
	
	public static final String SORT_DIRECTION="ascending";
	
	public static final String DELETE_CATEGORY="category deleted successfully";
	
	public static final Integer USER_NORMAL = 102;
	
	public static final Integer USER_ADMIN =101;
	
	public static final String[] PUBLIC_URLS = { "/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
			"/swagger-resources/**", "swagger-ui/**","webjars/**"};

	
}
