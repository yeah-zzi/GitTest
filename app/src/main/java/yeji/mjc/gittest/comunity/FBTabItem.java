package yeji.mjc.gittest.comunity;

public class FBTabItem {
    String code;
    String fb_friend;
    String fb_friend_img;

    public FBTabItem(){}

    public FBTabItem(String code, String fb_friend, String fb_friend_img) {
        this.code = code;
        this.fb_friend = fb_friend;
        this.fb_friend_img = fb_friend_img;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFb_friend() {
        return fb_friend;
    }

    public void setFb_friend(String fb_friend) {
        this.fb_friend = fb_friend;
    }

    public String getFb_friend_img() {
        return fb_friend_img;
    }

    public void setFb_friend_img(String fb_friend_img) {
        this.fb_friend_img = fb_friend_img;
    }
}
