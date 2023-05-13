package yeji.mjc.gittest.mypage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class NewFriend_holder extends RecyclerView.ViewHolder{

    ImageView friend_img;
    TextView friend_id,battle_count;

    public NewFriend_holder(@NonNull View itemView) {
        super(itemView);
        friend_img = itemView.findViewById(R.id.friend_img);
        friend_id = itemView.findViewById(R.id.friend_id);
        battle_count = itemView.findViewById(R.id.battle_count);
    }
}
