package yeji.mjc.gittest.comunity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class ReadyAdapter extends RecyclerView.Adapter<ReadyAdapter.ViewHolder> {

    private ArrayList<FBTabItem> items = null;
    private FBListener listener;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference readyDB;
    String userid;

    public class ViewHolder extends RecyclerView.ViewHolder{
       CircleImageView friend_img;
       TextView ok,no;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            friend_img = itemView.findViewById(R.id.cat);
            ok = itemView.findViewById(R.id.ok);
            no = itemView.findViewById(R.id.no);
        }
    }

    public ReadyAdapter(ArrayList<FBTabItem> items, FBListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReadyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.ready_recyclerview, parent, false);
        ReadyAdapter.ViewHolder vh = new ReadyAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReadyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView).load(items.get(position).getFb_friend_img()).into(holder.friend_img);

        userid = UserData.getInstance().getUserid();

        holder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(items.get(position));
            }
        });

        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자한테 집밥대결 코드 삭제
                readyDB = database.getReference().child("user").child(userid).child("foodbattle_code").child(items.get(position).getCode());
                readyDB.removeValue();
                //친구한테서 집밥대결 코드 삭제
                readyDB = database.getReference().child("user").child(items.get(position).getFb_friend()).child("foodbattle_code").child(items.get(position).getCode());
                readyDB.removeValue();
                //집밥대결에서 코드 삭제
                readyDB = database.getReference().child("foodbattle").child(items.get(position).getCode());
                readyDB.removeValue();

                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
