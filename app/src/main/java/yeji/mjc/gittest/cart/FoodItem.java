package yeji.mjc.gittest.cart;

public class FoodItem {

    String name;
    String num;
    int img;
    boolean checkBox;

    public FoodItem(String name, String num, int img, boolean checkBox) {
        this.name = name;
        this.num = num;
        this.img = img;
        this.checkBox = checkBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }
}