package yeji.mjc.gittest.mypage;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import yeji.mjc.gittest.R;

public class FriendimgHolder extends RecyclerView.ViewHolder{

    CircleImageView friendImg;

    public FriendimgHolder(@NonNull View itemView) {
        super(itemView);
        friendImg = itemView.findViewById(R.id.friendImg);
    }
}
