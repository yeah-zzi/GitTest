package yeji.mjc.gittest.comunity;


import android.widget.ImageView;
import android.widget.TextView;

public interface SelectListener {
    void onItemClicked(TipItem tip_fragment);
    void onHeartClicked(TipItem tip_fragment, ImageView imageView, TextView textView);
}