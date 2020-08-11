package com.zplus.cloudauth.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.AuthenticatedPrincipal;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO implements AuthenticatedPrincipal, Serializable
{
    private BigInteger id;

    private String username;

    private String password;

    private List<RoleDO> authorities;

    @Override
    public String getName()
    {
        return username;
    }
}
