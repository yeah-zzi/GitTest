package yeji.mjc.gittest;

//이미지주소를 담을 그릇이다
public class Model {
    private String imageUrl;
    Model(){}

    public Model(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
