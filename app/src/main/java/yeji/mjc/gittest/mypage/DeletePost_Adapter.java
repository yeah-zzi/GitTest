package yeji.mjc.gittest.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;
import yeji.mjc.gittest.comunity.Life_recycle_holder;
import yeji.mjc.gittest.comunity.SelectListener;
import yeji.mjc.gittest.comunity.TipItem;
import yeji.mjc.gittest.comunity.Tip_recycle_holder;

public class DeletePost_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context postContext;
    private ArrayList<TipItem> items;
    private SelectListener listener;
    final int[] postType = new int[2];

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference life_postDB, tip_postDB;
    String userid,code;

    public DeletePost_Adapter(Context postContext, ArrayList<TipItem> items, SelectListener listener) {
        this.postContext = postContext;
        this.items = items;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        userid = UserData.getInstance().getUserid();

        // 파이어베이스에서 게시물 유형 구분, life = 0, tip = 1
        life_postDB = database.getReference().child(userid);
        life_postDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String value = dataSnapshot.getValue(String.class);
                    //life 게시물이 존재한다면
                    if (value == "life_post") {
                        postType[0] = 0;
                    } else if (value == "tip_post"){
                        postType[1] = 1;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // life 게시물 holder 넣기
        if(postType[0]==0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.life_post_items, parent, false);
            return new Life_recycle_holder(view);
        }
        // tip 게시물 holder 넣기
        else if (postType[1]==1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_recyclerview, parent, false);
            return new Tip_recycle_holder(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TipItem tipItem = items.get(position);
        if(postType[0]==0){
//            Life_recycle_holder holder1 = (Life_recycle_holder) holder;
//            holder1.text_title.setText(items.get(position).getTitle());
//            holder1.text_script.setText(items.get(position).getContent());
//            Glide.with(holder1.itemView).load(items.get(position).getPost_img()).into(holder1.imageView);

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
