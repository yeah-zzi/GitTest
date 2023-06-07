package yeji.mjc.gittest.comunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class Life_Fragment extends Fragment implements SelectListener{

    private ArrayList<TipItem> list = new ArrayList<TipItem>(); //리사이클러뷰에 추가할 아이템 리스트
    private LifePost_Adapter adapter; // 어뎁터
    private RecyclerView recyclerView;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference lifeDB;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_life, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.life_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LifePost_Adapter(list,this);
        recyclerView.setAdapter(adapter);

        //파이어베이스에 저장되어 있는 회원의 알러지 정보를 받아 해당하는 알러지를 recyclerview로 나타낸다
        lifeDB = database.getReference().child("life");
        lifeDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                list.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    TipItem tipitem = snapshot1.getValue(TipItem.class);
                    list.add(tipitem);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });

        return view;
    }


    @Override
    public void onItemClicked(TipItem tip_fragment) {
        Intent comIntent = new Intent(getActivity(), LifeCommnet.class);
        String code = tip_fragment.com_code;
        comIntent.putExtra("커뮤니티 코드",code);
        comIntent.putExtra("작성자",tip_fragment.getWriter());
        comIntent.putExtra("내용",tip_fragment.getContent());
        comIntent.putExtra("이미지",tip_fragment.getPost_img());
        comIntent.putExtra("제목",tip_fragment.getTitle());
        comIntent.putExtra("좋아요",tip_fragment.getLike());
        comIntent.putExtra("댓글수",tip_fragment.getComment_count());
        comIntent.putExtra("작성자이미지",tip_fragment.getWriter_img());
        comIntent.putExtra("작성날짜",tip_fragment.getDate());
        startActivity(comIntent);
    }

    @Override
    public void onHeartClicked(TipItem tip_fragment, ImageView imageView, TextView textView) {

    }


}

