package cn.hinson.overseaapp.service;

import cn.hinson.overseaapp.enums.OauthType;
import cn.hinson.overseaapp.model.po.LocalAccount;
import cn.hinson.overseaapp.model.po.UserBase;

/**
 * Created by xinshengshu on 2018/9/27.
 */
public interface UserService {
    int createUser(UserBase userBase);
    int createUserWithEmail(UserBase userBase, String email, String password);
    int createUserWithOauth(UserBase userBase, OauthType oauthType, String oauthId);
    UserBase getUserByOauthId(OauthType oauthType, String oauthId);
    UserBase getUserByEmail(String email);
    LocalAccount getLocalAccount(String email);
}
