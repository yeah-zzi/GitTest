package yeji.mjc.gittest.comunity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class FriendAdd_Holder extends RecyclerView.ViewHolder{

    TextView friend_name;
    ImageView friend_img;
    ImageView friend_view;

    public FriendAdd_Holder(@NonNull View itemView) {
        super(itemView);
        friend_name = itemView.findViewById(R.id.friend_id);
        friend_img= itemView.findViewById(R.id.friend_img);
        friend_view = itemView.findViewById(R.id.friend_view);

    }

}
