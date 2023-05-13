package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import yeji.mjc.gittest.FoodSearch.FridgePlus;
import yeji.mjc.gittest.comunity.Comsujin;
import yeji.mjc.gittest.frige.Frigesujin;
import yeji.mjc.gittest.mypage.Bellset;
import yeji.mjc.gittest.mypage.UserInfoChange;

public class MainActivity extends AppCompatActivity {

    //플로팅 버튼
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    FloatingActionButton tab, tab2, tab3 ,tab4;
    private boolean cliked = false;

    // 하단바 연결
    BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰

    //프래그먼트 연결
    Bellset fragment_bellset;
    UserInfoChange fragment_user_info_change;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_bellset = new Bellset();
        fragment_user_info_change = new UserInfoChange();


        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        // + 버튼
        tab = findViewById(R.id.tab);
        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonCliked();
            }
        });

        // tab2 숫자 올라갈 수록 위쪽에 있는 플로팅 버튼임
        tab2 = findViewById(R.id.tab2);
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "ㅇㅇ", Toast.LENGTH_SHORT).show();
            }
        });


        tab3 = findViewById(R.id.tab3);
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //그림이 만든 냉장고추가 팝업창 설정
                Intent fridgePlusIntent = new Intent(MainActivity.this, FridgePlus.class);
                startActivity(fridgePlusIntent);
            }
        });

        tab4 = findViewById(R.id.tab4);
        /*tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //FrameLayout에 xml 띄우기
        getSupportFragmentManager().beginTransaction().add(R.id.container, new Frigesujin()).commit();

        //바텀 네비게이션뷰 안의 아이템 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.f:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Frigesujin()).commit();
                        break;
                    case R.id.c:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Cartsujin()).commit();
                        break;
                    case R.id.p:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyPagesujin()).commit();
                        break;
                    case R.id.co:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Comsujin()).commit();
                        break;
                }
                return true;
            }
        });
    }

    // fragment 교체
    public void onFragmentChanged(int index) {
        if(index == 0)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.mpcontainer, fragment_bellset).commit();
        }

        else if(index == 1)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.mpcontainer, fragment_user_info_change).commit();
        }
    }


    // 클릭 이벤트
    private void onAddButtonCliked(){
        setVisibility(cliked);
        setAnimation(cliked);
        cliked = !cliked;
    }

    // 보여짐 사라짐
    private void setVisibility(boolean cliked){

        if(!cliked){
            tab2.setVisibility(tab2.VISIBLE);
            tab3.setVisibility(tab3.VISIBLE);
            tab4.setVisibility(tab4.VISIBLE);
        } else {
            tab2.setVisibility(tab2.INVISIBLE);
            tab3.setVisibility(tab3.INVISIBLE);
            tab4.setVisibility(tab4.INVISIBLE);
        }
    }

    // 애니메이션
    private  void setAnimation(boolean cliked){

        if(!cliked){
            tab.startAnimation(rotateOpen);
            tab2.startAnimation(fromBottom);
            tab3.startAnimation(fromBottom);
            tab4.startAnimation(fromBottom);
        } else {
            tab.startAnimation(rotateClose);
            tab2.startAnimation(toBottom);
            tab3.startAnimation(toBottom);
            tab4.startAnimation(toBottom);

        }
    }
}


