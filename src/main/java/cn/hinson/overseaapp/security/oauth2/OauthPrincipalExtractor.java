package cn.hinson.overseaapp.security.oauth2;

import cn.hinson.overseaapp.enums.OauthType;
import cn.hinson.overseaapp.model.po.UserBase;
import cn.hinson.overseaapp.service.UserService;
import java.sql.Timestamp;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

/**
 * Created by xinshengshu on 2018/9/28.
 * 解析Gihub用户的id
 */

public class OauthPrincipalExtractor implements PrincipalExtractor {

    private OauthType oauthType;

    private UserService userService;

    protected final Log logger = LogFactory.getLog(this.getClass());

    public OauthPrincipalExtractor(OauthType oauthType, UserService userService) {
        this.oauthType = oauthType;
        this.userService = userService;
    }

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        String oauthId = map.get("id").toString();
        logger.info("extractPrincipal: " + oauthId);
        UserBase userBase = userService.getUserByOauthId(oauthType, oauthId);
        if(userBase == null) {
            userBase = new UserBase();
            userBase.setUsername(oauthType.toString() + oauthId);
            userBase.setHeadUrl("tmp_url");
            userBase.setCreateTime(new Timestamp(System.currentTimeMillis()));
            userBase.setUpdateTime( new Timestamp(System.currentTimeMillis()));
            logger.info(userBase.getAuditTime());
            userBase.setUserStatus(0);
            int uid = userService.createUserWithOauth(userBase, oauthType, oauthId);
            return uid;
        }
        return userBase.getUid();
    }
}
