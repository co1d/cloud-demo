package com.zplus.cloudauth.controller;

import com.zplus.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
// @RequestMapping("/oauth")
public class OauthController
{
    @Autowired
    private TokenEndpoint tokenEndpoint;

    // @GetMapping("/token")
    public Result<?> getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException
    {
        return custom(tokenEndpoint.getAccessToken(principal, parameters).getBody());
    }

    // @PostMapping("/token")
    public Result<?> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException
    {
        return custom(tokenEndpoint.postAccessToken(principal, parameters).getBody());
    }

    /**
     * 自定义返回格式
     *
     * @param accessToken
     * @return
     */
    private Result<?> custom(OAuth2AccessToken accessToken)
    {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        Map<String, Object> data = new LinkedHashMap<>(token.getAdditionalInformation());
        data.put("accessToken", token.getValue());
        if (token.getRefreshToken() != null)
        {
            data.put("refreshToken", token.getRefreshToken().getValue());
        }
        data.put("expiresIn", token.getExpiresIn());
        data.put("tokenType", token.getTokenType());

        return Result.ok(data);
    }

    @ExceptionHandler({InvalidGrantException.class})
    public Result<?> grantExceptionHandler()
    {
        return Result.error("grant error");
    }

    @ExceptionHandler({InvalidScopeException.class, UnsupportedGrantTypeException.class})
    public Result<?> validExceptionHandler()
    {
        return Result.error("valid error");
    }
}
