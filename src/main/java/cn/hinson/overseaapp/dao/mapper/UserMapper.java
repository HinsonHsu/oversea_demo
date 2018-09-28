package cn.hinson.overseaapp.dao.mapper;

import cn.hinson.overseaapp.model.po.LocalAccount;
import cn.hinson.overseaapp.model.po.OauthAccount;
import cn.hinson.overseaapp.model.po.UserBase;
import org.apache.ibatis.annotations.Param;

/**
 * Created by xinshengshu on 2018/9/27.
 */

public interface UserMapper {
    int createUserBaseAndGetUid(UserBase userBase);
    void createLocalAccount(LocalAccount localAccount);
    void createOauthAccount(OauthAccount oauthAccount);
    LocalAccount getLocalAccountByEmail(String email);
    UserBase getUserBaseByUid(int uid);
    UserBase getUserBaseByOauth(@Param("oauthType") int oauthType, @Param("oauthId") String oauthId);
}
