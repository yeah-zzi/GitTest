package yeji.mjc.gittest.frige;

public class Fridge_Item {
    int food_img;
    String food_name;
    String food_count;
    String food_date;
    String fridge_type;
    int progress;
    String dDay;

    public Fridge_Item() {
    }

    public Fridge_Item(int food_img, String food_name, String food_count, String food_date, String fridge_type, int progress) {
        this.food_img = food_img;
        this.food_name = food_name;
        this.food_count = food_count;
        this.food_date = food_date;
        this.fridge_type = fridge_type;
        this.progress = progress;
    }



    public String getFridge_type() {
        return fridge_type;
    }

    public void setFridge_type(String fridge_type) {
        this.fridge_type = fridge_type;
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


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getDDay() {
        return dDay;
    }

    public void setDDay(String dDay) {
        this.dDay = dDay;
    }






}
