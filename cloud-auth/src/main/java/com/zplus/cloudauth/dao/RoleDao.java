package com.zplus.cloudauth.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface RoleDao
{
    @Select("select name where id=#{roleId})")
    String queryRoleNameById(BigInteger roleId);
}
