package yeji.mjc.gittest.cart;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import yeji.mjc.gittest.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public ImageView food_img;
    public EditText food_name, food_count, food_dead;
    public ImageButton cancel, complete, calendar;
    TextView deadLine;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        food_img = itemView.findViewById(R.id.food_img);
        food_name = itemView.findViewById(R.id.search_food_name);
        food_count = itemView.findViewById(R.id.search_food_count);
        cancel = itemView.findViewById(R.id.cancel);
        complete = itemView.findViewById(R.id.complete);
        calendar = itemView.findViewById(R.id.calendar);
        deadLine = itemView.findViewById(R.id.search_food_dead);
    }
}
