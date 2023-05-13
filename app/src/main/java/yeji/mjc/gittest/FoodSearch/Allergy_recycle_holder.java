package yeji.mjc.gittest.FoodSearch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class Allergy_recycle_holder extends RecyclerView.ViewHolder {

    ImageView food_img;
    TextView food_name;

    public Allergy_recycle_holder(@NonNull View itemView) {
        super(itemView);

        food_img = itemView.findViewById(R.id.food_img);
        food_name = itemView.findViewById(R.id.food_name);


    }
}
