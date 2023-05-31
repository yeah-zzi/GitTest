package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import yeji.mjc.gittest.FoodSearch.FridgePlus;
import yeji.mjc.gittest.cart.Cartsujin;
import yeji.mjc.gittest.comunity.Comsujin;
import yeji.mjc.gittest.comunity.New_fight_sub;
import yeji.mjc.gittest.comunity.New_friend_sub;
import yeji.mjc.gittest.frige.Fridge;
import yeji.mjc.gittest.mypage.MyPagesujin;

public class MainActivity extends AppCompatActivity {

    //플로팅 버튼
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    private Animation die;
    FloatingActionButton tab, tabBarcode, tabWrite ,tabFriendAdd, tabPost, tabCamera, tabBattle;
    boolean clicked = false;
    private boolean community = false;
    boolean from_comm = false;

    // 하단바 연결
    BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰

    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference addfoodDB;
    String userid = "임시용 유저 아이디1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);
        die = AnimationUtils.loadAnimation(this,R.anim.die_ainm);


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
                addfoodDB=database.getReference().child("user").child(userid).child("addfood");
                //식재료 추가 버튼 눌렀을때 빈 페이지 불러오기
                //addfoodDB.removeValue();

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
                Intent addfriend = new Intent(MainActivity.this, New_friend_sub.class);
                startActivity(addfriend);
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
                Intent FBIMGPlusintent = new Intent(MainActivity.this, FoodBattle_IMG_Upload.class);
                startActivity(FBIMGPlusintent);
            }
        });

        tabBattle = findViewById(R.id.tabBattle);
        tabBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addfight = new Intent(MainActivity.this, New_fight_sub.class);
                startActivity(addfight);
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //FrameLayout에 xml 띄우기
        getSupportFragmentManager().beginTransaction().add(R.id.container, new Fridge()).commit();

        //바텀 네비게이션뷰 안의 아이템 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //냉장고
                    case R.id.f:
                        community = false;
                        if(clicked == true){
                            clicked = false;
                            if(from_comm == true){
                                tab.startAnimation(rotateClose);
                                tabFriendAdd.startAnimation(toBottom);
                                tabPost.startAnimation(toBottom);
                                tabCamera.startAnimation(toBottom);
                                tabBattle.startAnimation(toBottom);
                                tabWrite.startAnimation(toBottom);
                                tabBarcode.startAnimation(toBottom);
                            }else{
                                setAnimation();
                            }
                        }
                        from_comm = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fridge()).commit();
                        break;
                    case R.id.c://장바구니
                        community = false;
                        if(clicked == true){
                            clicked = false;
                            if(from_comm == true){
                                tab.startAnimation(rotateClose);
                                tabFriendAdd.startAnimation(toBottom);
                                tabPost.startAnimation(toBottom);
                                tabCamera.startAnimation(toBottom);
                                tabBattle.startAnimation(toBottom);
                                tabWrite.startAnimation(toBottom);
                                tabBarcode.startAnimation(toBottom);
                            }else{
                                setAnimation();
                            }
                        }
                        from_comm = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Cartsujin()).commit();
                        break;
                    case R.id.p://마이페이지
                        community = false;
                        if(clicked == true){
                            clicked = false;
                            if(from_comm == true){
                                tab.startAnimation(rotateClose);
                                tabFriendAdd.startAnimation(toBottom);
                                tabPost.startAnimation(toBottom);
                                tabCamera.startAnimation(toBottom);
                                tabBattle.startAnimation(toBottom);
                                tabWrite.startAnimation(toBottom);
                                tabBarcode.startAnimation(toBottom);
                            }else{
                                setAnimation();
                            }
                        }
                        from_comm = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyPagesujin()).commit();
                        break;
                    case R.id.co://커뮤니티
                        if(clicked == true){
                            clicked = false;
                            setAnimation();
                        }
                        community = true;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Comsujin()).commit();
                        from_comm = true;
                        break;
                }
                return true;
            }
        });

    }


    // 클릭 이벤트
    private void onAddButtonCliked(){
        clicked = !clicked;
        setVisibility();
        setAnimation();
    }



    // 보여짐 사라짐
    private void setVisibility(){

        if(clicked == true){
            if(community == true)
            {
                tabBarcode.setEnabled(false); tabWrite.setEnabled(false);
                tabWrite.setVisibility(View.GONE); tabBarcode.setVisibility(View.GONE);
                tabFriendAdd.setVisibility(View.VISIBLE); tabPost.setVisibility(View.VISIBLE); tabCamera.setVisibility(View.VISIBLE); tabBattle.setVisibility(View.VISIBLE);
                tabCamera.setEnabled(true); tabPost.setEnabled(true); tabBattle.setEnabled(true); tabFriendAdd.setEnabled(true);
            }else{
                tabBarcode.setEnabled(true); tabWrite.setEnabled(true);
                tabWrite.setVisibility(View.VISIBLE); tabBarcode.setVisibility(View.VISIBLE);
                tabFriendAdd.setVisibility(View.GONE); tabPost.setVisibility(View.GONE);
                tabCamera.setVisibility(View.GONE); tabBattle.setVisibility(View.GONE);
                tabCamera.setEnabled(false); tabPost.setEnabled(false); tabBattle.setEnabled(false); tabFriendAdd.setEnabled(false);
            }
        }
    }


    // 애니메이션
    private  void setAnimation() {

        if (clicked == true) {
            tab.startAnimation(rotateOpen);
            if(community == true)
            {
                tabFriendAdd.startAnimation(fromBottom);
                tabPost.startAnimation(fromBottom);
                tabCamera.startAnimation(fromBottom);
                tabBattle.startAnimation(fromBottom);
                tabWrite.startAnimation(fromBottom);
                tabBarcode.startAnimation(fromBottom);
            }else{
                tabWrite.startAnimation(fromBottom);
                tabBarcode.startAnimation(fromBottom);
            }
        } else {
            tab.startAnimation(rotateClose);
            if(community == true){
                tabFriendAdd.startAnimation(toBottom);
                tabPost.startAnimation(toBottom);
                tabCamera.startAnimation(toBottom);
                tabBattle.startAnimation(toBottom);
                tabWrite.startAnimation(toBottom);
                tabBarcode.startAnimation(toBottom);
            }else{
                tabFriendAdd.startAnimation(die);
                tabPost.startAnimation(die);
                tabCamera.startAnimation(die);
                tabBattle.startAnimation(die);
                tabWrite.startAnimation(toBottom);
                tabBarcode.startAnimation(toBottom);
            }
        }


    }

}



