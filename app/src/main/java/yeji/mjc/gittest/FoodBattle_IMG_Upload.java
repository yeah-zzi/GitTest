package yeji.mjc.gittest;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FoodBattle_IMG_Upload extends AppCompatActivity {

    private ImageView imageView;//불러온 이미지를 보여줄 이미지뷰
    private ProgressBar progressBar;//이미지가 불러올 동안 프로그레스바가 돌아간다

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference imgReference = database.getReference().child("foodbattle").child("fb_mem1").child("fb_img");
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    private  Uri imageUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodbattle_img_upload);

        //컴포넌트 객체에 담기
        Button uploadBtn = findViewById(R.id.upload_btn);
        progressBar = findViewById(R.id.progress_view);
        imageView = findViewById(R.id.image_view);

        Button cancel = findViewById(R.id.cancel_btn);

        //프로그래스바 숨기기
        progressBar.setVisibility(View.INVISIBLE);

        //취소 버튼 이벤트
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //이미지 클릭 이벤트
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/");
                activityResult.launch(galleryIntent);
            }
        });

        //업로드버튼 클릭 이벤트
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택한 이미지가 있다면
                if(imageUri != null){
                    uploadToFirebase(imageUri);
                }else{
                    Toast.makeText(FoodBattle_IMG_Upload.this,"사진을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //사진 가져오기
    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        imageUri = result.getData().getData();

                        imageView.setImageURI(imageUri);
                    }
                }
            });


    //파이어베이스 이미지 업로드
    private void uploadToFirebase(Uri uri){

        StorageReference fileRef = reference.child(System.currentTimeMillis()+"."+getFileExtension(uri));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //이미지 모델에 담기
                        Model model = new Model(uri.toString());

                        //키로 아이디 생성
                        String modelId = imgReference.push().getKey();

                        //데이터 넣기
                        imgReference.child(modelId).setValue(model);

                        //프로그래스바 숨김
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(FoodBattle_IMG_Upload.this,"업로드 성공",Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.tab_image);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //프로그래스바 보여주기
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //프로그래스바 숨기기
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(FoodBattle_IMG_Upload.this,"업로드 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //파일 타입 가져오기
    private String getFileExtension(Uri uri){
        ContentResolver cr =getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

}
