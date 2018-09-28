package cn.hinson.overseaapp.service.imp;

import cn.hinson.overseaapp.dao.mapper.UserMapper;
import cn.hinson.overseaapp.enums.OauthType;
import cn.hinson.overseaapp.model.po.LocalAccount;
import cn.hinson.overseaapp.model.po.OauthAccount;
import cn.hinson.overseaapp.model.po.UserBase;
import cn.hinson.overseaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by xinshengshu on 2018/9/27.
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int createUser(UserBase userBase) {
        userMapper.createUserBaseAndGetUid(userBase);
        return userBase.getUid();
    }

    @Override
    public int createUserWithEmail(UserBase userBase, String email, String password) {
        int uid = createUser(userBase);
        LocalAccount localAccount = new LocalAccount();
        localAccount.setUid(uid);
        localAccount.setEmail(email);
        localAccount.setPassword(new BCryptPasswordEncoder().encode(password));
        userMapper.createLocalAccount(localAccount);
        return uid;
    }

    @Override
    public int createUserWithOauth(UserBase userBase, OauthType oauthType, String oauthId) {
        int uid = createUser(userBase);
        OauthAccount oauthAccount = new OauthAccount();
        oauthAccount.setUid(uid);
        oauthAccount.setOauthSource(oauthType.ordinal());
        oauthAccount.setOauthId(oauthId);
        userMapper.createOauthAccount(oauthAccount);
        return uid;
    }

    @Override
    public UserBase getUserByOauthId(OauthType oauthType, String oauthId) {
        return userMapper.getUserBaseByOauth(oauthType.ordinal(), oauthId);
    }

    @Override
    public UserBase getUserByEmail(String email) {
        LocalAccount localAccount = userMapper.getLocalAccountByEmail(email);
        if(localAccount == null)
            return null;
        int uid = localAccount.getUid();
        return userMapper.getUserBaseByUid(uid);
    }

    @Override
    public LocalAccount getLocalAccount(String email) {
        return userMapper.getLocalAccountByEmail(email);
    }
}
