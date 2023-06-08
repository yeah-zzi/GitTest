package yeji.mjc.gittest.cart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

import yeji.mjc.gittest.FoodSearch.Food_expiry;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.comunity.FB_IMG_Holder;
import yeji.mjc.gittest.comunity.TipItem;
import yeji.mjc.gittest.comunity.Tip_comment_item;
import yeji.mjc.gittest.comunity.Tip_comment_recycle_holder;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference cartDB, frigeDB;
    ArrayList<CartItem> items;
    String userid = "2810839655";


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
        holder.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 캘린더 화면 나오고 날짜 설정 가능 , 혹은 날짜 직접 입력 가능
                holder.food_dead.setText(toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
