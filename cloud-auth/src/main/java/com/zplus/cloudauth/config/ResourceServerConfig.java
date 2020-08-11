package com.zplus.cloudauth.config;

import com.zplus.commonutils.ResponseEnum;
import com.zplus.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();
        //当权限不足时返回
        resources.accessDeniedHandler((request,response,e)->{
            log.error("[accessDeniedHandler]{}",e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            //统一认证失败返回的异常
            response.getWriter().write(objectMapper.writeValueAsString(Result.error(ResponseEnum.AUTH_ERROR)));
        });
        //当token不正确时返回
        resources.authenticationEntryPoint((request, response, e)->{
            log.error("[authenticationEntryPoint]{}",e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            //统一认证失败返回的异常
            response.getWriter().write(objectMapper.writeValueAsString(Result.error(ResponseEnum.TOKEN_AUTH_ERROR)));
        });

    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        //配置哪些请求需要验证
        /*http
                //放行接口 start
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                //放行 end
                //认证 start
                .anyRequest().authenticated();*/
        http
//                antMatcher表示只能处理/user的请求
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
                .antMatchers("/user/test1").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/refresh").permitAll()
                .antMatchers("/user/test2").authenticated().and().csrf().disable();
    }
}
