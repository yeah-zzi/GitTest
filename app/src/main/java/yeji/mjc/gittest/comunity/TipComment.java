package yeji.mjc.gittest.comunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

import de.hdodenhof.circleimageview.CircleImageView;
import yeji.mjc.gittest.R;

public class TipComment extends Activity {

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public ArrayList<Tip_comment_item> items = new ArrayList<Tip_comment_item>();

    String code,writer,content,post_img,title,getLike,getCommentCount,writer_img;

    CircleImageView writerImg;
    TextView writer_id,post_content,like_count,comment_count;
    ImageView tip_post_img,like;
    EditText add_comment_content;
    ImageButton comment_add;

    String comment_content,comment_code;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tipDB,commentDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_comment);
        recyclerView = findViewById(R.id.tip_comment_recycler);
        recyclerView.setHasFixedSize(true);

        Intent getIntent = getIntent();
        code = getIntent.getStringExtra("커뮤니티 코드");
        writer = getIntent.getStringExtra("작성자");
        content = getIntent.getStringExtra("내용");
        post_img = getIntent.getStringExtra("이미지");
        title = getIntent.getStringExtra("제목");
        getLike = getIntent.getStringExtra("좋아요");
        getCommentCount = getIntent.getStringExtra("댓글수");
        writer_img = getIntent.getStringExtra("작성자이미지");

        writer_id = findViewById(R.id.writer_id);
        post_content = findViewById(R.id.content);
        like_count = findViewById(R.id.like_count);
        comment_count = findViewById(R.id.comment_count);
        writerImg = findViewById(R.id.writer);
        tip_post_img = findViewById(R.id.tip_post_img);
        comment_add = findViewById(R.id.comment_add);
        add_comment_content = findViewById(R.id.comment_add_content);

        writer_id.setText(writer);
        post_content.setText(content);
        like_count.setText(getLike);
        comment_count.setText(getCommentCount);
        Glide.with(this).load(post_img).into(tip_post_img);
        Glide.with(this).load(writer_img).into(writerImg);

        comment_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_content = add_comment_content.getText().toString();
                add_comment_content.setText(null);
                //댓글작성자이미지,작성자이름,작성내용
                tipDB = database.getReference().child("tip").child(code).child("comment").push();
                comment_code = tipDB.getKey();
                commentDB = tipDB.child("comment_writer_id");
                commentDB.setValue(writer);
                commentDB = tipDB.child("comment_writer_img");
                commentDB.setValue(writer_img);
                commentDB = tipDB.child("comment_content");
                commentDB.setValue(comment_content);
                commentDB = tipDB.child("comment_code");
                commentDB.setValue(comment_code);
            }
        });
    }


    public void onStart(){
        super.onStart();

        //파이어베이스에 저장되어 있는 회원의 알러지 정보를 받아 해당하는 알러지를 recyclerview로 나타낸다
        tipDB = database.getReference().child("tip").child(code).child("comment");
        tipDB.addValueEventListener(new ValueEventListener() {
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
        recyclerView.setAdapter(new TipCommentAdapter(items));
    }
}