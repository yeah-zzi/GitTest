package yeji.mjc.gittest.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import yeji.mjc.gittest.FoodSearch.FridgePlus;
import yeji.mjc.gittest.MainActivity;
import yeji.mjc.gittest.R;

public class Bellset1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_pagesujin);

        FrameLayout user = findViewById(R.id.User);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user = new Intent(Bellset1.this, UserInfoChange.class);
                startActivity(user);
            }
        });

        FrameLayout bell = findViewById(R.id.Bell);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bellset = new Intent(Bellset1.this, Bellset.class);
                startActivity(bellset);
            }
        });


    }
}
