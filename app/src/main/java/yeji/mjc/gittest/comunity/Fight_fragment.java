package yeji.mjc.gittest.comunity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import yeji.mjc.gittest.FB_IMG_Item;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;


public class Fight_fragment extends Fragment implements FBListener{

    String userid ;
    TextView user_name,friend_name;
    TextView remainDate,ingDate,startDateText;
    Button meNum,friendNum;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference fbdDB,selectDB,userDB,imgDB;

    //리사이클러뷰에 추가할 아이템 리스트
    public ArrayList<FBTabItem> items = new ArrayList<FBTabItem>();
    public ArrayList<FB_IMG_Item> userimg_items = new ArrayList<FB_IMG_Item>();
    public ArrayList<FB_IMG_Item> friendimg_items = new ArrayList<FB_IMG_Item>();

    RecyclerView recyclerView,user_recyclerview,friend_recyclerview;

    //유저의 이미지
    String userImg,friendImg,userName,friendName;
    CircleImageView viewUserImg,viewFriendImg;

    //선택된 집밥대결 코드
    String fb_code;
    //선택된 집밥대결 날짜
    Date d_fb_end,d_fb_now,d_fb_start;
    String fb_end,fb_start;
    //집밥대결 날짜 차이
    long end_diffDays,start_diffDays;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fight_main, container, false);

        userid = UserData.getInstance().getUserid();
        userImg = UserData.getInstance().getUserimg();
        userName = UserData.getInstance().getUsername();

        user_name = view.findViewById(R.id.Me);
        friend_name = view.findViewById(R.id.Friend);
        remainDate = view.findViewById(R.id.ending);
        ingDate = view.findViewById(R.id.food_battle_range);
        viewUserImg = view.findViewById(R.id.cat);
        viewFriendImg = view.findViewById(R.id.dog);
        startDateText = view.findViewById(R.id.ing);
        meNum = view.findViewById(R.id.MeNum);
        friendNum = view.findViewById(R.id.FriendNum);

        recyclerView = view.findViewById(R.id.tbTabrecycler);
        recyclerView.setHasFixedSize(true);

        //리사이클러뷰에 매니저와 어댑터를 연결
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new FBTabAdapter(items,this));

        //유저 이미지 리사이클러뷰에 매니저와 어댑터를 연결
        user_recyclerview = view.findViewById(R.id.userImgZone1);
        user_recyclerview.setHasFixedSize(true);
        user_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        user_recyclerview.setAdapter(new FB_IMG_Adapter(userimg_items));

        //userimg_items.add(new FB_IMG_Item("https://k.kakaocdn.net/dn/hnRyZ/btseTDCs7y7/K9J1dG5DjWzjgI6khav2LK/img_640x640.jpg","2023/05/23"));

        //친구 이미지 리사이클러뷰에 매니저와 어댑터를 연결
        friend_recyclerview = view.findViewById(R.id.userImgZone2);
        friend_recyclerview.setHasFixedSize(true);
        friend_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        friend_recyclerview.setAdapter(new FB_IMG_Adapter(friendimg_items));


        //파이어베이스에 저장되어 있는 회원이 진행중인 집밥대결 정보들을 받아온다
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

        Glide.with(getActivity()).load(userImg).into(viewUserImg);
        user_name.setText(userName);
        return view;
    }


    @Override
    public void onItemClicked(FBTabItem myModel) {
        friend_name.setText(myModel.fb_friend);
        fb_code = myModel.code;
        friendImg = myModel.fb_friend_img;
        Glide.with(getActivity()).load(friendImg).into(viewFriendImg);

        startDateText.setText("대결 중");

        Calendar now = Calendar.getInstance();

        selectDB = database.getReference().child("foodbattle");
        selectDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fb_end = snapshot.child(fb_code).child("fb_end_date").getValue(String.class);
                fb_start = snapshot.child(fb_code).child("fb_start_date").getValue(String.class);


                //유저 이미지 올리는 리사이클러뷰
                userimg_items.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot2 : snapshot.child(fb_code).child("fb_mem").child(userid).child("upload_info").getChildren()){
                    FB_IMG_Item item_user = snapshot2.getValue(FB_IMG_Item.class);
                    userimg_items.add(item_user);
                }
                user_recyclerview.getAdapter().notifyDataSetChanged();

                //친구 이미지 올리는 리사이클러뷰
                friendimg_items.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot2 : snapshot.child(fb_code).child("fb_mem").child(myModel.fb_friend).child("upload_info").getChildren()){
                    FB_IMG_Item item_friend = snapshot2.getValue(FB_IMG_Item.class);
                    friendimg_items.add(item_friend);
                }
                friend_recyclerview.getAdapter().notifyDataSetChanged();

                meNum.setText(userimg_items.size()+"번");
                friendNum.setText(friendimg_items.size()+"번");

                //문자열을 날짜로 변환
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                //현재 날짜로부터 집밥대결 끝나는 날까지 얼마나 남았는지 계산
                try {
                    //현재 날짜를 시작날짜 문자열에 저장
                    now.setTime(new Date());
                    d_fb_now = now.getTime();
                    d_fb_end = df.parse(fb_end);
                    d_fb_start = df.parse(fb_start);

                    //종료 날짜로부터 지금기준으로 몇일 차이 나느가
                    long end_diffSec = (d_fb_end.getTime() - d_fb_now.getTime()) / 1000; //초 차이
                    end_diffDays = end_diffSec / (24*60*60) + 1; //일자수 차이
                    remainDate.setText(end_diffDays+"일 뒤 대결이 종료 됩니다");
                    //시작 날짜로부터 지금기준으로 몇일 차이 나느가
                    long start_diffSec = (d_fb_now.getTime() - d_fb_start.getTime()) / 1000; //초 차이
                    start_diffDays = start_diffSec / (24*60*60) + 1; //일자수 차이
                    ingDate.setText(start_diffDays+"일째");

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}