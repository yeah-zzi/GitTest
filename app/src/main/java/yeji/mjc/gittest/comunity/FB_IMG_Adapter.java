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

public class FB_IMG_Adapter extends RecyclerView.Adapter<FB_IMG_Holder>{

    private ArrayList<FB_IMG_Item> items;

    public FB_IMG_Adapter(ArrayList<FB_IMG_Item> items) {
        this.items = items;
    }

    public FB_IMG_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fb_upload_recyclerview,parent,false);
        return new FB_IMG_Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FB_IMG_Holder holder, @SuppressLint("RecyclerView") int position) {
        //이미지 uir로 가져오기 위해
        Glide.with(holder.itemView).load(items.get(position).getUpload_img()).into(holder.fb_img);
        holder.fb_img_date.setText(items.get(position).getUpload_time());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}
