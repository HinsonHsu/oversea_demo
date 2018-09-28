package cn.hinson.overseaapp.security.service;

import cn.hinson.overseaapp.model.po.LocalAccount;
import cn.hinson.overseaapp.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by xinshengshu on 2018/9/27.
 */
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("loadUserByUsername: " + email);
        LocalAccount localAccount = userService.getLocalAccount(email);
        if(localAccount == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        logger.info("loadUserByUsername: " + localAccount.getPassword());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities。
        authorities.add(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new User(email, localAccount.getPassword(), authorities);
        return userDetails;
    }
}
