package yeji.mjc.gittest.mypage;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import yeji.mjc.gittest.FoodSearch.AllergyAdapter;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.LoginActivity;
import yeji.mjc.gittest.UserData;
import yeji.mjc.gittest.comunity.FBTabItem;
import yeji.mjc.gittest.register.RegisterAllergyAdapter;

public class MyPagesujin extends Fragment {
    Button logoutBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allergyDB, fbdDB;
    public String userid = UserData.getInstance().getUserid();

    private ImageView profile, myprofile, profile1, profile2;
    private TextView username, userAllergyInfo;
    String userN="", userA="";

    //리사이클러뷰 선언 및 리사이클러뷰에 넣을 아이템 선언
    public RecyclerView allergyrecyclerView;
    public ArrayList<MyPageAllergyItem> allergyitems = new ArrayList<MyPageAllergyItem>();


    @Override
    public void setEnterTransition(@Nullable Object transition) {
        super.setEnterTransition(transition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_pagesujin, container, false);
        View User = view.findViewById(R.id.User);
        allergyrecyclerView = view.findViewById(R.id.allergyrecyclerview);
        allergyrecyclerView.setHasFixedSize(true);
        //리사이클러뷰에 매니저와 어댑터를 연결
        allergyrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        allergyrecyclerView.setAdapter(new MyPageAdapter(allergyitems));

        //View Friend = view.findViewById(R.id.Friend);
        View Post = view.findViewById(R.id.post);
        //View Tutorial = view.findViewById(R.id.Tutorial);

        logoutBtn = view.findViewById(R.id.logoutButton);
        //로그아웃 버튼 기능 부여
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 유저의 토큰 값 삭제 됨
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class); // 다시 로그인 화면으로 돌아감
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)); // 뒤로 가기 눌러도 로그아웃 전으로 못 돌아가게 설정
                        return null;
                    }
                });
            }
        });

        profile = view.findViewById(R.id.profile); // 유저 프로필 사진 담길 곳
        myprofile = view.findViewById(R.id.myprofile);
        username = view.findViewById(R.id.username); // 유저 닉네임 담길 곳 (임시로 이름)
        userAllergyInfo = view.findViewById(R.id.userAllergyInfo); // 유저 닉네임 넣어 수정할 텍스트 뷰

        profile1 = view.findViewById(R.id.profile1); // 대결하고 있는 상대1,2 프로필 담길 곳
        profile2 = view.findViewById(R.id.profile2);

        //Glide 라이브러리 사용하여 이미지 circlecrop(원형)으로 넣기
        Glide.with(this).load(UserData.getInstance().getUserimg()).circleCrop().into(profile);
        Glide.with(this).load(UserData.getInstance().getUserimg()).circleCrop().into(myprofile);

        userN = UserData.getInstance().getUsernickname();
        userA = UserData.getInstance().getUsername()+"님의 알러지 정보 ";
        username.setText(userN); // 유저 이름 설정
        userAllergyInfo.setText(userA);



        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mpcontainer, new UserInfoChange()).commit();
                return;
            }
        });

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mpcontainer, new Mypage_Post()).commit();
                return;
            }
        });

        //파이어베이스에 저장되어 있는 회원의 알러지 정보를 받아 해당하는 알러지를 recyclerview로 나타낸다
        allergyDB = database.getReference().child("user").child(userid).child("allergy");
        allergyDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                allergyitems.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    MyPageAllergyItem mypageallergyItem = snapshot1.getValue(MyPageAllergyItem.class);
                    allergyitems.add(mypageallergyItem);
                }
                allergyrecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });

        //파이어베이스에 저장되어 있는 회원의 진행중인 집밥대결 정보 중 대결친구 이미지 정보를 받아온다 (최대 2명 표시)
        fbdDB = database.getReference().child("user").child(userid).child("foodbattle_code").child("fb_friend_img");
        fbdDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                if(snapshot.exists()){
                    int count=0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        String friend_img ="";
                        friend_img = snapshot1.getValue(String.class);
                        if(friend_img != ""){
                            count++;
                            if(count==1) Glide.with(getContext()).load(friend_img).circleCrop().into(profile1);
                            else if(count==2) Glide.with(getContext()).load(friend_img).circleCrop().into(profile2);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }
}