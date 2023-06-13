package yeji.mjc.gittest.mypage;

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
import yeji.mjc.gittest.comunity.FBListener;
import yeji.mjc.gittest.comunity.FBTabHolder;
import yeji.mjc.gittest.comunity.FBTabItem;

public class FriendimgAdapter extends RecyclerView.Adapter<FriendimgHolder>{
    private ArrayList<FBTabItem> items;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference makeDB,foodbattleDB;

    public FriendimgAdapter(ArrayList<FBTabItem> items) {
        this.items = items;
    }

    public FriendimgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_friendimg_recyclerview,parent,false);
        return new FriendimgHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendimgHolder holder, @SuppressLint("RecyclerView") int position) {
        //이미지 uir로 가져오기 위해
        Glide.with(holder.itemView).load(items.get(position).getFb_friend_img()).circleCrop().into(holder.friendImg);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}

