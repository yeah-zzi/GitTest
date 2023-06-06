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
import yeji.mjc.gittest.comunity.Tip_comment_recycle_holder;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private ArrayList<CartItem> cartItems;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference CFDB;
    String user = "2810839655";

    public CartAdapter(ArrayList<CartItem> items){
        this.cartItems = items;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { Context context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frige_plus_recycler,parent,false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
       Glide.with(holder.itemView).load(cartItems.get(position).getFood_img()).into(holder.food_img);
        holder.food_name.setText(cartItems.get(position).getFood_name());
        holder.food_count.setText(cartItems.get(position).getFood_count());
    }

    private void finish() {
        return;
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

//    public void addCart(String name, String num, int Img, String date) {
//        // 파이어베이스에 들어갈 이름, 갯수, 이미지, 날짜 넣는 코드
//        CFDB = database.getReference().child(user);
//    }
}
