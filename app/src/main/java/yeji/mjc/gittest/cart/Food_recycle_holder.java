package yeji.mjc.gittest.cart;

import android.view.View;
import android.widget.CheckBox;
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
    ImageView plus;
    ImageView minus;
    ImageView cart;
    TextView foodNum;
    CircleImageView c;

    public Food_recycle_holder(@NonNull View itemView) {
        super(itemView);

        checkBox=itemView.findViewById(R.id.checkBox);
        foodImage=itemView.findViewById(R.id.foodImage);
        foodName=itemView.findViewById(R.id.foodName);
        plus=itemView.findViewById(R.id.plus);
        minus=itemView.findViewById(R.id.minus);
        cart=itemView.findViewById(R.id.cart);
        foodNum=itemView.findViewById(R.id.foodNum);
    }

}
