package com.zplus.cloudauth.dao;

import com.zplus.cloudauth.entity.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao
{
    @Select("select * from user where username=#{username}")
    UserDO queryUserByName(String username);

    @Select("select * from user")
    List<UserDO> queryUsers();

    @Insert("insert into user(username,password) values (#{username},#{password})")
    void insert(UserDO userDO);

}
