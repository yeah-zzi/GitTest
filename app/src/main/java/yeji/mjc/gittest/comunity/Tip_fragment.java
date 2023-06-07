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

import java.util.ArrayList;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;


public class Tip_fragment extends Fragment implements SelectListener{

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_tip;
    public ArrayList<TipItem> tipItems = new ArrayList<TipItem>();

    ImageView heartBtn;
    TextView heartText;

    String userid,code,heartClick;
    int like_size;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tipDB,heartClickDB,likeDB,heartDB,userHeartDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tip_fragment, container, false);
        recyclerView = view.findViewById(R.id.tipRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TipAdapter(getActivity(),tipItems,this));
        // Inflate the layout for this fragment

        userid = UserData.getInstance().getUserid();

        recyclerView.setHasFixedSize(true);

        //파이어베이스에 저장되어 있는 이거어때 게시글을 불러온다
        tipDB = database.getReference().child("tip");
        tipDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                tipItems.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    TipItem tipitem = snapshot1.getValue(TipItem.class);
                    tipItems.add(tipitem);
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
        Intent comIntent = new Intent(getActivity(), TipComment.class);
        Toast.makeText(getActivity(),tip_fragment.getTitle(),Toast.LENGTH_SHORT).show();
        String code = tip_fragment.com_code;
        comIntent.putExtra("커뮤니티 코드",code);
        comIntent.putExtra("작성자",tip_fragment.getWriter());
        comIntent.putExtra("내용",tip_fragment.getContent());
        comIntent.putExtra("이미지",tip_fragment.getPost_img());
        comIntent.putExtra("제목",tip_fragment.getTitle());
        comIntent.putExtra("좋아요",tip_fragment.getLike());
        comIntent.putExtra("댓글수",tip_fragment.getComment_count());
        comIntent.putExtra("작성자이미지",tip_fragment.getWriter_img());
        startActivity(comIntent);
    }

    @Override
    public void onHeartClicked(TipItem tip_fragment, ImageView imageView, TextView textView) {
        heartBtn = imageView;
        heartText = textView;

        code = tip_fragment.getCom_code();
        String getLike = tip_fragment.getLike();
        like_size = Integer.valueOf(getLike);

        //유저가 하트를 눌렀는지 불린 가져오기
        heartDB = database.getReference().child("tip").child(code).child("like_user");
        heartDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                heartClick = "unclick";
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(userid.equals(dataSnapshot.getKey())){
                        heartClick = dataSnapshot.getValue(String.class);
                        break;
                    }
                }

                // onDataChange 메소드가 호출된 이후에 처리되어야 하는 로직 호출
                handleHeartClick();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 오류 처리
            }
        });

        //현재 하트상태에 따라 불값 바꾸기
        heartClickDB = database.getReference().child("tip").child(code).child("like_user").child(userid);
        likeDB = database.getReference().child("tip").child(code).child("like");
        userHeartDB = database.getReference().child("user").child(userid).child("heart_tip").child(code);

    }

    private void handleHeartClick() {
        if(heartClick.equals("click")){
            like_size--;
            heartBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            heartClick = "unclick";
            heartClickDB.setValue("unclick");
            userHeartDB.removeValue();
        }else{
            like_size++;
            heartBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
            heartClick = "click";
            heartClickDB.setValue("click");
            userHeartDB.setValue(code);
        }
        heartText.setText(like_size+"");
        likeDB.setValue(like_size+"");
    }

}