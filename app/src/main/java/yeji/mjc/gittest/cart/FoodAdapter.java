package yeji.mjc.gittest.cart;

import android.util.Log;
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

import yeji.mjc.gittest.R;

public class FoodAdapter extends RecyclerView.Adapter<Food_recycle_holder> {

    ArrayList<FoodItem> items;
    private int count =1;

    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference cartdb, cartdeleteditems;
    String userid = "2810839655";

    public FoodAdapter(ArrayList<FoodItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Food_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recyclerview,parent,false);
        return new Food_recycle_holder(v); //오징어 이미지 수정
    }

    @Override
    public void onBindViewHolder(@NonNull Food_recycle_holder holder, int position) {
        holder.foodName.setText(items.get(position).getName());
        holder.foodNum.setText(items.get(position).getNum());
        holder.checkBox.setChecked(items.get(position).isCheckBox());
        Glide.with(holder.itemView).load(items.get(position).getImg()).into(holder.foodImage);

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>1) {
                count--;
                holder.foodNum.setText(count); }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                holder.foodNum.setText(count);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // 전체 선택
    public void selectAll() {
        for (FoodItem item : items) {
            item.setCheckBox(true);
        }
        notifyDataSetChanged(); // 변경 사항을 어댑터에 알림
    }

    // 전체 해제
    public void deselectAll() {
        for (FoodItem item : items) {
            item.setCheckBox(false);
        }
        notifyDataSetChanged();
    }

    public void addItem(int position, FoodItem item) {
        items.add(position, item);
    }
    public void removeItem(int positon) {
        items.remove(positon);
    }

    public void  removeAllItem(){
        items.clear();
    }

}