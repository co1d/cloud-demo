package com.zplus.cloudauth.granter;

/*public class PasswordTokenGranter extends AbstractCustomTokenGranter
{
    protected UserDetailsService userDetailsService;

    public PasswordTokenGranter(UserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, "password");
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected UserDO getUser(Map<String, String> parameters) {
        String username = parameters.get("username");
        return userDetailsService.loadUserByUsername(username);
    }
}*/
