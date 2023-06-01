package yeji.mjc.gittest.mypage;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class MyPage_Holder extends RecyclerView.ViewHolder {

    ImageView allergy1, allergy2, allergy3;

    public MyPage_Holder(@NonNull View itemView) {
        super(itemView);

        allergy1 = itemView.findViewById(R.id.allergy1);
        allergy2 = itemView.findViewById(R.id.allergy2);
        allergy3 = itemView.findViewById(R.id.allergy3);
    }
}
