package yeji.mjc.gittest.FoodSearch;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import yeji.mjc.gittest.R;

public class Food_expiry extends AppCompatActivity {
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_expiry);

        //뒤 배경을 반투명하게
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true);
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
            window.setStatusBarColor(Color.parseColor("#80000000"));
        }
        overridePendingTransition(0,0);

        calendar = findViewById(R.id.calendar);


        /*
        day3 = findViewById(R.id.days3);
        day7 = findViewById(R.id.days7);
        day30 = findViewById(R.id.days30);

        //전페이지에서 유저 아이디를 가져옴
        Intent dateIntent = getIntent();
        userid = dateIntent.getStringExtra("유저");

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
        }); */

    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
