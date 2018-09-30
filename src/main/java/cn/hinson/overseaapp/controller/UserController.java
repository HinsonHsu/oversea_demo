package cn.hinson.overseaapp.controller;

import cn.hinson.overseaapp.dao.mapper.UserMapper;
import cn.hinson.overseaapp.enums.OauthType;
import cn.hinson.overseaapp.model.dto.User;
import cn.hinson.overseaapp.model.po.UserBase;
import cn.hinson.overseaapp.service.Oauth2Service;
import cn.hinson.overseaapp.service.UserService;
import java.security.Principal;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by xinshengshu on 2018/9/27.
 */
@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Oauth2Service oauth2Service;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @GetMapping(value = "/oauth")
    public ModelAndView loginWithOauth() {
        //这个重定向，会访问两次，初始就访问一次，手动输入不会
        return new ModelAndView(new RedirectView("https://www.facebook.com/dialog/oauth?client_id=549957782114948&redirect_uri=http://localhost:8080/code&response_type=code&state=nfeXN4&scope=publish_pages,manage_pages,publish_to_groups"));
    }

    @GetMapping(value = "/code")
    public String getCode(@RequestParam("code") String code) {
        /**
         * 浏览器输入
         * https://www.facebook.com/dialog/oauth?client_id=549957782114948&redirect_uri=http://localhost:8080/code&response_type=code&state=nfeXN4&scope=publish_pages,manage_pages,publish_to_groups
         */
        logger.info("time: " + System.currentTimeMillis());
        String accessToken = oauth2Service.getAccessToken(code);
        logger.info("access_token: " + accessToken);
        String pageId = "239040333455132";
        String res = oauth2Service.publishPage(accessToken, pageId);
        return res;
    }


    @GetMapping(value = "/user")
    public Principal getUser(Principal principal){

        return principal;
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam("email") String email, @RequestParam("password") String password) {
        UserBase userBase = userService.getUserByEmail(email);
        if(userBase != null) {
            return "email已注册";
        }else {
            userBase = new UserBase();
            userBase.setUsername(email + "_name");
            userBase.setHeadUrl("tmp_url");
            userBase.setCreateTime(new Timestamp(System.currentTimeMillis()));
            userBase.setUpdateTime( new Timestamp(System.currentTimeMillis()));
            userBase.setUserStatus(0);
            int uid = userService.createUserWithEmail(userBase, email, password);
            return "注册成功: " + userBase.getUsername();
        }
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
