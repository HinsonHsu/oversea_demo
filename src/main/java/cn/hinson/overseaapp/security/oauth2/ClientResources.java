package cn.hinson.overseaapp.security.oauth2;

import cn.hinson.overseaapp.enums.OauthType;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * Created by xinshengshu on 2018/9/28.
 * 配置客户端的认证服务器信息和资源服务器信息
 */
public class ClientResources {

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    private OauthType Oauth2Type;

    public ClientResources(OauthType oauthType){
        this.Oauth2Type = oauthType;
    }

    public OauthType getOauth2Type(){
        return this.Oauth2Type;
    }

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}