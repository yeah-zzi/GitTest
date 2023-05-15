package yeji.mjc.gittest.FoodSearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class FoodSearchAdapter extends RecyclerView.Adapter<FoodSearch_recycle_holder> {

    private Context context;
    ArrayList<FoodSearchItem> items;
    private SelectListener listener;


    public FoodSearchAdapter(Context context, ArrayList<FoodSearchItem> items,SelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public FoodSearch_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_img_search_recyclerview,parent,false);
        return new FoodSearch_recycle_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodSearch_recycle_holder holder, @SuppressLint("RecyclerView") int position) {
        holder.food_img.setImageResource(items.get(position).getFood_img());
        holder.food_name.setText(items.get(position).getFood_name());

        //이벤트 추가
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
