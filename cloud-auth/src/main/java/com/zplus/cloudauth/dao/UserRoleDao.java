package com.zplus.cloudauth.dao;

import com.zplus.cloudauth.entity.RoleDO;
import com.zplus.cloudauth.entity.UserRoleDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface UserRoleDao
{
    @Select("select * from user_role where user_id=#{userId}")
    UserRoleDO queryUserRoleByUserId(BigInteger userId);

    @Select("select * from role inner join user_role on role.id=user_role.role_id where user_role.user_id=#{userId}")
    List<RoleDO> queryRoleByUserId(BigInteger userId);

    @Insert("insert into user_role (user_id,role_id) values(#{userId},#{roleId})")
    void insert(UserRoleDO userRoleDO);
}
