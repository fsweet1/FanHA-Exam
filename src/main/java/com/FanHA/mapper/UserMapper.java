package com.FanHA.mapper;

import com.FanHA.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author HeTao
 * @data 2023/4/8
 **/
public interface UserMapper {
    /**
     * 根据用户名和密码查询用户对象
     * @param username 用户名
     * @param password 密码
     * @return User对象
     */
    @Select("select * from user where username = #{username} and password = #{password}")
    User select(@Param("username") String username, @Param("password")  String password);

    /**
     * 根据用户名查询用户对象
     * @param username 用户名
     * @return User对象
     */
    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

    /**
     * 添加用户
     * @param user 用户对象
     */
    @Insert("insert into user values(null,#{username},#{password})")
    void add(User user);
}
