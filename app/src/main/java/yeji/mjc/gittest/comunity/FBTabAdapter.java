package yeji.mjc.gittest.comunity;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class FBTabAdapter extends RecyclerView.Adapter<FBTabHolder>{
    private ArrayList<FBTabItem> items;
    private FBListener listener;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference makeDB,foodbattleDB;

    public FBTabAdapter(ArrayList<FBTabItem> items, FBListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public FBTabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fb_tab_recyclerview,parent,false);
        return new FBTabHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FBTabHolder holder, @SuppressLint("RecyclerView") int position) {
        //이미지 uir로 가져오기 위해
        Glide.with(holder.itemView).load(items.get(position).getFb_friend_img()).into(holder.fb_friendImg);

        //이벤트 추가
        holder.fb_friendImg.setOnClickListener(new View.OnClickListener() {
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

