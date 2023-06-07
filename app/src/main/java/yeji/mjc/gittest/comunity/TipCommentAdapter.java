package yeji.mjc.gittest.comunity;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class TipCommentAdapter extends RecyclerView.Adapter<Tip_comment_recycle_holder> {

    //Context context;
    ArrayList<Tip_comment_item> items;
    private CommentListener listener;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference commentDB;
    String userid;

    public TipCommentAdapter(ArrayList<Tip_comment_item> items, CommentListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public Tip_comment_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_comment_recycler,parent,false);
        return new Tip_comment_recycle_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Tip_comment_recycle_holder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView).load(items.get(position).getComment_writer_img()).into(holder.comment_img);
        holder.comment_id.setText(items.get(position).getComment_writer_id());
        holder.comment.setText(items.get(position).getComment_content());

        userid = UserData.getInstance().getUsername();

        if(userid.equals(items.get(position).getComment_writer_id())){
            holder.delete.setVisibility(View.VISIBLE);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(items.get(position));
                }
            });
        }else{
            holder.delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
