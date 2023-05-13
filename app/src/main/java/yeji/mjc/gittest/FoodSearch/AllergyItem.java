package yeji.mjc.gittest.FoodSearch;

public class AllergyItem {
    int food_img;
    String food_name;

    public AllergyItem(int food_img, String food_name) {
        this.food_img = food_img;
        this.food_name = food_name;
    }

    public int getFood_img() {
        return food_img;
    }

    public void setFood_img(int food_img) {
        this.food_img = food_img;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }
}
