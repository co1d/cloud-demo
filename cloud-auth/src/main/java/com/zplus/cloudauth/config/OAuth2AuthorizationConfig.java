package com.zplus.cloudauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    private DataSource dataSource;
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public TokenStore tokenStore()
    {
        //存在mysql中
        // return new JdbcTokenStore(dataSource);
        //存在内存中
        //return new InMemoryTokenStore();
        //存在redis中
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        redisTokenStore.setPrefix("token:");
        // redisTokenStore.setSerializationStrategy(new JacksonRedisTokenStoreSerializationStrategy());
        return redisTokenStore;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public ClientDetailsService jdbcClientDetailsService()
    {
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
    {
        security
                //允许表单认证
                .allowFormAuthenticationForClients()
                //对获取token的请求不再拦截
                // .tokenKeyAccess("permitAll()")
                .tokenKeyAccess("isAuthenticated()")
                //验证获取token的验证信息
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        //将客户端的信息
        clients.withClientDetails(jdbcClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
    {
        //配置token的存储方式
        /*endpoints
                //读取用户的认证信息
                .userDetailsService(userDetailsService)
                //注入WebSecurityConfig配置的bean
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore());*/
        endpoints
                //读取用户的认证信息
                .userDetailsService(userDetailsService)
                //注入WebSecurityConfig配置的bean
                .authenticationManager(authenticationManager);
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        // tokenServices.setReuseRefreshToken(true);
        // tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        // tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds(7200);//token有效期
        tokenServices.setRefreshTokenValiditySeconds(604800);//Refresh_token有效期
        endpoints.tokenServices(tokenServices);
    }

}
