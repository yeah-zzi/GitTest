package yeji.mjc.gittest.cart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.zip.Inflater;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.comunity.FB_IMG_Holder;
import yeji.mjc.gittest.comunity.TipItem;
import yeji.mjc.gittest.comunity.Tip_comment_item;
import yeji.mjc.gittest.comunity.Tip_comment_recycle_holder;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    //Context context;
    ArrayList<CartItem> items;

    public CartAdapter(ArrayList<CartItem> items) {
        this.items = items;
    }

    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frige_item_list,parent,false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Glide.with(holder.itemView).load(items.get(position).getFood_img()).into(holder.food_img);
        holder.food_name.setText(items.get(position).getFood_name());
        holder.food_count.setText(items.get(position).getFood_count());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
