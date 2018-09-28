package cn.hinson.overseaapp.controller;

import cn.hinson.overseaapp.dao.mapper.UserMapper;
import cn.hinson.overseaapp.enums.OauthType;
import cn.hinson.overseaapp.model.po.UserBase;
import cn.hinson.overseaapp.service.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xinshengshu on 2018/9/27.
 */
@RestController
@RequestMapping(value = "/")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping(value = "/user")
    public Principal getUser(Principal principal){

        return principal;
    }

    @GetMapping(value = "/test")
    public int test() {
//        UserBase userBase = new UserBase();
//        userBase.setUsername("hinsonhsu");
//        userBase.setHeadUrl("tmp");
//        int uid = userMapper.createUserBaseAndGetUid(userBase);
//        LocalAccount localAccount = new LocalAccount();
//        localAccount.setUid(uid);
//        localAccount.setEmail("619179450@qq.com");
//        localAccount.setPassword(new BCryptPasswordEncoder().encode("password"));

//        userService.createUser(userBase);
        OauthType oauthType = OauthType.GITHUB;
        String oauthId = "18041867";
        UserBase userBase = userService.getUserByOauthId(oauthType, oauthId);
        return userBase.getUid();
    }

    @GetMapping(value = "/test1")
    public String test1() {

        return "success";
    }
}
