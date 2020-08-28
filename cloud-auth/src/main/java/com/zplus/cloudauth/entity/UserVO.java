package com.zplus.cloudauth.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVO
{
    private BigInteger id;

    private String username;

//    private String password;

    private List<RoleDO> authorities;

}
