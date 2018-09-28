package cn.hinson.overseaapp.model.po;

/**
 * Created by xinshengshu on 2018/9/27.
 * 用户登录账户密码，与UserBase对应
 */
public class LocalAccount {
    private int id;

    private int uid;

    private String email;

    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
