package yeji.mjc.gittest.cart;

public class FoodItem {

    String food_name;
    String food_count;
    String food_img;
    boolean checkBox;

    public FoodItem(String food_name, String food_count, String food_img, boolean checkBox) {
        this.food_name = food_name;
        this.food_count = food_count;
        this.food_img = food_img;
        this.checkBox = checkBox;
    }

    public String getName() {
        return food_name;
    }

    public void setName(String name) {
        this.food_name = food_name;
    }


    public String getNum() {
        return food_count;
    }

    public void setNum(String num) {
        this.food_count = food_count;
    }


    public String getImg() {
        return food_img;
    }

    public void setImg(String img) {
        this.food_img= food_img;
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
    
