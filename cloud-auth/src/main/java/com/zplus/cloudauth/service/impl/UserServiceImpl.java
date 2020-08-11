package com.zplus.cloudauth.service.impl;

import com.zplus.cloudauth.dao.UserDao;
import com.zplus.cloudauth.entity.UserDO;
import com.zplus.cloudauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDO queryUserByName(String username)
    {
        //TODO 替换成Principal
        return userDao.queryUserByName(username);
    }
}
