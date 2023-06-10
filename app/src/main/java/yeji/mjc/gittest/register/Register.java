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
import yeji.mjc.gittest.FoodItem;
import yeji.mjc.gittest.FoodSearch.Allergy;
import yeji.mjc.gittest.MainActivity;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class Register extends AppCompatActivity {

    ImageButton startBtn;
    Button allergyBtn,nameBtn;
    EditText edit_name;
    public String userid;


    //리사이클러뷰 선언 및 리사이클러뷰에 넣을 아이템 선언
    public RecyclerView registerallergyrecyclerView;
    public ArrayList<AllergyItem> allergyitems = new ArrayList<AllergyItem>();

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allergyDB,userdb,foodbattleDB,foodDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerallergyrecyclerView = findViewById(R.id.allergyregister);
        registerallergyrecyclerView.setHasFixedSize(true);

        //리사이클러뷰에 매니저와 어댑터를 연결
        registerallergyrecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        registerallergyrecyclerView.setAdapter(new RegisterAllergyAdapter(allergyitems));

        startBtn = findViewById(R.id.startbutton);
        allergyBtn = findViewById(R.id.allergybtn);
        edit_name = findViewById(R.id.edittextnickname);
        nameBtn = findViewById(R.id.idbuttion);

        //로그인 시 아이디값 받아오기
        userid = UserData.getInstance().getUserid();

        //닉네임 중복 확인 버튼을 누르면 쓰여있는 닉네임을 받아서 데이터베이스에 넣는다
        //TODO 데이터 중복 확인 구문 작성
        nameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString();
                userdb = database.getReference().child("user").child(userid).child("user_info").child("user_nickname");
                userdb.setValue(name);
            }
        });

        //시작하기 버튼을 누르면 main화면으로 이동한다
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //가입하는 회원의 집밥대결 횟수를 0으로 만든다
                int num = 1;
                foodbattleDB = database.getReference().child("user").child(userid).child("foodbattle_count");
                foodbattleDB.setValue(num);
                Intent registerIntent = new Intent(Register.this, MainActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        //알러지 선택 버튼을 누르면 알러지 선택 dialog창을 띄워준다
        allergyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Register.this, Allergy.class);
                registerIntent.putExtra("userId", userid);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //로그인 시 아이디값 변수 받아오기
        userid = UserData.getInstance().getUserid();

        //파이어베이스에 저장되어 있는 회원의 알러지 정보를 받아 해당하는 알러지를 recyclerview로 나타낸다
        allergyDB = database.getReference().child("user").child(userid).child("allergy");
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
