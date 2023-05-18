package yeji.mjc.gittest.register;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import yeji.mjc.gittest.AllergyItem;
import yeji.mjc.gittest.R;

public class RegisterAllergyAdapter extends RecyclerView.Adapter<RegisterAllergy_recycle_holder> {

    ArrayList<AllergyItem> items;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allergyDB;

    public RegisterAllergyAdapter(ArrayList<AllergyItem> items) {
        this.items = items;
    }

    //리사이클러뷰 홀더를 연결한다
    public RegisterAllergy_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_allergy_recyclerview,parent,false);
        return new RegisterAllergy_recycle_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterAllergy_recycle_holder holder, @SuppressLint("RecyclerView") int position) {
        holder.food_name.setText(items.get(position).getAllergy_name());
        holder.food_img.setImageResource(items.get(position).getAllergy_img());
        //x버튼을 누를시 해당 알러지를 파이어베이스에서 삭제하며 해당 리사이클러뷰도 삭제한다
        String deleteAllergyName = items.get(position).getAllergy_name();

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allergyDB = database.getReference().child("userid").child("allergy").child(deleteAllergyName);
                allergyDB.removeValue();

                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
