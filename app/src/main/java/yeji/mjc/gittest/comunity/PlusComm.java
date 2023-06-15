package yeji.mjc.gittest.comunity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import yeji.mjc.gittest.FoodBattle_IMG_Upload;
import yeji.mjc.gittest.MainActivity;
import yeji.mjc.gittest.Model;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class PlusComm extends AppCompatActivity {

    ImageButton cancel,kind,complete;
    TextView com_kind;
    ImageView image;
    EditText title,content;

    String com_name,select_com_name;
    Uri imageUri;

    String com_title,com_content;
    String com_code;

    String userid;
    String username,userimg;
    public ArrayList<String> code_item = new ArrayList<String>();
    Calendar now = Calendar.getInstance();
    String today;

    //파이어베이스 설정
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference comDB,userDB;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference(); // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_community);

        userid = UserData.getInstance().getUserid();
        userimg = UserData.getInstance().getUserimg();
        username = UserData.getInstance().getUsername();

        cancel = findViewById(R.id.cancel);
        kind = findViewById(R.id.com_kind_btn);
        com_kind = findViewById(R.id.com_kind);
        image = findViewById(R.id.plus_img);
        complete = findViewById(R.id.complete);
        title = findViewById(R.id.title);
        content = findViewById(R.id.plus_content);

        //문자열로 날짜를 초기화
        DateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");

        //현재 날짜를 시작날짜 문자열에 저장
        now.setTime(new Date());
        today = df.format(now.getTime());


        //현재 선택되어 있는 커뮤니티 종류 이름을 받아온다
        com_name = (String) com_kind.getText();

        //파이어베이스의 경로 조정
        comDB = database.getReference().child("life").push();
        //생성된 게시물의 코드를 변수에 저장
        com_code = comDB.getKey();

        //취소 버튼을 누를 시 종료
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //커뮤니티 종류 선택 버튼
        kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kindIntent = new Intent(getApplicationContext(), Comm_kind.class);
                kindIntent.putExtra("커뮤니티 종류", com_name);
                startActivityForResult(kindIntent, 2);
            }
        });

        //사진 추가를 누를시
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        //완료버튼을 누른다
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com_title = title.getText().toString();
                com_content = content.getText().toString();

                if(com_title.equals(null) || com_title.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"제목을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(com_content.equals(null) || com_content.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"내용을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(imageUri == null){
                    Toast.makeText(getApplicationContext(),"이미지를 선택해 주세요",Toast.LENGTH_SHORT).show();
                }else{
                    //파이어베이스에 데이터 올리기
                    comDB.child("title").setValue(com_title);
                    comDB.child("content").setValue(com_content);
                    comDB.child("like").setValue("0");
                    comDB.child("comment_count").setValue("0");
                    comDB.child("com_code").setValue(com_code);
                    comDB.child("writer").setValue(username);
                    comDB.child("writer_img").setValue(userimg);
                    comDB.child("date").setValue(today);
                    uploadToFirebase(imageUri);

                    //선택한 게시판에 따라 유저가 작성한 게시글의 코드 경로가 달라진다
                    if(com_name.equals("생활정보")){
                        userDB = database.getReference().child("user").child(userid).child("life_post").push();
                        userDB.setValue(com_code);
                    }else if(com_name.equals("이거어때")){
                        userDB = database.getReference().child("user").child(userid).child("tip_post").push();
                        userDB.setValue(com_code);
                    }

                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                select_com_name = data.getStringExtra("커뮤니티 종류");
                if (select_com_name != null) {
                    //Toast.makeText(this, select_com_name, Toast.LENGTH_SHORT).show();
                    com_kind.setText(select_com_name);
                    com_name = select_com_name;

                    //파이어베이스의 경로 조정
                    if(select_com_name.equals("생활정보")){
                        comDB = database.getReference().child("life").push();
                        com_code = comDB.getKey();
                    }else if(select_com_name.equals("이거어때")){
                        comDB = database.getReference().child("tip").push();
                        com_code = comDB.getKey();
                    }
                }
            }
        }else if(requestCode == 1){
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                image.setImageURI(imageUri);
            }
        }
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + imageUri);
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //이미지 모델에 담기
                        comDB.child("post_img").setValue(uri.toString());

                    }
                });
            }
        });
    }
}
