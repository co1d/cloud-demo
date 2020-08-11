package com.zplus.cloudauth.service.impl;

import com.zplus.cloudauth.dao.UserRoleDao;
import com.zplus.cloudauth.entity.RoleDO;
import com.zplus.cloudauth.entity.UserRoleDO;
import com.zplus.cloudauth.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService
{
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRoleDO queryUserRoleByUserId(BigInteger userId)
    {
        return userRoleDao.queryUserRoleByUserId(userId);
    }

    @Override
    public List<RoleDO> queryRoleByUserId(BigInteger userId)
    {
        return userRoleDao.queryRoleByUserId(userId);
    }

    @Override
    public void insert(UserRoleDO userRoleDO)
    {
        userRoleDao.insert(userRoleDO);
    }
}
