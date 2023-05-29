package yeji.mjc.gittest.comunity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class FB_IMG_Holder extends RecyclerView.ViewHolder{

    ImageView fb_img;
    TextView fb_img_date;

    public FB_IMG_Holder(@NonNull View itemView) {
        super(itemView);
        fb_img = itemView.findViewById(R.id.fb_img);
        fb_img_date = itemView.findViewById(R.id.fb_img_date);
    }
}
