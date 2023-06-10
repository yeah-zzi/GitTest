package yeji.mjc.gittest.frige;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;
import yeji.mjc.gittest.comunity.FightGo;

public class Select_date extends AppCompatActivity {

    ImageButton close, calendar;

    String startDate; //오늘 날짜
    String lastDate; //유통기한 끝나는 날짜
    String userid; //로그인한 유저 아이디


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expiry_date);

        //뒤 배경을 반투명하게 설정
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true);
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
            window.setStatusBarColor(Color.parseColor("#80000000"));
        }
        overridePendingTransition(0, 0);

        calendar = findViewById(R.id.calendar);

        userid = UserData.getInstance().getUserid();

        Calendar now = Calendar.getInstance();

        //문자열로 날짜를 초기화
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        //현재 날짜를 시작날짜 문자열에 저장
        now.setTime(new Date());
        startDate = df.format(now.getTime());


        close = findViewById(R.id.complete);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastDate == null) {
                    Toast.makeText(getApplicationContext(), "유통기한이 끝나는 날짜를 선택하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent dateIntent = new Intent(getApplicationContext(), FightGo.class);
                    dateIntent.putExtra("유저", userid);
                    dateIntent.putExtra("오늘날짜", startDate);
                    dateIntent.putExtra("유통기한 끝나는 날짜", lastDate);
                    startActivity(dateIntent);

                    finish();
                }
            }
        });

        //오늘 날짜
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        // 달력 버튼을 누를 때
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker().setTheme(com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
                builder.setTitleText("집밥 대결을 진행할 기간을 선택해 주세요");

                // 날짜 선택 제약 조건 설정
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
                constraintsBuilder.setValidator(DateValidatorPointForward.now());

                builder.setCalendarConstraints(constraintsBuilder.build());

                // 첫 번째 날짜 범위 설정
                builder.setSelection(Pair.create(today, today));

                MaterialDatePicker materialDatePicker = builder.build();

                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                // 확인 버튼
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        Date startD = new Date();
                        Date endD = new Date();

                        startD.setTime(selection.first);
                        endD.setTime(selection.second);

                        startDate = df.format(startD);
                        lastDate = df.format(endD);

                    }
                });
            }
        });
    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
