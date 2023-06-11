package yeji.mjc.gittest.frige;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import yeji.mjc.gittest.R;

public class Select_date extends AppCompatActivity {

    TextView selectedDateText;
    CalendarView calendarView;
    Button complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_expiry);

        selectedDateText = findViewById(R.id.selectedDateText);
        calendarView = findViewById(R.id.calendarView);
        complete = findViewById(R.id.complete);

        // 현재 날짜로 초기화
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        selectedDateText.setText(dateFormat.format(calendar.getTime()));

        // MaterialDatePicker 생성
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("유통기한 선택");
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());
        builder.setCalendarConstraints(constraintsBuilder.build());
        MaterialDatePicker<Long> materialDatePicker = builder.build();

        // MaterialDatePicker가 닫힐 때 선택된 날짜 처리
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selectedDate) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.setTimeInMillis(selectedDate);
                        selectedDateText.setText(dateFormat.format(selectedCalendar.getTime()));
                    }
                }
        );

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선택된 날짜 가져오기
                String selectedDate = selectedDateText.getText().toString();

                // FridgePlus 액티비티로 이동하는 인텐트 생성
                Intent intent = new Intent(Select_date.this, FridgePlus.class);
                intent.putExtra("DEADLINE", selectedDate);
                startActivity(intent);
                finish();
            }
        });

        // MaterialDatePicker를 표시
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
    }
}
