package yeji.mjc.gittest.frige;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import yeji.mjc.gittest.R;

public class FoodSearch_recycle_holder_Fridge extends RecyclerView.ViewHolder {

    //리사이클러뷰에 사용된 각 뷰의 id를 연결해주기 위한 홀더이다
    ImageView food_img;
    TextView food_name;
    CardView cardView;

    public FoodSearch_recycle_holder_Fridge(@NonNull View itemView) {
        super(itemView);

        food_img = itemView.findViewById(R.id.food_img);
        food_name = itemView.findViewById(R.id.food_name);
        cardView = itemView.findViewById(R.id.cv_item_movie_parent);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition(); //현재 자신의 위치를 알아내는 메서드
                if (pos != RecyclerView.NO_POSITION) {
                    // 리스너 객체의 메서드 호출.
                    if (mListener != null) {
                        mListener.onItemClick(v, pos) ;
                    }
                }
            }
        });
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }
}


