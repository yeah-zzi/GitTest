package yeji.mjc.gittest.comunity;

public class Tip_comment_item {
    int comment_img;
    String comment_id;
    String comment;

    public Tip_comment_item(int comment_img, String comment_id, String comment) {
        this.comment_img = comment_img;
        this.comment_id = comment_id;
        this.comment = comment;
    }

    public int getComment_img() {
        return comment_img;
    }

    public void setComment_img(int comment_img) {
        this.comment_img = comment_img;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
