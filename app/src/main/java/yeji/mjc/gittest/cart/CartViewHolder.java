package yeji.mjc.gittest.cart;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public ImageView food_img;
    public EditText food_name, food_count, food_dead;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        food_img = itemView.findViewById(R.id.food_img);
        food_name = itemView.findViewById(R.id.search_food_name);
        food_count = itemView.findViewById(R.id.search_food_count);
        food_dead = itemView.findViewById(R.id.search_food_dead);
    }
}
