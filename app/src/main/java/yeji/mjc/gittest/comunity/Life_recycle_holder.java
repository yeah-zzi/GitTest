package yeji.mjc.gittest.comunity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class Life_recycle_holder extends RecyclerView.ViewHolder{
    TextView text_title;
    TextView text_script;
    ImageView imageView;
    CardView back;

    public Life_recycle_holder(@NonNull View itemView){
        super(itemView);

        text_title = itemView.findViewById(R.id.title_text);
        text_script = itemView.findViewById((R.id.script_text));
        imageView = itemView.findViewById(R.id.post_img);
        back = itemView.findViewById(R.id.back);
    }
}