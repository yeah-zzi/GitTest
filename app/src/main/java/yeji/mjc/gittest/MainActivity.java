package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import yeji.mjc.gittest.FoodSearch.FridgePlus;
import yeji.mjc.gittest.cart.Cartsujin;
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
    FloatingActionButton tab, tabBarcode, tabWrite ,tabFriendAdd, tabPost, tabCamera, tabBattle;
    private boolean cliked = false;

    // 하단바 연결
    BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        tabWrite = findViewById(R.id.tabWrite);
        tabWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FridgePlusintent = new Intent(MainActivity.this, FridgePlus.class);
                startActivity(FridgePlusintent);
            }
        });

        tabBarcode = findViewById(R.id.tabBarcode);
        tabBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcode = new Intent(MainActivity.this, Barcode.class);
                startActivity(barcode);
            }
        });


        tabFriendAdd = findViewById(R.id.tabFriendAdd);
        tabFriendAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tabFriendAdd 누르면 친구 추가 화면
            }
        });

        tabPost =findViewById(R.id.tabPost);
        tabPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tabPost 누르면 글쓰는 화면
            }
        });

        tabCamera = findViewById(R.id.tabCamera);
        tabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tabCamera 누르면 카메라
            }
        });

        tabBattle = findViewById(R.id.tabBattle);
        tabBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tabBattle 누르면 배틀신청
            }
        });

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
                        tabBarcode.setEnabled(true); tabWrite.setEnabled(true);
                        tabWrite.setVisibility(View.VISIBLE); tabBarcode.setVisibility(View.VISIBLE);
                        tabFriendAdd.setVisibility(View.INVISIBLE); tabPost.setVisibility(View.INVISIBLE);
                        tabCamera.setVisibility(View.INVISIBLE); tabBattle.setVisibility(View.INVISIBLE);
                        tabCamera.setEnabled(false); tabPost.setEnabled(false); tabBattle.setEnabled(false); tabFriendAdd.setEnabled(false);
                        break;
                    case R.id.c:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Cartsujin()).commit();
                        tabBarcode.setEnabled(true); tabWrite.setEnabled(true);
                        tabWrite.setVisibility(View.VISIBLE); tabBarcode.setVisibility(View.VISIBLE);
                        tabFriendAdd.setVisibility(View.INVISIBLE); tabPost.setVisibility(View.INVISIBLE);
                        tabCamera.setVisibility(View.INVISIBLE); tabBattle.setVisibility(View.INVISIBLE);
                        tabCamera.setEnabled(false); tabPost.setEnabled(false); tabBattle.setEnabled(false); tabFriendAdd.setEnabled(false);
                        break;
                    case R.id.p:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyPagesujin()).commit();
                        tabBarcode.setEnabled(true); tabWrite.setEnabled(true);
                        tabWrite.setVisibility(View.VISIBLE); tabBarcode.setVisibility(View.VISIBLE);
                        tabFriendAdd.setVisibility(View.INVISIBLE); tabPost.setVisibility(View.INVISIBLE);
                        tabCamera.setVisibility(View.INVISIBLE); tabBattle.setVisibility(View.INVISIBLE);
                        tabCamera.setEnabled(false); tabPost.setEnabled(false); tabBattle.setEnabled(false); tabFriendAdd.setEnabled(false);
                        break;
                    case R.id.co:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Comsujin()).commit();
                        tabBarcode.setEnabled(false); tabWrite.setEnabled(false);
                        tabWrite.setVisibility(View.INVISIBLE); tabBarcode.setVisibility(View.INVISIBLE);
                        tabFriendAdd.setVisibility(View.VISIBLE); tabPost.setVisibility(View.VISIBLE); tabCamera.setVisibility(View.VISIBLE); tabBattle.setVisibility(View.VISIBLE);
                        tabCamera.setEnabled(true); tabPost.setEnabled(true); tabBattle.setEnabled(true); tabFriendAdd.setEnabled(true);
                        break;

                }
                return true;
            }
        });

    }


    // 클릭 이벤트
    private void onAddButtonCliked(){
        //setVisibility(cliked);
        tabCamera.setVisibility(View.INVISIBLE);
        tabFriendAdd.setVisibility(View.INVISIBLE);
        setAnimation(cliked);
        cliked = !cliked;
    }


//    // 보여짐 사라짐
//    private void setVisibility(boolean cliked){
//
//        if(!cliked){
//            tabWrite.setVisibility(View.VISIBLE);
//            tabBarcode.setVisibility(View.VISIBLE);
//            tabFriendAdd.setVisibility(View.VISIBLE);
//            tabPost.setVisibility(View.VISIBLE);
//            tabCamera.setVisibility(View.VISIBLE);
//            tabBattle.setVisibility(View.VISIBLE);
//
//        } else {
//            tabWrite.setVisibility(View.INVISIBLE);
//            tabBarcode.setVisibility(View.INVISIBLE);
//            tabFriendAdd.setVisibility(View.INVISIBLE);
//            tabPost.setVisibility(View.INVISIBLE);
//            tabCamera.setVisibility(View.INVISIBLE);
//            tabBattle.setVisibility(View.INVISIBLE);
//        }
//    }

    // 애니메이션
    private  void setAnimation(boolean cliked) {

        if (!cliked) {
            tab.startAnimation(rotateOpen);
            tabWrite.startAnimation(fromBottom);
            tabBarcode.startAnimation(fromBottom);
            tabFriendAdd.startAnimation(fromBottom);
            tabPost.startAnimation(fromBottom);
            tabCamera.startAnimation(fromBottom);
            tabBattle.startAnimation(fromBottom);
        } else {
            tab.startAnimation(rotateClose);
            tabWrite.startAnimation(toBottom);
            tabBarcode.startAnimation(toBottom);
            tabFriendAdd.startAnimation(toBottom);
            tabPost.startAnimation(toBottom);
            tabCamera.startAnimation(toBottom);
            tabBattle.startAnimation(toBottom);
        }


    }

}



