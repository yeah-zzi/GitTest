package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import yeji.mjc.gittest.FoodSearch.Allergy;
import yeji.mjc.gittest.FoodSearch.FridgePlus;

public class Register extends AppCompatActivity {

    ImageButton startBtn;
    Button allergyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        startBtn = findViewById(R.id.startbutton);
        allergyBtn = findViewById(R.id.allergybtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Register.this, MainActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        allergyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Register.this, Allergy.class);
                startActivity(registerIntent);
            }
        });
    }
}
