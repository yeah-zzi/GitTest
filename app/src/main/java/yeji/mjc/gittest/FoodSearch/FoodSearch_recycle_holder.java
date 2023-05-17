package yeji.mjc.gittest.FoodSearch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class FoodSearch_recycle_holder extends RecyclerView.ViewHolder {

    //리사이클러뷰에 사용된 각 뷰의 id를 연결해주기 위한 홀더이다
    ImageView food_img;
    TextView food_name;
    CardView cardView;

    public FoodSearch_recycle_holder(@NonNull View itemView) {
        super(itemView);

        food_img = itemView.findViewById(R.id.food_img);
        food_name = itemView.findViewById(R.id.food_name);
        cardView = itemView.findViewById(R.id.cv_item_movie_parent);
    }
}
