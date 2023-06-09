package yeji.mjc.gittest.comunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class LifeCommnet extends Activity implements CommentListener{

    String code,writer,getcontent,post_img,gettitle,getLike,getCommentCount,writer_img,date;

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public ArrayList<Tip_comment_item> items = new ArrayList<Tip_comment_item>();

    //뷰에서 가져오기
    ImageView img,like;
    TextView title,info,heart_count,comment_count,content;
    EditText comment_add_content;
    ImageButton comment_add;

    String comment_content,comment_code,heartClick;
    String userid,username,userimg;
    int comment_size,like_size;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference uploadDB,commentDB,countDB,lifeDB,heartDB,heartClickDB,likeDB,userHeartDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_plus);

        recyclerView = findViewById(R.id.life_commnet_recycler);
        recyclerView.setHasFixedSize(true);

        userid = UserData.getInstance().getUserid();
        username = UserData.getInstance().getUsername();
        userimg = UserData.getInstance().getUserimg();

        Intent getIntent = getIntent();
        code = getIntent.getStringExtra("커뮤니티 코드");
        getcontent = getIntent.getStringExtra("내용");
        post_img = getIntent.getStringExtra("이미지");
        gettitle = getIntent.getStringExtra("제목");
        getLike = getIntent.getStringExtra("좋아요");
        getCommentCount = getIntent.getStringExtra("댓글수");
        writer = getIntent.getStringExtra("작성자");
        writer_img = getIntent.getStringExtra("작성자이미지");
        date = getIntent.getStringExtra("작성날짜");

        //댓글수로 int로
        comment_size = Integer.valueOf(getCommentCount);
        like_size = Integer.valueOf(getLike);

        //인플레이트
        img = findViewById(R.id.main_picture);
        like = findViewById(R.id.like);
        title = findViewById(R.id.title);
        info = findViewById(R.id.info);
        heart_count = findViewById(R.id.like_count);
        comment_count = findViewById(R.id.comment_count);
        content = findViewById(R.id.text1);
        comment_add = findViewById(R.id.comment_add);
        comment_add_content = findViewById(R.id.comment_add_content);

        //게시글 내용 반영
        Glide.with(this).load(post_img).into(img);
        title.setText(gettitle);
        info.setText("작성자 : "+writer+" / 게시일 : "+date);
        content.setText(getcontent);
        heart_count.setText(getLike);
        comment_count.setText(getCommentCount);

        //유저가 하트를 눌렀는지 불린 가져오기
        heartDB = database.getReference().child("life").child(code).child("like_user");
        heartDB.addValueEventListener(new ValueEventListener() {
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

        //댓글 작성
        comment_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_content = comment_add_content.getText().toString();
                if(comment_content == null || comment_content.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"댓글 내용을 입력해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    comment_add_content.setText(null);
                    //댓글작성자이미지,작성자이름,작성내용
                    uploadDB = database.getReference().child("life").child(code).child("comment").push();
                    //생성된 댓글 코드
                    comment_code = uploadDB.getKey();
                    commentDB = uploadDB.child("comment_writer_id");
                    commentDB.setValue(username);
                    commentDB = uploadDB.child("comment_writer_img");
                    commentDB.setValue(userimg);
                    commentDB = uploadDB.child("comment_content");
                    commentDB.setValue(comment_content);
                    commentDB = uploadDB.child("comment_code");
                    commentDB.setValue(comment_code);

                    comment_size++;
                    getCommentCount = Integer.toString(comment_size);
                    countDB = database.getReference().child("life").child(code).child("comment_count");
                    countDB.setValue(getCommentCount);
                    comment_count.setText(getCommentCount);
                }
            }
        });

        userHeartDB = database.getReference().child("user").child(userid).child("heart_life").child(code);
        //좋아요 버튼 누르기
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 하트상태에 따라 불값 바꾸기
                heartClickDB = database.getReference().child("life").child(code).child("like_user").child(userid);
                likeDB = database.getReference().child("life").child(code).child("like");
                if(heartClick.equals("click")){
                    like_size--;
                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    heartClick = "unclick";
                    heartClickDB.setValue("unclick");
                    userHeartDB.removeValue();
                }else{
                    like_size++;
                    like.setImageResource(R.drawable.ic_baseline_favorite_24);
                    heartClick = "click";
                    heartClickDB.setValue("click");
                    userHeartDB.setValue(code);
                }
                heart_count.setText(like_size+"");
                likeDB.setValue(like_size+"");
            }
        });
    }

    public void onStart(){
        super.onStart();

        //파이어베이스에 저장되어 있는 댓글 정보를 가져와
        lifeDB = database.getReference().child("life").child(code).child("comment");
        lifeDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                items.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Tip_comment_item tipitem = snapshot1.getValue(Tip_comment_item.class);
                    items.add(tipitem);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Life_Comment_Adapter(items,this));
    }

    @Override
    public void onItemClicked(Tip_comment_item item) {
        String comment_Code = item.getComment_code();
        //Toast.makeText(this,getCommentCount,Toast.LENGTH_SHORT).show();
        lifeDB.child(comment_Code).removeValue();

        comment_size--;
        getCommentCount = Integer.toString(comment_size);
        countDB = database.getReference().child("life").child(code).child("comment_count");
        countDB.setValue(getCommentCount);
        comment_count.setText(getCommentCount);
    }

    private void handleHeartClick() {
        // 불러온 유저 하트 누른 Boolean으로 설정
        if(heartClick.equals("click")){
            like.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }
}
