package yeji.mjc.gittest.firebase;

public class UserFirebase {
    public String userId;
    public String user_name;
    public String user_email;
    public String user_img;
    public String user_nickname;

    public UserFirebase() {
        // Default constructor required for calls to DataSnapshot.getValue(UserFirebase.class)
    }

    public UserFirebase(String userId,String userName, String userEmail, String userImgUrl) {
        this.userId = userId;
        this.user_name = userName;
        this.user_email = userEmail;
        this.user_img = userImgUrl;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String userName) {
        this.user_name = userName;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    @Override
    public String toString() {
        return "UserFirebase{" +
                "user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_img='" + user_img + '\'' +
                ", user_nickname='" + user_nickname + '\'' +
                '}';
    }
}
