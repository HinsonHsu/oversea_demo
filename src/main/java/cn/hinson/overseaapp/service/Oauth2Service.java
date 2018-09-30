package cn.hinson.overseaapp.service;

/**
 * Created by xinshengshu on 2018/9/29.
 */
public interface Oauth2Service {

    String getAccessToken(String code);

    String publishPage(String accessToken, String pageId);
}
