package com.zplus.cloudauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zplus.cloudauth.RedisUtils;
import com.zplus.cloudauth.entity.LoginVO;
import com.zplus.cloudauth.entity.UserDO;
import com.zplus.cloudauth.entity.UserVO;
import com.zplus.cloudauth.service.UserService;
import com.zplus.commonutils.ResponseEnum;
import com.zplus.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController
{
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/user/info")
    public Map<String, Object> info(@RequestHeader String authorization)
    {
        //必须通过客户端{携带的token在服务端的token存储中获取用户信息。
        //header中 Authorization传过来的格式为[type token]的格式
        //因此必须先对Authorization传过来的数据进行分隔authorization.split(" ")[1]才是真正的token
        Map<String, Object> map = new HashMap<>();
        OAuth2Authentication authen = null;
        try
        {
            authen = tokenStore.readAuthentication(authorization.split(" ")[1]);
            if (authen == null)
            {
                map.put("error", "invalid token !");
                return map;
            }
        } catch (Exception e)
        {
            map.put("error", e);
            return map;
        }
        ObjectMapper objectMapper=new ObjectMapper();
        UserVO userVO;
        userVO=objectMapper.convertValue(authen.getPrincipal(),UserVO.class);
        //注意这两个key都不能随便填，都是和客户端进行数据处理时进行对应的。
        map.put("user", userVO);
        // map.put("authorities", authen.getAuthorities());
        map.put("token", tokenStore.getAccessToken(authen).getValue());
        return map;
    }

    //header加 Authorization:bearer {token}
    @GetMapping("/user/me")
    public Principal user(Principal principal)
    {
        return principal;
    }

    @GetMapping("/user/test1")
    public Result<Object> test()
    {
        return Result.ok("test1");
    }

    @GetMapping("/user/test2")
    public Result<Object> test2()
    {
        return Result.ok("test2");
    }

    // TODO 浏览器带着token,如果返回给前端token错误,前端传用户名密码调用后端刷新token接口,后端post到/oauth/token,
    // TODO 如果返回refreshToken过期则返回前端重新登录
    @PostMapping("/user/login")
    public Result<Object> login(LoginVO loginVO)
    {
        UserDO userDO = userService.queryUserByName(loginVO.getUsername());
        if (userDO == null)
        {
            return Result.error(ResponseEnum.USER_NOT_FOUND_ERROR);
        }
        boolean authUser = passwordEncoder.matches(loginVO.getPassword(), userDO.getPassword());
        if (!authUser)
        {
            return Result.error(ResponseEnum.USER_LOGIN_FAIL_ERROR);
        }
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.set("grant_type", "password");
        paramsMap.set("username", loginVO.getUsername());
        paramsMap.set("password", loginVO.getPassword());
        paramsMap.set("scope", "all");
        paramsMap.set("client_id", "dev");
        paramsMap.set("client_secret", "zplus");

        // 构造请求的实体。包含body和headers的内容
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramsMap, null);
        // 进行请求，并返回数据
        Map<?,?> authInfo = restTemplate.postForObject("http://localhost:7001/oauth/token", request, Map.class);
        if(authInfo==null)
        {
            return Result.error(ResponseEnum.TOKEN_AUTH_ERROR);
        }
        String token = String.valueOf(authInfo.get("access_token"));
        Map<String, Object> data = new HashMap<>();
        OAuth2Authentication authen = tokenStore.readAuthentication(token);
        if (authen == null)
        {
            return Result.error(ResponseEnum.TOKEN_AUTH_ERROR);
        }
        ObjectMapper objectMapper=new ObjectMapper();

        data.put("user", objectMapper.convertValue(authen.getPrincipal(),Map.class));
        data.put("accessToken", token);
        return Result.ok(data);
    }

    @GetMapping("/user/refresh")
    public Result<?> refresh(String username,String accessToken)
    {

        String refreshToken= String.valueOf(stringRedisTemplate.opsForValue().get("token:access_to_refresh:"+accessToken));
        if("null".equals(refreshToken))
        {
            return Result.error(ResponseEnum.USER_LOGIN_EXPIRE_ERROR);
        }
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.set("grant_type", "refresh_token");
        paramsMap.set("username", username);
        paramsMap.set("client_id", "dev");
        paramsMap.set("client_secret", "zplus");
        paramsMap.set("refresh_token", refreshToken);
        // 构造请求的实体。包含body和headers的内容
        HttpEntity<MultiValueMap<String, String>> requestParam = new HttpEntity<>(paramsMap, null);
        // 进行请求，并返回数据
        Map<?,?> authInfo = restTemplate.postForObject("http://localhost:7001/oauth/token", requestParam, Map.class);
        String token = String.valueOf(authInfo.get("access_token"));
        Map<String,Object> tokenMap=new HashMap<>();
        tokenMap.put("accessToken",token);
        return Result.ok(tokenMap);
    }
}
