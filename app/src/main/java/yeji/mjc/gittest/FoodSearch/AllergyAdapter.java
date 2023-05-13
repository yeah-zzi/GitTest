package yeji.mjc.gittest.FoodSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class AllergyAdapter extends RecyclerView.Adapter<Allergy_recycle_holder> {

    //Context context;
    ArrayList<AllergyItem> items;

    public AllergyAdapter(ArrayList<AllergyItem> items) {
        this.items = items;
    }

    public Allergy_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.allergy_search_recyclerview,parent,false);
        return new Allergy_recycle_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Allergy_recycle_holder holder, int position) {
        holder.food_img.setImageResource(items.get(position).getFood_img());
        holder.food_name.setText(items.get(position).getFood_name());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
