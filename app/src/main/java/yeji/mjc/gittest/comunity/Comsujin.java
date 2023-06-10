package yeji.mjc.gittest.comunity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class Comsujin extends Fragment implements View.OnClickListener{

    ColorStateList def;
    TextView life_info,tip,food_battle,select;

    String userid;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference foodbattleDB;
    int foodbattle_count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //커뮤티니 메뉴바 구현
        View view = inflater.inflate(R.layout.tip_main, container, false);

        userid = UserData.getInstance().getUserid();

        getFragmentManager().beginTransaction().add(R.id.tip_container, new Life_Fragment()).commit();

        life_info = view.findViewById(R.id.life_info);
        tip = view.findViewById(R.id.tip);
        food_battle = view.findViewById(R.id.food_battle);


        life_info.setOnClickListener(this);
        tip.setOnClickListener(this);
        food_battle.setOnClickListener(this);

        select = view.findViewById(R.id.select);
        def = tip.getTextColors();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //파이어베이스에서 유저당 대결횟수를 가져옴
        foodbattleDB = database.getReference().child("user").child(userid).child("foodbattle_count");
        foodbattleDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodbattle_count = (int)snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //커뮤니티 메뉴바 선택 배경 움직이는 애니메이션
    public void onClick(View view){
        if(view.getId() == R.id.life_info){
            select.animate().x(70).setDuration(100);
            //선택되는 메뉴에 따라 프래그먼트가 해당 커뮤니티의 프래그먼트로 바뀐다
            getFragmentManager().beginTransaction().replace(R.id.tip_container, new Life_Fragment()).commit();
        }else if(view.getId()==R.id.tip){
            int size = tip.getWidth() + 75;
            select.animate().x(size).setDuration(100);
            getFragmentManager().beginTransaction().replace(R.id.tip_container, new Tip_fragment()).commit();
        }else if(view.getId()==R.id.food_battle){
            int size = tip.getWidth() * 2 + 85;
            select.animate().x(size).setDuration(100);
            //유저 대결횟수가 0일때와 아닐때의 화면이 다르게 나온다
            if(foodbattle_count == 0)
            {
                getFragmentManager().beginTransaction().replace(R.id.tip_container, new NewFight_fragment()).commit();
            }else{
                getFragmentManager().beginTransaction().replace(R.id.tip_container, new Fight_fragment()).commit();
            }
        }
    }//

}
