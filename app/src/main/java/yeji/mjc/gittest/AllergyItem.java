package yeji.mjc.gittest;

public class AllergyItem {
    int allergy_img;
    String allergy_name;

    public AllergyItem(){}

    public AllergyItem(int allergy_img, String allergy_name) {
        this.allergy_img = allergy_img;
        this.allergy_name = allergy_name;
    }

    public int getAllergy_img() {
        return allergy_img;
    }

    public void setAllergy_img(int allergy_img) {
        this.allergy_img = allergy_img;
    }

    public String getAllergy_name() {
        return allergy_name;
    }

    public void setAllergy_name(String allergy_name) {
        this.allergy_name = allergy_name;
    }
}
