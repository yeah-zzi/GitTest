package yeji.mjc.gittest.frige;

public class Fridge_Item {
    int food_img;
    String food_name;
    String food_count;
    String food_date;


    public Fridge_Item(int food_img, String food_name, String food_count, String food_date) {
        this.food_img = food_img;
        this.food_name = food_name;
        this.food_count = food_count;
        this.food_date = food_date;
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


    public String getFood_count() {
        return food_count;
    }

    public void setFood_count(String food_count) {
        this.food_count = food_count;
    }


    public String getFood_date() {
        return food_date;
    }

    public void setFood_date(String food_date) {
        this.food_date = food_date;
    }
}
