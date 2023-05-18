package yeji.mjc.gittest.comunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import yeji.mjc.gittest.FoodSearch.FoodSearch;
import yeji.mjc.gittest.R;


public class New_fight_sub extends AppCompatActivity {

    ImageButton close, bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fight);

        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt1 = findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeSearchIntent = new Intent(getApplicationContext(), Select_date_sub.class);
                startActivity(fridgeSearchIntent);
            }
        });
    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}