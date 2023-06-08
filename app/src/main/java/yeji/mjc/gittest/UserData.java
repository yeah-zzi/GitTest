package yeji.mjc.gittest;

import android.app.Application;

public class UserData{
    private String userid;
    private String username;
    private String userimg;



    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    private  static UserData instance = null;

    public static synchronized UserData getInstance(){
        if (null  == instance){
            instance = new UserData();
            UserData.getInstance().getUserid();
            UserData.getInstance().getUserimg();
            UserData.getInstance().getUsername();
        }
        return instance;
    }
}
