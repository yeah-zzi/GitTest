package yeji.mjc.gittest;

public class FB_IMG_Item {
    String upload_img;
    String upload_time;

    FB_IMG_Item(){}

    public FB_IMG_Item(String upload_img, String upload_time) {
        this.upload_img = upload_img;
        this.upload_time = upload_time;
    }

    public String getUpload_img() {
        return upload_img;
    }

    public void setUpload_img(String upload_img) {
        this.upload_img = upload_img;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }
}
