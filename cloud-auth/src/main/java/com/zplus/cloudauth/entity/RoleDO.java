package com.zplus.cloudauth.entity;

import org.springframework.security.core.GrantedAuthority;

import java.math.BigInteger;

public class RoleDO implements GrantedAuthority
{
    private BigInteger id;

    private String name;
    private String authority;

    public void setId(BigInteger id)
    {
        this.id=id;
    }

    @Override
    public String getAuthority()
    {
        return name;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"id\": \"");
        builder.append(id);
        builder.append("\",\"name\": \"");
        builder.append(name);
        builder.append("\"}");
        return builder.toString();
    }
}
