package yeji.mjc.gittest.cart;

public class FoodItem {

    String name;
    String num;
    String img;
    boolean checkBox;

    public FoodItem(String name, String num, String img, boolean checkBox) {
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


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public boolean isCheckBox() {
        return checkBox;
    }

    public boolean isCheckBox(boolean checkBox) {
        return this.checkBox;

    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }
}
    
