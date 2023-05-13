package yeji.mjc.gittest.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import yeji.mjc.gittest.R;

public class NewFriendAdapter extends RecyclerView.Adapter<NewFriend_holder> {

    Context context;
    List<NewFriendItem> items;

    public NewFriendAdapter(Context context, List<NewFriendItem> items) {
        this.context = context;
        this.items = items;
    }

    public NewFriend_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewFriend_holder(LayoutInflater.from(context).inflate(R.layout.new_friend_recyclerview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewFriend_holder holder, int position) {
        holder.friend_img.setImageResource(items.get(position).getFriend_img());
        holder.friend_id.setText(items.get(position).getFriend_id());
        holder.battle_count.setText(items.get(position).getBattle_count());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
