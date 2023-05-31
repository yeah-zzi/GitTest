package yeji.mjc.gittest;


import android.Manifest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;

import yeji.mjc.gittest.comunity.FBTabItem;
import yeji.mjc.gittest.comunity.FriendAdd_Item;
import yeji.mjc.gittest.comunity.Tip_comment_item;


public class FoodBattle_IMG_Upload extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 1;
    ImageView imageView;
    ImageButton btnCamera, btnGallery;
    Button cancel, btnUpload;

    ActivityResultLauncher<Uri>  takePictureLauncher;
    Uri imageUri;

    public ArrayList<String> code_item = new ArrayList<String>();

    Calendar now = Calendar.getInstance();

    String userid = "임시용 유저 아이디1";
    String today;
    private  static final int REQUEST_IMAGE_CODE = 101;

    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference fb_upload,codeDB,ref;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference(); // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodbattle_img_upload);

        imageView = findViewById(R.id.image_view);
        btnCamera = findViewById(R.id.camera);
        btnGallery = findViewById(R.id.gallery);
        cancel = findViewById(R.id.cancel_btn);
        btnUpload = findViewById(R.id.upload_btn);

        imageUri = createUri();
        registerPictureLauncher();

        //오늘 날짜를 가져온다
        //문자열로 날짜를 초기화
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        //현재 날짜를 시작날짜 문자열에 저장
        now.setTime(new Date());
        today = df.format(now.getTime());


        //파이어베이스에서 유저의 집밥대결 코드를 가져와 배열에 넣는다
        codeDB = database.getReference().child("user").child(userid).child("foodbattle_code");
        codeDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot friend_data : snapshot.getChildren()){
                    FBTabItem getItem = friend_data.getValue(FBTabItem.class);
                    String code = getItem.getCode();
                    code_item.add(code);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //파이어베이스 실시간 데이터베이스에 저장될 경로를 지정한다
        fb_upload = database.getReference().child("foodbattle");

        //취소 버튼 누를시 화면이 닫힘
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //카메라 버튼을 누를시 사진 찰영을 할 수 있음
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermissionOpenCamera();
            }
        });

        //업로드 버튼을 누를 시
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    uploadToFirebase(imageUri);
                    finish();
                }else{
                    Toast.makeText(FoodBattle_IMG_Upload.this,"사진을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis()+"."+imageUri);
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //이미지 모델에 담기
                        Model model = new Model(uri.toString());

                        for(int i=0;i<code_item.size();i++){
                            String code = code_item.get(i);
                            ref = fb_upload.child(code_item.get(i)).child("fb_mem").child(userid).child("upload_info").push();
                            ref.child("upload_img").setValue(uri.toString());
                            ref.child("upload_time").setValue(today);
                        }

                        Toast.makeText(FoodBattle_IMG_Upload.this,"업로드 성공",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


            private Uri createUri() {
                File imageFile = new File(getApplicationContext().getFilesDir(), "camera_photo.jpg");
                return FileProvider.getUriForFile(
                        getApplicationContext(),
                        "yeji.mjc.gittest.fileProvider",
                        imageFile
                );
            }

            private void registerPictureLauncher() {
                takePictureLauncher = registerForActivityResult(
                        new ActivityResultContracts.TakePicture(),
                        new ActivityResultCallback<Boolean>() {
                            @Override
                            public void onActivityResult(Boolean result) {
                                try {
                                    if (result) {
                                        imageView.setImageURI(null);
                                        imageView.setImageURI(imageUri);
                                    }
                                } catch (Exception exception) {
                                    exception.getStackTrace();
                                }
                            }
                        }
                );
            }

            private void checkCameraPermissionOpenCamera() {
                if (ActivityCompat.checkSelfPermission(FoodBattle_IMG_Upload.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FoodBattle_IMG_Upload.this,
                            new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                } else {
                    takePictureLauncher.launch(imageUri);
                }
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (requestCode == CAMERA_PERMISSION_CODE) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        takePictureLauncher.launch(imageUri);
                    } else {
                        Toast.makeText(this, "카메라 권한이 거부 되었습니다. 권한을 허용해 주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    }


