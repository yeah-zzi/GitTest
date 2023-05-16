package yeji.mjc.gittest.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.AllergyItem;
import yeji.mjc.gittest.FoodSearch.Allergy;
import yeji.mjc.gittest.MainActivity;
import yeji.mjc.gittest.R;

public class Register extends AppCompatActivity {

    ImageButton startBtn;
    Button allergyBtn,nameBtn;
    EditText edit_name;

    public RecyclerView registerallergyrecyclerView;
    public ArrayList<AllergyItem> allergyitems = new ArrayList<AllergyItem>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //알러지 레퍼런스 가져ㅗ기
    DatabaseReference allergyDB,userdb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerallergyrecyclerView = findViewById(R.id.allergyregister);
        registerallergyrecyclerView.setHasFixedSize(true);


        registerallergyrecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        registerallergyrecyclerView.setAdapter(new RegisterAllergyAdapter(allergyitems));

        startBtn = findViewById(R.id.startbutton);
        allergyBtn = findViewById(R.id.allergybtn);
        edit_name = findViewById(R.id.edittextnickname);
        nameBtn = findViewById(R.id.idbuttion);


        nameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString();
                userdb = database.getReference().child("userid").child("user_name");
                userdb.setValue(name);
            }
        });

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

    @Override
    protected void onStart() {
        super.onStart();

        allergyDB = database.getReference().child("userid").child("allergy");
        allergyDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                allergyitems.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    AllergyItem allergyItem = snapshot1.getValue(AllergyItem.class);
                    allergyitems.add(allergyItem);
                }
                registerallergyrecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });
    }
}
