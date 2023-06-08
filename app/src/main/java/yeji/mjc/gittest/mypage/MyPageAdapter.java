package yeji.mjc.gittest.mypage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import yeji.mjc.gittest.AllergyItem;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.register.Register;
import yeji.mjc.gittest.register.RegisterAllergy_recycle_holder;

public class MyPageAdapter extends RecyclerView.Adapter<MyPage_Holder> {

    ArrayList<MyPageAllergyItem> items;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allergyDB;

    Register register = new Register();
    String userid = register.userid;

    public MyPageAdapter(ArrayList<MyPageAllergyItem> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public MyPage_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_allergy_recyclerview,parent,false);
        return new MyPage_Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPage_Holder holder, int position) {
        Glide.with(holder.itemView).load(items.get(position).getAllergy_img()).into(holder.allergy1);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
