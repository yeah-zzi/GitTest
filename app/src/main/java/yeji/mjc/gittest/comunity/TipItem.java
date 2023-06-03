package yeji.mjc.gittest.comunity;

public class TipItem {

    String comment_count;
    String content;
    String like;
    String post_img;
    String title;
    String writer;

    public TipItem(){}

    public TipItem(String comment_count, String content, String like, String post_img, String title, String writer) {
        this.comment_count = comment_count;
        this.content = content;
        this.like = like;
        this.post_img = post_img;
        this.title = title;
        this.writer = writer;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
