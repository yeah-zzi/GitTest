package yeji.mjc.gittest.comunity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.*;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class FightGo extends AppCompatActivity {

    ImageView friendImg;
    TextView friendId,fbDate;
    ImageButton finish;

    Date startCalender=null,endCalender=null;
    long diffDays;

    String userid,friendid,friendName,friendimg,startDate,changeDate;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference foodbattleDB,makeDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight_go);

        friendImg = findViewById(R.id.friend_img);
        friendId = findViewById(R.id.name);
        fbDate = findViewById(R.id.date);
        finish = findViewById(R.id.complete);

        //전페이지에서 입력받은 정보들 가져오기
        Intent finishIntent = getIntent();
        userid = UserData.getInstance().getUserid();
        friendid = finishIntent.getStringExtra("집밥대결 친구 아이디");
        friendName = finishIntent.getStringExtra("집밥대결 친구 이름");
        friendimg = finishIntent.getStringExtra("집밥대결 친구 이미지");
        startDate = finishIntent.getStringExtra("집밥대결 시작날짜");
        changeDate = finishIntent.getStringExtra("집밥대결 종료날짜");


        //문자열을 날짜로 변환
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        try {
            startCalender = df.parse(startDate);
            endCalender = df.parse(changeDate);

            //시작날짜와 종료날짜의 차이를 구해 기간을 구하기
            long diffSec = (endCalender.getTime() - startCalender.getTime()) / 1000; //초 차이
            diffDays = diffSec / (24*60*60); //일자수 차이
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //이미지 로드
        Glide.with(this).load(friendimg).into(friendImg);
        //아이디 적용
        friendId.setText(friendName);
        fbDate.setText(diffDays+"일간");

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDB = database.getReference().child("foodbattle").push();
                foodbattleDB = makeDB.child("fb_end_date");
                foodbattleDB.setValue(changeDate);
                foodbattleDB = makeDB.child("fb_start_date");
                foodbattleDB.setValue(startDate);
                foodbattleDB = makeDB.child("fb_mem").child(friendid).child("upload_count");
                foodbattleDB.setValue(0);
                foodbattleDB = makeDB.child("fb_mem").child(userid).child("upload_count");
                foodbattleDB.setValue(0);

                String fb_code = makeDB.getKey();


                //로그인한 유저한테 집밥대결 코드 정보를 저장
                makeDB = database.getReference().child("user").child(userid).child("foodbattle_code").child(fb_code);
                makeDB.child("code").setValue(fb_code);
                makeDB.child("fb_friend").setValue(friendid);
                makeDB.child("fb_friend_img").setValue(friendimg);

                //집밥대결을 신청한 친구한테 집밥대결 코드 정보를 저장
                makeDB = database.getReference().child("user").child(friendid).child("foodbattle_code").child(fb_code);
                makeDB.child("code").setValue(fb_code);
                makeDB.child("fb_friend").setValue(userid);
                //makeDB.child("fb_friend_img").setValue(friendimg);
                finish();
            }
        });

    }
}
