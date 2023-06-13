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
import yeji.mjc.gittest.UserData;

public class FoodAdapter extends RecyclerView.Adapter<Food_recycle_holder> {

    ArrayList<FoodItem> items;


    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference cartdb, cartdeleteditems;
    String userid ;
    int count =1;


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
        //로그인 시 아이디값 변수 받아오기
        userid = UserData.getInstance().getUserid();

        holder.foodName.setText(items.get(position).getName());
        holder.foodNum.setText(items.get(position).getNum());
        holder.checkBox.setChecked(items.get(position).isCheckBox());
        Glide.with(holder.itemView).load(items.get(position).getImg()).into(holder.foodImage);

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                int count = Integer.parseInt(items.get(position).getNum());
                if(count>1) {
                    count--;
                    items.get(position).setNum(String.valueOf(count));
                    holder.foodNum.setText(String.valueOf(count));

                    // 파이어베이스에서 해당 아이템의 값을 업데이트
                    String itemName = items.get(holder.getAdapterPosition()).getName();
                    int itemIndex = holder.getAdapterPosition(); // 아이템의 인덱스 확인
                    if (itemIndex >= 0 && itemIndex < items.size()) {
                        DatabaseReference cartdb = database.getReference().child("user").child(userid).child("cart");
                        cartdb.child(itemName).child("food_count").setValue(String.valueOf(count));
                    }
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                int count = Integer.parseInt(items.get(position).getNum());
                count++;
                items.get(position).setNum(String.valueOf(count));
                holder.foodNum.setText(String.valueOf(count));

                // 파이어베이스에서 해당 아이템의 값을 업데이트
                String itemName = items.get(holder.getAdapterPosition()).getName();
                int itemIndex = holder.getAdapterPosition(); // 아이템의 인덱스 확인
                if (itemIndex >= 0 && itemIndex < items.size()) {
                    DatabaseReference cartdb = database.getReference().child("user").child(userid).child("cart");
                    cartdb.child(itemName).child("food_count").setValue(String.valueOf(count));
                }
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