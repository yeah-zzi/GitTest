package yeji.mjc.gittest.comunity;

public class TipItem {

    String com_code;
    String comment_count;
    String content;
    String like;
    String post_img;
    String title;
    String writer;
    String writer_img;

    public TipItem(){}

    public TipItem(String com_code, String comment_count, String content, String like, String post_img, String title, String writer, String writer_img) {
        this.com_code = com_code;
        this.comment_count = comment_count;
        this.content = content;
        this.like = like;
        this.post_img = post_img;
        this.title = title;
        this.writer = writer;
        this.writer_img = writer_img;
    }

    public String getCom_code() {
        return com_code;
    }

    public void setCom_code(String com_code) {
        this.com_code = com_code;
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

    public String getWriter_img() {
        return writer_img;
    }

    public void setWriter_img(String writer_img) {
        this.writer_img = writer_img;
    }
}
