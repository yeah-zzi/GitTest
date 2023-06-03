package yeji.mjc.gittest.comunity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class TipAdapter extends RecyclerView.Adapter<Tip_recycle_holder>{

    private Context tipContext;
    private ArrayList<TipItem> items;
    private SelectListener listener;

    public TipAdapter(Context tipContext, ArrayList<TipItem> items, SelectListener listener) {
        this.tipContext = tipContext;
        this.items = items;
        this.listener = listener;
    }

    public Tip_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_recyclerview,parent,false);
        return new Tip_recycle_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Tip_recycle_holder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView).load(items.get(position).getPost_img()).into(holder.tip_img);
        holder.id.setText(items.get(position).getWriter());
        holder.like.setText(items.get(position).getLike());
        holder.comment.setText(items.get(position).getComment_count());
        holder.content.setText(items.get(position).getTitle());

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
