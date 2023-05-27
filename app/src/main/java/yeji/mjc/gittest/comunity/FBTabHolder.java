package yeji.mjc.gittest.comunity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import yeji.mjc.gittest.R;

public class FBTabHolder extends RecyclerView.ViewHolder{

    CircleImageView fb_friendImg;
    ImageButton cancel;

    public FBTabHolder(@NonNull View itemView) {
        super(itemView);
        fb_friendImg = itemView.findViewById(R.id.fb_friendImg);
        cancel = itemView.findViewById(R.id.cancel);
    }
}
