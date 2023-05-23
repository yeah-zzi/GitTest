package yeji.mjc.gittest.comunity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.*;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.SelectListener;

public class FriendAdd_Adapter extends RecyclerView.Adapter<FriendAdd_Holder>{
    private Context context;
    private ArrayList<FriendAdd_Item> items;
    private FriendListener listener;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference makeDB,foodbattleDB;

    public FriendAdd_Adapter(Context context, ArrayList<FriendAdd_Item> items, FriendListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public FriendAdd_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_friend_recyclerview,parent,false);
        return new FriendAdd_Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdd_Holder holder, @SuppressLint("RecyclerView") int position) {
        //이미지 uir로 가져오기 위해
        Glide.with(holder.itemView).load(items.get(position).getUser_img()).into(holder.friend_img);
        holder.friend_name.setText(items.get(position).getUser_name());

        //이벤트 추가
        holder.friend_view.setOnClickListener(new View.OnClickListener() {
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

    //검색을 위한 setItems함수
    public void setItems(ArrayList<FriendAdd_Item> list){
        items = list;
        notifyDataSetChanged();
    }

}
