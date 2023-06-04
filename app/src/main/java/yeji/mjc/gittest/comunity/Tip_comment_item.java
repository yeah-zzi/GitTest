package yeji.mjc.gittest.comunity;

public class Tip_comment_item {
    String comment_writer_id;
    String comment_writer_img;
    String comment_content;
    String comment_code;

    Tip_comment_item(){}

    public Tip_comment_item(String comment_writer_id, String comment_writer_img, String comment_content, String comment_code) {
        this.comment_writer_id = comment_writer_id;
        this.comment_writer_img = comment_writer_img;
        this.comment_content = comment_content;
        this.comment_code = comment_code;
    }

    public String getComment_writer_id() {
        return comment_writer_id;
    }

    public void setComment_writer_id(String comment_writer_id) {
        this.comment_writer_id = comment_writer_id;
    }

    public String getComment_writer_img() {
        return comment_writer_img;
    }

    public void setComment_writer_img(String comment_writer_img) {
        this.comment_writer_img = comment_writer_img;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_code() {
        return comment_code;
    }

    public void setComment_code(String comment_code) {
        this.comment_code = comment_code;
    }
}
