package yeji.mjc.gittest.comunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import yeji.mjc.gittest.R;

public class LifeCommnet extends Activity {

    String code,writer,getcontent,post_img,gettitle,getLike,getCommentCount,writer_img,date;

    //뷰에서 가져오기
    ImageView img;
    TextView title,info,heart_count,comment_count,content;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference lifeDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_plus);

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

        //인플레이트
        img = findViewById(R.id.main_picture);
        title = findViewById(R.id.title);
        info = findViewById(R.id.info);
        heart_count = findViewById(R.id.heart_count);
        comment_count = findViewById(R.id.comment_count);
        content = findViewById(R.id.text1);

        //게시글 내용 반영
        Glide.with(this).load(post_img).into(img);
        title.setText(gettitle);
        info.setText("작성자 : "+writer+" / 게시일 : "+date);
        content.setText(getcontent);
        heart_count.setText(getLike);
        comment_count.setText(getCommentCount);


    }
}
