package yeji.mjc.gittest.comunity;

public class TipItem {

    String id;
    String like;
    String comment;
    String content;
    int tip_img;

    public TipItem(String id, String like, String comment, String content, int tip_img) {
        this.id = id;
        this.like = like;
        this.comment = comment;
        this.content = content;
        this.tip_img = tip_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTip_img() {
        return tip_img;
    }

    public void setTip_img(int tip_img) {
        this.tip_img = tip_img;
    }
}
