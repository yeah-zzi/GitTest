package yeji.mjc.gittest.comunity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import yeji.mjc.gittest.R;

public class Select_date_sub extends AppCompatActivity {

    ImageButton close,calendar;
    AppCompatButton day3,day7,day30;

    //현재시간 기준 선택한 기간에 따라 정해지는 집밥대결이 끝나는 날짜
    String changeDate = null;
    //집밥대결 시작 날짜
    String startDate;
    //로그인한 유저 아이디, 집밥대결 상대 아이디
    String userid,friendid,friendImg,friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_date);


        //뒤 배경을 반투명하게 하려고
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true);
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
            window.setStatusBarColor(Color.parseColor("#80000000"));
        }
        overridePendingTransition(0,0);

        day3 = findViewById(R.id.days3);
        day7 = findViewById(R.id.days7);
        day30 = findViewById(R.id.days30);
        calendar = findViewById(R.id.calendar);

        //전페이지에서 유저 아이디와 집밥대결 유저를 가져온다
        Intent dateIntent = getIntent();
        userid = dateIntent.getStringExtra("유저");
        friendid = dateIntent.getStringExtra("집밥대결 친구 아이디");
        friendName = dateIntent.getStringExtra("집밥대결 친구 이름");
        friendImg = dateIntent.getStringExtra("집밥대결 친구 이미지");


        Calendar now = Calendar.getInstance();

        //문자열로 날짜를 초기화
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        //현재 날짜를 시작날짜 문자열에 저장
        now.setTime(new Date());
        startDate = df.format(now.getTime());

        //3일을 선택하면
      day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 버튼만 색 변화
                day3.setBackground(getDrawable(R.drawable.radius_select));
                day7.setBackground(getDrawable(R.drawable.radius_noselect));
                day30.setBackground(getDrawable(R.drawable.radius_noselect));

                //현재 날짜 기준으로 더함
                now.setTime(new Date());
                now.add(Calendar.DATE,3);

                //바뀐 날짜를 문자열로 변환
                changeDate = df.format(now.getTime());
                Toast.makeText(getApplicationContext(),startDate,Toast.LENGTH_SHORT).show();
            }
        });

        //7일을 선택하면
        day7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 버튼만 색 변화
                day3.setBackground(getDrawable(R.drawable.radius_noselect));
                day7.setBackground(getDrawable(R.drawable.radius_select));
                day30.setBackground(getDrawable(R.drawable.radius_noselect));

                //현재 날짜 기준으로 더함
                now.setTime(new Date());
                now.add(Calendar.DATE,7);

                //바뀐 날짜를 문자열로 변환
                changeDate = df.format(now.getTime());
                Toast.makeText(getApplicationContext(),changeDate,Toast.LENGTH_SHORT).show();
            }
        });

        //30일을 선택하면
        day30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 버튼만 색 변화
                day3.setBackground(getDrawable(R.drawable.radius_noselect));
                day7.setBackground(getDrawable(R.drawable.radius_noselect));
                day30.setBackground(getDrawable(R.drawable.radius_select));

                //현재 날짜 기준으로 더함
                now.setTime(new Date());
                now.add(Calendar.MONTH,1);

                //바뀐 날짜를 문자열로 변환
                changeDate = df.format(now.getTime());
                Toast.makeText(getApplicationContext(),changeDate,Toast.LENGTH_SHORT).show();
            }
        });

        close = findViewById(R.id.complete);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeDate == null){
                    Toast.makeText(getApplicationContext(),"집밥대결을 진행할 기간을 선택해 주십시오",Toast.LENGTH_SHORT).show();
                }else{
                    Intent dateIntent = new Intent(getApplicationContext(), FightGo.class);
                    dateIntent.putExtra("유저",userid);
                    dateIntent.putExtra("집밥대결 친구 아이디",friendid);
                    dateIntent.putExtra("집밥대결 친구 이름",friendName);
                    dateIntent.putExtra("집밥대결 친구 이미지",friendImg);
                    dateIntent.putExtra("집밥대결 시작날짜",startDate);
                    dateIntent.putExtra("집밥대결 종료날짜",changeDate);
                    startActivity(dateIntent);

                    finish();
                }
            }
        });

        //오늘 날짜
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        //달력 버튼을 누를시
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialDatePicker.Builder<Pair<Long,Long>> builder = MaterialDatePicker.Builder.dateRangePicker().setTheme(com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
                builder.setTitleText("날짜를 선택");

                //미리 날짜 선택
                builder.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds()));

                MaterialDatePicker materialDatePicker = builder.build();

                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");

                //확인 버튼
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long,Long> selection) {
                        Date startD = new Date();
                        Date endD = new Date();

                        startD.setTime(selection.first);
                        endD.setTime(selection.second);

                        startDate = df.format(startD);
                        changeDate = df.format(endD);

                        Toast.makeText(getApplicationContext(),startDate+"이랑"+changeDate,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
