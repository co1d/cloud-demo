package com.zplus.cloudauth.service;


import com.zplus.cloudauth.entity.RoleDO;
import com.zplus.cloudauth.entity.UserRoleDO;

import java.math.BigInteger;
import java.util.List;

public interface UserRoleService
{
    UserRoleDO queryUserRoleByUserId(BigInteger userId);

    List<RoleDO> queryRoleByUserId(BigInteger userId);

    void insert(UserRoleDO userRoleDO);
}
