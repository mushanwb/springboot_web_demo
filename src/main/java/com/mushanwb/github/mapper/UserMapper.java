package com.mushanwb.github.mapper;

import com.mushanwb.github.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User findUserById(@Param("id") Integer id);

    @Select("select * from user where username = #{username}")
    User findUserByUsername(@Param("username") String username);

    @Select("insert into user (username, encrypt_password, created_at, updated_at) " +
            "values (#{username}, #{encode}, now(), now())")
    void save(@Param("username") String username, @Param("encode") String encode);
}
