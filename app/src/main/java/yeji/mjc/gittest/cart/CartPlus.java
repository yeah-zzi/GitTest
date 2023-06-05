package yeji.mjc.gittest.cart;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kakao.sdk.user.model.User;

import java.io.File;
import java.util.ArrayList;

import yeji.mjc.gittest.FoodBattle_IMG_Upload;
import yeji.mjc.gittest.FoodSearch.FoodSearch;
import yeji.mjc.gittest.FoodSearch.FoodSearchItem;
import yeji.mjc.gittest.Model;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.SelectListener;
import yeji.mjc.gittest.UserData;

public class CartPlus extends AppCompatActivity {

    //카메라를 위한 변수들
    private static final int CAMERA_PERMISSION_CODE = 1;
    ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;
    public ArrayList<String> code_item = new ArrayList<String>();

    //변수 선언
    ImageButton plusBTN,cancelBTN,completeBTN,calendarBTN;
    TextView foodName;
    ImageView foodImg;

    TextView foodCount;

    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference cartDB,realData;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference(); // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정
    String userid,name,count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_plus);

        //로그인 시 아이디값 변수 받아오기
        userid = UserData.getInstance().getUserid();

        //cartDB=database.getReference().child("user").child(userid).child("cart");

        plusBTN = findViewById(R.id.plusbtn);
        cancelBTN = findViewById(R.id.cancel);
        completeBTN = findViewById(R.id.complete);
        //calendarBTN = findViewById(R.id.calendar);
        foodName = findViewById(R.id.search_food_name);
        foodImg = findViewById(R.id.food_img);
        foodCount=findViewById(R.id.search_food_count);

        imageUri = createUri();
        registerPictureLauncher();


        //취소버튼을 누르면 dialog창이 닫힌다
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //확인 버튼을 누르면 dialog창이 닫힌다
        completeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파이어베이스에 정보를 저장한다
                name = foodName.getText().toString();
                count = foodCount.getText().toString();
                cartDB = database.getReference().child("user").child(userid).child("cart").child(name);
                cartDB.child("food_name").setValue(name);
                cartDB.child("food_count").setValue(count);
                uploadToFirebase(imageUri);
                finish();
            }
        });

        //상품추가 버튼을 누르면 상품추가 dialog창이 뜬다
        plusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeSearchIntent = new Intent(getApplicationContext(), FoodSearch.class);
                startActivity(fridgeSearchIntent);
                startActivityForResult(fridgeSearchIntent,3);
            }
        });

        //이미지를 누르면 카메라로 이동
        foodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO밑에
                checkCameraPermissionOpenCamera();
            }
        });


    }

    //팝업창을 종료할때 하단으로 내려가는 애니메이션 효과를 제거하는 함수
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                name = data.getStringExtra("이름");
                int img = data.getIntExtra("이미지", 0);
                imageUri = getURLForResource(img);


                foodName.setText(name);
                foodImg.setImageResource(img);
                }
            }
        }

    private void checkCameraPermissionOpenCamera() {
        if (ActivityCompat.checkSelfPermission(CartPlus.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CartPlus.this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            takePictureLauncher.launch(imageUri);
        }
    }

    private void registerPictureLauncher() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        try {
                            if (result) {
                                foodImg.setImageURI(null);
                                foodImg.setImageURI(imageUri);
                            }
                        } catch (Exception exception) {
                            exception.getStackTrace();
                        }
                    }
                }
        );
    }

    private Uri createUri() {
        File imageFile = new File(getApplicationContext().getFilesDir(), "camera_photo.jpg");
        return FileProvider.getUriForFile(
                getApplicationContext(),
                "yeji.mjc.gittest.fileProvider",
                imageFile
        );
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

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + imageUri);
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        cartDB.child("food_img").setValue(uri.toString());
                        for (int i = 0; i < code_item.size(); i++) {
                            String code = code_item.get(i);
                            //cartDB.child("food_img").setValue(uri.toString());
                        }
                    }
                });
            }
        });
    }

    private Uri getURLForResource(int resId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resId);
    }

    }




