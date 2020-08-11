package com.zplus.cloudauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// @Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /*http
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and()
                .requestMatchers().antMatchers("/user/hello","/user/registry")
                .and()
                .authorizeRequests().anyRequest().authenticated();*/
        http    // 配置登陆页/login并允许访问
                .formLogin()
                // .loginPage("/login")
                .permitAll()
                // 登出页
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                // 其余所有请求全部需要鉴权认证
                .and().authorizeRequests().anyRequest().authenticated()
                .and().cors()
                .and().csrf().disable();
    }

    //添加要忽略校验拦截的静态资源的请求
    // @Override
    // public void configure(WebSecurity web) throws Exception
    // {
    //     web.ignoring().antMatchers("/user/hello");
    // }
}
