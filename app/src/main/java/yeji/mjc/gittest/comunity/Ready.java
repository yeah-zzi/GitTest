package yeji.mjc.gittest.comunity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import yeji.mjc.gittest.FB_IMG_Item;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class Ready extends AppCompatActivity implements FBListener {

    RecyclerView recyclerView;
    Button complete;
    public ArrayList<FBTabItem> items = new ArrayList<FBTabItem>();

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference readyDB, acceptDB, dateDB, removeDB,nowDB;

    String userid;

    Date d_fb_end, d_fb_now, d_fb_start;
    String get_end, get_start,get_now;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ready_add);

        userid = UserData.getInstance().getUserid();
        //userid = "2827271993";

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new ReadyAdapter(items, this));

        complete = findViewById(R.id.complete);

        //뒤 배경을 반투명하게 하려고
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true);
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
            window.setStatusBarColor(Color.parseColor("#80000000"));
        }
        overridePendingTransition(0, 0);
        


        //파이어베이스에 저장되어 있는 회원이 진행중인 집밥대결 정보들을 받아온다
        readyDB = database.getReference().child("user").child(userid).child("foodbattle_code");
        readyDB.addListenerForSingleValueEvent(new ValueEventListener() {
            //기존 배열리스트가 존재하지 않게 초기화
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                items.clear();   //기존 배열리스트가 존재하지 않게 초기화
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        FBTabItem item = snapshot1.getValue(FBTabItem.class);
                        if ("false".equals(item.getReady()) && "false".equals(item.getPropose())) {
                            checkDate(item);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemClicked(FBTabItem myModel) {

        Calendar now = Calendar.getInstance();
        //문자열로 날짜를 초기화
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        //현재 시각을 받아온다
        now.setTime(new Date());
        d_fb_now = now.getTime();
        get_now = df.format(d_fb_now);
        //집밥대결의 시작 날짜를 조정한다
        dateDB = database.getReference().child("foodbattle").child(myModel.getCode());
        dateDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                get_end = snapshot.child("fb_end_date").getValue(String.class);
                get_start = snapshot.child("fb_start_date").getValue(String.class);

                try {
                    d_fb_end = df.parse(get_end);
                    d_fb_start = df.parse(get_start);

                    nowDB = dateDB.child("fb_start_date");
                    nowDB.setValue(get_now);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //TODO.집밥대결 시작 날짜를 오늘로 바꾸고 종료날짜가 이미 지났으면 대결을 삭제
        //사용자한테 집밥대결 코드 수락
        acceptDB = database.getReference().child("user").child(userid).child("foodbattle_code").child(myModel.getCode()).child("ready");
        acceptDB.setValue("true");
        //친구한테서 집밥대결 코드 수락
        acceptDB = database.getReference().child("user").child(myModel.getFb_friend()).child("foodbattle_code").child(myModel.getCode()).child("ready");
        acceptDB.setValue("true");

        //사용자한테 집밥대결 코드 수락
        acceptDB = database.getReference().child("user").child(userid).child("foodbattle_code").child(myModel.getCode()).child("propose");
        acceptDB.setValue("true");
        //친구한테서 집밥대결 코드 수락
        acceptDB = database.getReference().child("user").child(myModel.getFb_friend()).child("foodbattle_code").child(myModel.getCode()).child("propose");
        acceptDB.setValue("true");

        finish();
    }

    public void checkDate(FBTabItem item) {
        if (item != null) {
            Calendar now = Calendar.getInstance();
            //문자열로 날짜를 초기화
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            //현재 시각을 받아온다
            now.setTime(new Date());
            d_fb_now = now.getTime();
            //집밥대결의 시작 날짜를 조정한다
            dateDB = database.getReference().child("foodbattle").child(item.getCode());
            dateDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    get_end = snapshot.child("fb_end_date").getValue(String.class);
                    get_start = snapshot.child("fb_start_date").getValue(String.class);

                    try {
                        if (get_end != null && get_start != null) {
                            d_fb_end = df.parse(get_end);
                            d_fb_start = df.parse(get_start);
                        }
                        long diffDate = (d_fb_end.getTime() - d_fb_now.getTime()) / 1000; //초 차이
                        if (diffDate <= 0) {
                            //Toast.makeText(getActivity(),"작음",Toast.LENGTH_SHORT).show();
                            dateDB.removeValue();
                            removeDB = database.getReference().child("user").child(userid).child("foodbattle_code").child(item.getCode());
                            removeDB.removeValue();
                            removeDB = database.getReference().child("user").child(item.getFb_friend()).child("foodbattle_code").child(item.getCode());
                            removeDB.removeValue();
                        }else{
                            items.add(item);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}
