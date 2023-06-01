package yeji.mjc.gittest.comunity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.R;



public class New_fight_sub extends AppCompatActivity implements FriendListener {
    private  FriendAdd_Adapter friendAdd_adapter;
    ImageButton close, next;
    EditText editText;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference friendDB,makeDB;

    //리사이클러뷰에 추가할 아이템 리스트
    public ArrayList<FriendAdd_Item> friendaddItems = new ArrayList<FriendAdd_Item>();
    //검색시 같은 이름이 있는 아이템이 담길 리스트
    public ArrayList<FriendAdd_Item> search_Items = new ArrayList<FriendAdd_Item>();

    String userid = "임시용 유저 아이디1";
    String friendid = "";
    String friendName;
    String friendImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fight);

        editText = findViewById(R.id.edt1);

        RecyclerView recyclerView = findViewById(R.id.friend_add_recyclerview);

        //파이어베이스에서 유저 정보를 가져와 검색될 아이템리스트에 저장한다
        friendDB = database.getReference().child("user");
        friendDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot friend_data : snapshot.getChildren()){
                    FriendAdd_Item getItem = friend_data.child("user_info").getValue(FriendAdd_Item.class);
                    friendaddItems.add(getItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //새로만들어질 집밥대결 키
        makeDB = database.getReference().child("foodbattle").push();

        //닫는 버튼
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //다음페이지 넘어가는 버튼
        next = findViewById(R.id.bt1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friendid == ""){
                    Toast.makeText(getApplicationContext(),"집밥대결 상대를 선택해 주십시오",Toast.LENGTH_SHORT).show();
                }else{
                    Intent fridgeSearchIntent = new Intent(getApplicationContext(), Select_date_sub.class);
                    fridgeSearchIntent.putExtra("유저",userid);
                    fridgeSearchIntent.putExtra("집밥대결 친구 아이디",friendid);
                    fridgeSearchIntent.putExtra("집밥대결 친구 이름",friendName);
                    fridgeSearchIntent.putExtra("집밥대결 친구 이미지",friendImg);
                    startActivity(fridgeSearchIntent);
                    finish();
                }
            }
        });

        //editText 리스너 작성
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            //s문자열이 start위치로부터 count길이만큼이 after길이로 변경되려고 한다는 내용을 전달해주면서 호출된다.
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            //s가 start위치로부터 count길이만큼 변경되었다는 것을 알려준다. 이전 문자열에서 before길이만큼 바뀌었다는 것을 알려준다.
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            //s내의 어느 문자열이 변경되었다는 것을 알려주기 위해 호출된다. s를 변경할 시 다시 재호출이 되기 때문에 무한루프에 빠질 가능성이 있으니 주의해야 한다.
            public void afterTextChanged(Editable s) {
                String searchText = editText.getText().toString();
                search_Items.clear();

                if(searchText.equals("")){
                    friendAdd_adapter.setItems(friendaddItems);
                }
                else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < friendaddItems.size(); a++) {
                        if (friendaddItems.get(a).user_name.toLowerCase().contains(searchText.toLowerCase())) {
                            search_Items.add(friendaddItems.get(a));
                        }
                        friendAdd_adapter.setItems(search_Items);
                    }
                }
            }
        });

        //리사이클러뷰 설정
        //어댑터 설정
        friendAdd_adapter = new FriendAdd_Adapter(this,friendaddItems,this);
        recyclerView.setAdapter(friendAdd_adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClicked(FriendAdd_Item myModel) {
        friendName = myModel.getUser_name();
        friendid = myModel.getUserId();
        friendImg = myModel.getUser_img();
    }
}