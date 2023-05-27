package yeji.mjc.gittest.comunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.AllergyItem;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.register.RegisterAllergyAdapter;


public class Fight_fragment extends Fragment implements FBListener{

    String userid = "임시용 유저 아이디1";
    TextView user_name,friend_name;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference fbdDB,makeDB;

    //리사이클러뷰에 추가할 아이템 리스트
    public ArrayList<FBTabItem> items = new ArrayList<FBTabItem>();

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fight_main, container, false);

        user_name = view.findViewById(R.id.Me);
        friend_name = view.findViewById(R.id.Friend);

        recyclerView = view.findViewById(R.id.tbTabrecycler);
        recyclerView.setHasFixedSize(true);

        //리사이클러뷰에 매니저와 어댑터를 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new FBTabAdapter(items,this));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //파이어베이스에 저장되어 있는 회원의 알러지 정보를 받아 해당하는 알러지를 recyclerview로 나타낸다
        fbdDB = database.getReference().child("user").child(userid).child("foodbattle_code");
        fbdDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                items.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    FBTabItem item = snapshot1.getValue(FBTabItem.class);
                    items.add(item);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });

        user_name.setText(userid);
    }

    @Override
    public void onItemClicked(FBTabItem myModel) {
        friend_name.setText(myModel.fb_friend);
    }
}