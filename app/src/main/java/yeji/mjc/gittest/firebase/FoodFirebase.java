package yeji.mjc.gittest.firebase;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FoodFirebase {
    String food_name;
    int food_img;

    public FoodFirebase(String food_name, int food_img) {
        this.food_name = food_name;
        this.food_img = food_img;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_img() {
        return food_img;
    }

    public void setFood_img(int food_img) {
        this.food_img = food_img;
    }
}
