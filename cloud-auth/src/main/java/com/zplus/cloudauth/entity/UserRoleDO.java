package com.zplus.cloudauth.entity;

import java.math.BigInteger;

public class UserRoleDO
{
    private BigInteger userId;

    private BigInteger roleId;

    public BigInteger getUserId()
    {
        return userId;
    }

    public void setUserId(BigInteger userId)
    {
        this.userId = userId;
    }

    public BigInteger getRoleId()
    {
        return roleId;
    }

    public void setRoleId(BigInteger roleId)
    {
        this.roleId = roleId;
    }
}
