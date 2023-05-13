package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import yeji.mjc.gittest.FoodSearch.FridgePlus;

public class Register extends AppCompatActivity {

    ImageButton startBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        startBtn = findViewById(R.id.startbutton);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Register.this, MainActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });
    }
}
