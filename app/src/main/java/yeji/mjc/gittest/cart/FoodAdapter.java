package yeji.mjc.gittest.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class FoodAdapter extends RecyclerView.Adapter<Food_recycle_holder> {

    ArrayList<FoodItem> items;

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
        holder.foodImage.setImageResource(items.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}