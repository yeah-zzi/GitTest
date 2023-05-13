package yeji.mjc.gittest.comunity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class Tip_comment_recycle_holder extends RecyclerView.ViewHolder {

    ImageView comment_img;
    TextView comment_id;
    TextView comment;

    public Tip_comment_recycle_holder(@NonNull View itemView) {
        super(itemView);

        comment_img = itemView.findViewById(R.id.comment);
        comment_id = itemView.findViewById(R.id.comment_id);
        comment = itemView.findViewById(R.id.comment_content);

    }
}
