package yeji.mjc.gittest.FoodSearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import yeji.mjc.gittest.R;

public class FridgePlus extends AppCompatActivity {

    //변수 선언
    ImageButton plusBTN,cancelBTN,completeBTN,calendarBTN;
    TextView foodName,foodCount,deadLine;
    ImageView foodImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_plus);

        plusBTN = findViewById(R.id.plusbtn);
        cancelBTN = findViewById(R.id.cancel);
        completeBTN = findViewById(R.id.complete);
        calendarBTN = findViewById(R.id.calendar);
        foodName = findViewById(R.id.search_food_name);
        foodImg = findViewById(R.id.food_img);

        //취소버튼을 누르면 dialog창이 닫힌다
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //확인 버튼을 누르면 dialog창이 닫힌다
        completeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //상품추가 버튼을 누르면 상품추가 dialog창이 뜬다
        plusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeSearchIntent = new Intent(getApplicationContext(), FoodSearch.class);
                startActivity(fridgeSearchIntent);
            }
        });

        String productName = getIntent().getStringExtra("productName");
        foodName.setText(productName);
    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
