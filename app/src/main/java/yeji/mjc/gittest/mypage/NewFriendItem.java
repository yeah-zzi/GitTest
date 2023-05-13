package yeji.mjc.gittest.mypage;

public class NewFriendItem {
    int friend_img;
    String friend_id;
    String battle_count;

    public NewFriendItem(int friend_img, String friend_id, String battle_count) {
        this.friend_img = friend_img;
        this.friend_id = friend_id;
        this.battle_count = battle_count;
    }

    public int getFriend_img() {
        return friend_img;
    }

    public void setFriend_img(int friend_img) {
        this.friend_img = friend_img;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getBattle_count() {
        return battle_count;
    }

    public void setBattle_count(String battle_count) {
        this.battle_count = battle_count;
    }
}
