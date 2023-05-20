package yeji.mjc.gittest.comunity;

public class FriendAdd_Item {
    String user_name;
    String user_img;

    public FriendAdd_Item(){}

    public FriendAdd_Item(String user_name, String user_img) {
        this.user_name = user_name;
        this.user_img = user_img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }
}
