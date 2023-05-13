package yeji.mjc.gittest.comunity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class TipCommentAdapter extends RecyclerView.Adapter<Tip_comment_recycle_holder> {

    //Context context;
    ArrayList<Tip_comment_item> items;

    public TipCommentAdapter(ArrayList<Tip_comment_item> items) {
        this.items = items;
    }

    public Tip_comment_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_comment_recycler,parent,false);
        return new Tip_comment_recycle_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Tip_comment_recycle_holder holder, int position) {
        holder.comment_img.setImageResource(items.get(position).getComment_img());
        holder.comment_id.setText(items.get(position).getComment_id());
        holder.comment.setText(items.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
