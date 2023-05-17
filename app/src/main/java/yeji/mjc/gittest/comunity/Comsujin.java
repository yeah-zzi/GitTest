package yeji.mjc.gittest.comunity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import yeji.mjc.gittest.R;

public class Comsujin extends Fragment implements View.OnClickListener{

    ColorStateList def;
    TextView life_info,tip,food_battle,select;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //커뮤티니 메뉴바 구현
        View view = inflater.inflate(R.layout.tip_main, container, false);

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
            getFragmentManager().beginTransaction().replace(R.id.tip_container, new Fight_fragment()).commit();
        }
    }//
}