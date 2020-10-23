package com.mushanwb.github.service;

import com.mushanwb.github.dao.BlogDao;
import com.mushanwb.github.entity.Blog;
import com.mushanwb.github.entity.Result;

import javax.inject.Inject;
import java.util.List;

public class BlogService {

    private BlogDao blogDao;

    @Inject
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public Result getBlogs(Integer page, Integer pageSize, Integer userId) {
        return null;
    }
}
