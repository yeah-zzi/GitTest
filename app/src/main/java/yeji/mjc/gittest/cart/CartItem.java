package yeji.mjc.gittest.cart;

import java.util.ArrayList;
import java.util.Date;

public class CartItem {

    String food_name;
    String food_count;
    String food_img;

    public CartItem() {

    }

    public CartItem(String food_name, String food_count, String food_img) {
        this.food_name = food_name;
        this.food_count = food_count;
        this.food_img = food_img;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_count() {
        return food_count;
    }

    public void setFood_count(String food_count) {
        this.food_count = food_count;
    }

    public String getFood_img() {
        return food_img;
    }

    public void setFood_img(String food_img) {
        this.food_img = food_img;
    }

}
