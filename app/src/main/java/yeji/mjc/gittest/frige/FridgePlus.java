package yeji.mjc.gittest.frige;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import yeji.mjc.gittest.FoodSearch.FoodSearch;
import yeji.mjc.gittest.R;

public class FridgePlus extends AppCompatActivity {

    ImageButton plusBTN, cancelBTN, completeBTN, calendarBTN;
    TextView foodName;
    ImageView foodImg;
    TextView foodCount;
    TextView deadLine;
    private String deadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_plus);

        plusBTN = findViewById(R.id.plusbtn);
        cancelBTN = findViewById(R.id.cancel);
        completeBTN = findViewById(R.id.complete);
        calendarBTN = findViewById(R.id.calendar);
        foodName = findViewById(R.id.search_food_name);
        foodImg = findViewById(R.id.food_img);
        deadLine = findViewById(R.id.search_food_dead);

        // Intent에서 deadline 값 받아오기
        Intent intent = getIntent();
        if (intent.hasExtra("DEADLINE")) {
            deadline = intent.getStringExtra("DEADLINE");
        }

        deadLine.setText(deadline);

        // 취소 버튼을 누르면 액티비티 종료
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 확인 버튼을 누르면 액티비티 종료
        completeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 상품 추가 버튼을 누르면 FoodSearch 액티비티로 이동
        plusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeSearchIntent = new Intent(getApplicationContext(), FoodSearch.class);
                startActivity(fridgeSearchIntent);
                startActivityForResult(fridgeSearchIntent, 3);

            }
        });

        // 선택한 상품의 이름 설정
        String productName = getIntent().getStringExtra("productName");
        foodName.setText(productName);

        // 캘린더 버튼을 누르면 Select_date 액티비티로 이동
        calendarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectdate = new Intent(getApplicationContext(), Select_date.class);
                startActivity(selectdate);
                finish();
            }
        });
    }

    // onActivityResult: FoodSearch 액티비티에서 결과를 받아올 때 호출되는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("이름");
                int img = data.getIntExtra("이미지", 0);

                foodName.setText(name);
                foodImg.setImageResource(img);
            }
        }
    }
}
