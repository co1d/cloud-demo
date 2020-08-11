package com.zplus.cloudauth.granter;

/*abstract class AbstractCustomTokenGranter extends AbstractTokenGranter
{
    private final OAuth2RequestFactory requestFactory;

    protected AbstractCustomTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType)
    {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.requestFactory = requestFactory;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest)
    {
        Map<String, String> parameters = tokenRequest.getRequestParameters();
        UserVO user = getUser(parameters);
        if (user == null)
        {
            throw new InvalidGrantException("无法获取用户信息");
        }
        OAuth2Request storedOAuth2Request = this.requestFactory.createOAuth2Request(client, tokenRequest);
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(user);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(storedOAuth2Request, authentication);
        return oAuth2Authentication;
    }

    protected abstract UserDO getUser(Map<String, String> parameters);
}*/
