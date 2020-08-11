package com.zplus.cloudauth.service.impl;

import com.zplus.cloudauth.entity.RoleDO;
import com.zplus.cloudauth.entity.UserDO;
import com.zplus.cloudauth.service.UserRoleService;
import com.zplus.cloudauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserDO userDO =userService.queryUserByName(username);
        if(userDO ==null)
        {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<RoleDO> roleEntities=userRoleService.queryRoleByUserId(userDO.getId());
        userDO.setAuthorities(roleEntities);
        log.info(userDO.toString());
        return userDO;
    }
}
