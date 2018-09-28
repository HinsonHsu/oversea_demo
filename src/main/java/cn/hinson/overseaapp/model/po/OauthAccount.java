package cn.hinson.overseaapp.model.po;

/**
 * Created by xinshengshu on 2018/9/27.
 * 用于twitter、facebook登录用户，与UserBase对应
 */
public class OauthAccount {
    private int id;

    private int uid;

    private int oauthSource;

    private String oauthId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getOauthSource() {
        return oauthSource;
    }

    public void setOauthSource(int oauthSource) {
        this.oauthSource = oauthSource;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }
}
