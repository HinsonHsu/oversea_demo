package cn.hinson.overseaapp.service.imp;

import cn.hinson.overseaapp.service.Oauth2Service;
import cn.hinson.overseaapp.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Created by xinshengshu on 2018/9/29.
 */
@Service
public class Oauth2ServiceImp implements Oauth2Service {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Override
    public String getAccessToken(String code) {

//        code = "AQBvlN-U_bPcLl1NHdoR---k-dYBGfCJy7XTCMjfbIg06rKLKSm121Z0ODQ95R4HA4pfk53UkkV0j4r0RwoNr2YPaWFhUyy4tE5hoZLIgHWly81TXYCQC7ejNZo0pTyNKLxEy5S7V2Vyw3vpOhwFxtgkfZ4e07S67oDPEUSCvhkrAEKe8ESXI8L5nJSySZSBgmC1bjY0_E1rAc3OWOIN1wFO-jY4zixqhljGlxEWi3ik43tZR3FBxU6t-SQBai9SL0xtwRj3hlWQaCtrBAv9qfZBfBScZiTq5gLYyr8tL4pr86prfOGi6wQ1s5IGyQJnQhow8z5w1NNYxFW9tKE9EPvE&state=nfeXN4#_=_";
//        code = "AQDY0bV0EkYmxgGfchRGqGDwpD6TSKZoOhBqQjeIH6Cq2PqyPFx8RuSGZCsjT4sf6eN8Do29HHdepBQR7I3KNlgqDUi65d_Nm_54CefP2G3sauozvUIgqnnKPz4TEM5bF7uEYfQ5DY3DR-0OjBFUAzeNVVn9l140ovyne7fDqRFVuDG0Lwle6rQcODG6lUVdeuk6cbzuy5MLSv8pE8i8YMwFUxhCoJaPjIgF_ZK9R9Iss49TC4RbiW-_3XaYy_MW-ZaJD2IDMfoSPTT4gYeji9If-jo7Gd4abkbgwSGq5PZjHZJ6O2YqKdKpmqxlPxhqY2VTy4UI7PDZgd8NnxLhRiMX&state=nfeXN4#_=_";
        String appId = "549957782114948";
        String clientSecret = "081a70850ac47341c3fd7bc466d5b827";
        String redirectUri = "http://localhost:8080/code";
        String accessTokenUrl = "https://graph.facebook.com/v3.1/oauth/access_token?"
            + "client_id=" + appId
            + "&redirect_uri=" + redirectUri
            + "&client_secret=" + clientSecret
            + "&code=" + code;
        String res =  HttpUtil.get(accessTokenUrl);
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.getString("access_token");
    }

    @Override
    public String publishPage(String accessToken, String pageId) {
        //获取主页的accessToken
        String getPageAccessToken = "https://graph.facebook.com/v3.1/me/accounts";
        JSONObject json = HttpUtil.getJsonObject(getPageAccessToken + "?access_token="+accessToken);
        logger.info("json: " + json);
        JSONArray jsonArray = (JSONArray)json.get("data");
        JSONObject page1 = null;
        for(int i=0; i < jsonArray.size(); i++) {
            JSONObject tmp = jsonArray.getJSONObject(i);
            if (tmp.getString("id").equals(pageId))
                page1 = tmp;
        }
        if(page1 == null){
            logger.error("pageId： " + pageId + "不存在");
            return "pageId： " + pageId + "不存在";
        }

        String pageAccessToken = page1.getString("access_token");
        String pargeId = page1.getString("id");


        //调用以主页身份发帖api
        String message = "This is a message from focus" + System.currentTimeMillis();
        logger.info("message: " + message);
        Map<String, String> pageParams = new HashMap<>();
        String publishPageUrl = "https://graph.facebook.com/v3.1/" + pargeId + "/feed";
        pageParams.put("message", message);
        pageParams.put("access_token", pageAccessToken);
        String res  = HttpUtil.post(publishPageUrl, pageParams);
        logger.info(res);
        return res;
    }

}
