package yeji.mjc.gittest.frige;

public class Data_Item {

    private static int food_img;
    private static String food_name;
    private static String food_count;
    private static String food_date;


    public Data_Item(int food_img, String food_name, String food_count, String food_date) {
        this.food_img = food_img;
        this.food_name = food_name;
        this.food_count = food_count;
        this.food_date = food_date;
    }



    public static int getFood_img() {
        return food_img;
    }

    public void setFood_img(int food_img) {
        this.food_img = food_img;
    }


    public static String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }


    public static String getFood_count() {
        return food_count;
    }

    public void setFood_count(String food_count) {
        this.food_count = food_count;
    }


    public static String getFood_date() {
        return food_date;
    }

    public void setFood_date(String food_date) {
        this.food_date = food_date;
    }
}
