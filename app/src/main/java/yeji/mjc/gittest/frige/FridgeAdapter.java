package yeji.mjc.gittest.frige;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.MainActivity;
import yeji.mjc.gittest.R;

public class FridgeAdapter extends RecyclerView.Adapter<Fridge_recycle_holder> {

    //Context context;
    ArrayList<Item> items;
    int count=0;

    public FridgeAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    public Fridge_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fridge_recyclerview,parent,false);
        return new Fridge_recycle_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Fridge_recycle_holder holder, int position) {
        holder.food_img.setImageResource(items.get(position).getFood_img());
        holder.food_name.setText(items.get(position).getFood_name());
        holder.food_count.setText(items.get(position).getFood_count());
        holder.food_date.setText(items.get(position).getFood_date());

        //재료 수 더하기
        holder.count_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                holder.food_count.setText(count+"개");
            }
        });

        //재료 수 감소하기
        holder.count_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>1){ //식재료 수가 0 또는 음수일때 감소하지 않도록
                    count--;
                    holder.food_count.setText(count+"개");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
