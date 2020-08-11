package com.zplus.cloudauth.service;


import com.zplus.cloudauth.entity.UserDO;

public interface UserService
{
    UserDO queryUserByName(String username);
}
