package yeji.mjc.gittest.comunity;

public class HeartItem {

    String bool,user;

    public HeartItem() {
    }

    public HeartItem(String bool, String user) {
        this.bool = bool;
        this.user = user;
    }

    public String getBool() {
        return bool;
    }

    public void setBool(String bool) {
        this.bool = bool;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
