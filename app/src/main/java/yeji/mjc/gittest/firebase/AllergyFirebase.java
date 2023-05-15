package yeji.mjc.gittest.firebase;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class AllergyFirebase {
    String allergy_name;
    int allergy_img;

    public AllergyFirebase(String allergy_name, int allergy_img) {
        this.allergy_name = allergy_name;
        this.allergy_img = allergy_img;
    }

    public String getAllergy_name() {
        return allergy_name;
    }

    public void setAllergy_name(String allergy_name) {
        this.allergy_name = allergy_name;
    }

    public int getAllergy_img() {
        return allergy_img;
    }

    public void setAllergy_img(int allergy_img) {
        this.allergy_img = allergy_img;
    }
}
