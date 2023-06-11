package yeji.mjc.gittest.comunity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class LifePost_Adapter extends RecyclerView.Adapter<Life_recycle_holder> {

    private ArrayList<TipItem> items = null;
    private SelectListener listener;

    public LifePost_Adapter(ArrayList<TipItem> items, SelectListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Life_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.life_post_items, parent, false);
        Life_recycle_holder vh = new Life_recycle_holder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Life_recycle_holder holder, @SuppressLint("RecyclerView") int position) {
        holder.text_title.setText(items.get(position).getTitle());
        holder.text_script.setText(items.get(position).getContent());
        Glide.with(holder.itemView).load(items.get(position).getPost_img()).into(holder.imageView);

        holder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(items.get(position));
            }
        });

        //TODO : [holder.객체.setOnClickListener] 만들어야 함 --> 클릭 시, 게시물 상세페이지로 넘어가게
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
