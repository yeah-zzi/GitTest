package yeji.mjc.gittest.cart;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import yeji.mjc.gittest.R;

public class Food_recycle_holder extends RecyclerView.ViewHolder {
    CheckBox checkBox;
    ImageView foodImage;
    TextView foodName;
    TextView foodNum;
    ImageButton count_plus;
    ImageButton count_minus;

    public Food_recycle_holder(@NonNull View itemView) {
        super(itemView);

        checkBox=itemView.findViewById(R.id.checkBox);
        foodImage=itemView.findViewById(R.id.foodImage);
        foodName=itemView.findViewById(R.id.foodName);
        foodNum=itemView.findViewById(R.id.foodNum);
        count_plus=itemView.findViewById(R.id.count_plus);
        count_minus=itemView.findViewById(R.id.count_minus);
    }

}
