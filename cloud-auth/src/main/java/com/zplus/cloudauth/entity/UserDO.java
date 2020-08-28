package com.zplus.cloudauth.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public class UserDO implements UserDetails
{
    private static final long serialVersionUID = -5055697825946270055L;
    private BigInteger id;

    private String username;

    private String password;

    private List<RoleDO> authorities;

    public BigInteger getId()
    {
        return id;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(List<RoleDO> authorities)
    {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"id\": \"");
        builder.append(id);
        builder.append("\",\"username\": \"");
        builder.append(username);
        builder.append("\",\"password\": \"");
        builder.append(password);
        builder.append("\",\"authorities\": \"");
        builder.append(authorities);
        builder.append("\"}");
        return builder.toString();
    }
}
