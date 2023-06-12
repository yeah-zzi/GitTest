package yeji.mjc.gittest.mypage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.LoginActivity;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;
import yeji.mjc.gittest.comunity.LifePost_Adapter;
import yeji.mjc.gittest.comunity.SelectListener;
import yeji.mjc.gittest.comunity.TipAdapter;
import yeji.mjc.gittest.comunity.TipItem;
import yeji.mjc.gittest.register.Register;

public class DeletePost extends Fragment implements SelectListener {

    public RecyclerView life_recyclerview, tip_recyclerview;
    private LifePost_Adapter life_adapter;
    public ArrayList<TipItem> list = new ArrayList<TipItem>();

    String userid, postType;
    RadioGroup radioGroup;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mylifeDB, lifeDB, mytipDB, tipDB, mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deletepost, container, false);

        //뒤로 가기 버튼 기능 구현
        View backbtn = view.findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.postcontainer, new Mypage_Post()).commit();
                return;
            }
        });

        // life 게시물 리사이클러뷰 객체 생성
        life_recyclerview = view.findViewById(R.id.lifepost_RecyclerView);
        life_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        life_adapter = new LifePost_Adapter(list, this);
        life_recyclerview.setAdapter(life_adapter);

        // tip 게시물 리사이클러뷰 객체 생성
        tip_recyclerview = view.findViewById(R.id.tippost_RecyclerView);
        tip_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        tip_recyclerview.setAdapter(new TipAdapter(getActivity(), list, this));

        userid = UserData.getInstance().getUserid();
        radioGroup = view.findViewById(R.id.choice_post);

        // 라디오 버튼 누르면 리사이클러뷰 뜨게 클릭이벤트
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.lifepost_rbtn:
                        //생활정보 라디오버튼 클릭시 tip리사이클러뷰 안보이게
                        life_recyclerview.setVisibility(View.VISIBLE);
                        tip_recyclerview.setVisibility(View.INVISIBLE);

                        //파이어베이스에 저장되어 있는 회원의 생활정보 작성 게시글 코드 확인
                        mylifeDB = database.getReference().child("user").child(userid).child("life_post");
                        mylifeDB.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                list.clear();
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    String mypostNum = snapshot1.getValue().toString(); // 변수에 코드 저장
                                    postType = "life";
                                    Log.d(mypostNum, "내가 올린 생활정보 게시물 넘버 : "+mypostNum);
                                    //comparePostData(mypostNum, postType);
                                    showmypost(postType, mypostNum);
                                }
                                life_recyclerview.getAdapter().notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                //디비를 가져오다 오류 발생시
                            }
                        });
                        break;

                    case R.id.tippost_rbtn:
                        //이거어때 라디오버튼 클릭시 life리사이클러뷰 안보이게
                        tip_recyclerview.setVisibility(View.VISIBLE);
                        life_recyclerview.setVisibility(View.INVISIBLE);

                        //파이어베이스에 저장되어 있는 회원의 이거어때 작성 게시글 코드 확인
                        mytipDB = database.getReference().child("user").child(userid).child("tip_post");
                        mytipDB.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                list.clear();
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    String mypostNum = snapshot1.getValue().toString(); // 변수에 코드 저장
                                    postType = "tip";
                                    Log.d(mypostNum, "내가 올린 이거어때 게시물 넘버 : "+mypostNum);
                                    //comparePostData(mypostNum, postType);
                                    showmypost(postType, mypostNum);
                                }
                                tip_recyclerview.getAdapter().notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                //디비를 가져오다 오류 발생시
                            }
                        });
                        break;
                }
            }
        });

        return view;
    }
//// 나의 게시물 코드와 게시물을 비교해 파이어베이스 게시물 데이터를 받아온다
//    private void comparePostData(String mypostNum, String postType) {
//        if(postType=="life"){
//            lifeDB = database.getReference().child(postType).child(mypostNum);
//            lifeDB.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    list.clear();
//                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                        String post = snapshot1.getValue().toString();
//                        Log.d(post, "생활정보 게시물 넘버 : "+post);
//                        if(post.equals(mypostNum)){
//                            showmypost(postType, mypostNum);
//                        }
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        else if (postType=="tip"){
//            tipDB = database.getReference().child(postType).child(mypostNum);
//            tipDB.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    list.clear();
//                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                        String post = snapshot1.getValue().toString();
//                        Log.d(post, "이거어때 게시물 넘버 : "+post);
//                        if(post.equals(mypostNum)){
//                            showmypost(postType, mypostNum);
//                        }
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//    }

// 내가 쓴 게시물 코드와 일치하는 정보만 담아 리사이클러뷰에 보여지게 하는 메소드
    private void showmypost(String postType, String mypostNum) {
        mDatabase = FirebaseDatabase.getInstance().getReference(postType);
        Query myTopPostsQuery = mDatabase.orderByChild("com_code").equalTo(mypostNum);
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    TipItem tipitem = snapshot1.getValue(TipItem.class);
                    list.add(tipitem);
                }
                if(postType=="life"){
                    life_recyclerview.getAdapter().notifyDataSetChanged();
                }
                else if(postType=="tip"){
                    tip_recyclerview.getAdapter().notifyDataSetChanged();
                }

                Log.w("MainActivity", "List = "+list.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "onCancelled");
            }
        });
    }

    @Override
    public void onItemClicked(TipItem tip_fragment) {
        String deletePostCode = tip_fragment.getCom_code();
        DialogInterface.OnClickListener confirm = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatabase.child(deletePostCode).removeValue();
                Log.d("postType", "postType = "+postType);
                if(postType=="life") {
                    lifeDB = database.getReference().child("user").child(userid).child("life_post").child(deletePostCode);
                    lifeDB.removeValue();
                    life_recyclerview.getAdapter().notifyDataSetChanged();
                }
                else if(postType=="tip") {
                    mytipDB.child(deletePostCode).removeValue();
                    tip_recyclerview.getAdapter().notifyDataSetChanged();
                }
                Toast.makeText(getActivity(), "게시물 삭제를 완료했습니다!", Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().replace(R.id.mpcontainer, new DeletePost()).commit();
                return;
            }
        };
        DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        };
        new AlertDialog.Builder(getActivity()).setTitle("삭제하시겠습니까?")
                .setPositiveButton("삭제", confirm)
                .setNegativeButton("취소", cancle).show();

    }


    @Override
    public void onHeartClicked(TipItem tip_fragment, ImageView imageView, TextView textView) {

    }
}
