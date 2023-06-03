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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import yeji.mjc.gittest.FoodBattle_IMG_Upload;
import yeji.mjc.gittest.MainActivity;
import yeji.mjc.gittest.Model;
import yeji.mjc.gittest.R;

public class PlusComm extends AppCompatActivity {

    ImageButton cancel,kind,complete;
    TextView com_kind;
    ImageView image;
    EditText title,content;

    String com_name,select_com_name;
    Uri imageUri;

    String com_title,com_content;

    String userid = "임시";
    public ArrayList<String> code_item = new ArrayList<String>();

    //파이어베이스 설정
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference comDB;
    DatabaseReference ref;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference(); // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_community);

        cancel = findViewById(R.id.cancel);
        kind = findViewById(R.id.com_kind_btn);
        com_kind = findViewById(R.id.com_kind);
        image = findViewById(R.id.plus_img);
        complete = findViewById(R.id.complete);
        title = findViewById(R.id.title);
        content = findViewById(R.id.plus_content);

        //현재 선택되어 있는 커뮤니티 종류 이름을 받아온다
        com_name = (String) com_kind.getText();

        //파이어베이스의 경로 조정
        comDB = database.getReference().child("life").push();

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

                if(com_title.equals(null) || com_title.equals("")){
                    Toast.makeText(getApplicationContext(),"제목을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(com_content.equals(null) || com_content.equals("")){
                    Toast.makeText(getApplicationContext(),"내용을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(imageUri == null){
                    Toast.makeText(getApplicationContext(),"이미지를 선택해 주세요",Toast.LENGTH_SHORT).show();
                }else{
                    //파이어베이스에 데이터 올리기
                    comDB.child("writer").setValue(userid);
                    comDB.child("title").setValue(com_title);
                    comDB.child("content").setValue(com_content);
                    comDB.child("like").setValue("0");
                    comDB.child("comment_count").setValue("0");
                    uploadToFirebase(imageUri);

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
                    Toast.makeText(this, select_com_name, Toast.LENGTH_SHORT).show();
                    com_kind.setText(select_com_name);
                    com_name = select_com_name;

                    //파이어베이스의 경로 조정
                    if(select_com_name.equals("생활정보")){
                        comDB = database.getReference().child("life").push();
                    }else if(select_com_name.equals("이거어때")){
                        comDB = database.getReference().child("tip").push();
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
