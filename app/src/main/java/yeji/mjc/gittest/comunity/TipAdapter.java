package yeji.mjc.gittest.comunity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.Queue;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class TipAdapter extends RecyclerView.Adapter<Tip_recycle_holder>{

    private Context tipContext;
    private ArrayList<TipItem> items;
    private SelectListener listener;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference heartDB,heartClickDB,likeDB;
    String userid,code,heartClick;
    int like_size;


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

        userid = UserData.getInstance().getUserid();
        code = items.get(position).getCom_code();

        //유저가 하트를 눌렀는지 불린 가져오기
        heartDB = database.getReference().child("tip").child(code).child("like_user");
        heartDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                heartClick = "unclick";
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(userid.equals(dataSnapshot.getKey())){
                        heartClick = dataSnapshot.getValue(String.class);
                        break;
                    }
                }

                // onDataChange 메소드가 호출된 이후에 처리되어야 하는 로직 호출
                handleHeartClick(holder.like_btn);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 오류 처리
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(items.get(position));
            }
        });

        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHeartClicked(items.get(position),holder.like_btn,holder.like);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void handleHeartClick(ImageView imageView) {
        // 불러온 유저 하트 누른 Boolean으로 설정
        if(heartClick.equals("click")){
            imageView.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }

}
