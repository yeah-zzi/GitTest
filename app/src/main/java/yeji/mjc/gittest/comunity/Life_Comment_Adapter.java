package yeji.mjc.gittest.comunity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class Life_Comment_Adapter extends RecyclerView.Adapter<Life_Comment_Adapter.ViewHolder>{

    String userid;
    ArrayList<Tip_comment_item> items;
    private CommentListener listener;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference commentDB;

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView comment_img;
        ImageView delete;
        TextView comment_id;
        TextView comment;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            delete = itemView.findViewById(R.id.delete);
            comment_img = itemView.findViewById(R.id.cat);
            comment_id = itemView.findViewById(R.id.writer);
            comment = itemView.findViewById(R.id.comment_content);
        }
    }

    public Life_Comment_Adapter(ArrayList<Tip_comment_item> items, CommentListener listener) {
        this.items = items;
        this.listener = listener;
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
