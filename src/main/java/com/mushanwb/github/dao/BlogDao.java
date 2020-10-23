package com.mushanwb.github.dao;

import com.mushanwb.github.entity.Blog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogDao {

    private final SqlSession sqlSession;

    @Inject
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogs(Integer page, Integer pageSize, Integer userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("offset", (page - 1) * pageSize);
        param.put("limit", pageSize);
        return sqlSession.selectList("selectBlog", param);
    }
}
