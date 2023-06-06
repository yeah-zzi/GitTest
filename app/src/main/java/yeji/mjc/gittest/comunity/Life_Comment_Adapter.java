package yeji.mjc.gittest.comunity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import yeji.mjc.gittest.R;

public class Life_Comment_Adapter extends RecyclerView.Adapter<Life_Comment_Adapter.ViewHolder> {

    ArrayList<Tip_comment_item> items;
    //private SelectListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView comment_img;
        TextView comment_id;
        TextView comment;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            comment_img = itemView.findViewById(R.id.cat);
            comment_id = itemView.findViewById(R.id.writer);
            comment = itemView.findViewById(R.id.comment_content);
        }
    }

    public Life_Comment_Adapter(ArrayList<Tip_comment_item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.life_comment_recycler, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Life_Comment_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.comment_id.setText(items.get(position).getComment_writer_id());
        holder.comment.setText(items.get(position).getComment_content());
        Glide.with(holder.itemView).load(items.get(position).getComment_writer_img()).into(holder.comment_img);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
