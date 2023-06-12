package yeji.mjc.gittest.frige;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import yeji.mjc.gittest.FoodSearch.FoodSearch;
import yeji.mjc.gittest.Product;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class FridgePlus extends AppCompatActivity {

    //카메라를 위한 변수들
    private static final int CAMERA_PERMISSION_CODE = 1;
    ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;
    public ArrayList<String> code_item = new ArrayList<String>();

    ImageButton plusBTN, cancelBTN, completeBTN, calendarBTN;
    EditText foodName;
    ImageView foodImg;
    EditText foodCount;
    TextView deadLine;
    Button cold,frozen;
    private String deadline;
    Date endD;

    //FireBase DB 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference cartDB,barcodedb,realData;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference = storage.getReference(); // 저장소 레퍼런스 객체 : storage 를 사용해 저장 위치를 설정
    String userid,startDate,changeDate,fridge_type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_plus);

        //뒤 배경을 반투명하게 하려고
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true);
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
            window.setStatusBarColor(Color.parseColor("#80000000"));
        }
        overridePendingTransition(0,0);

        //로그인 시 아이디값 변수 받아오기
        userid = UserData.getInstance().getUserid();

        plusBTN = findViewById(R.id.plusbtn);
        cancelBTN = findViewById(R.id.cancel);
        completeBTN = findViewById(R.id.complete);
        calendarBTN = findViewById(R.id.calendar);

        foodName = findViewById(R.id.search_food_name);
        foodImg = findViewById(R.id.food_img);
        foodCount=findViewById(R.id.search_food_count);
        deadLine = findViewById(R.id.search_food_dead);
        cold = findViewById(R.id.cold);
        frozen = findViewById(R.id.frozen);

        imageUri = createUri();
        registerPictureLauncher();
        Calendar now = Calendar.getInstance();

        //문자열로 날짜를 초기화
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        //현재 날짜를 시작날짜 문자열에 저장
        now.setTime(new Date());
        startDate = df.format(now.getTime());

        deadLine.setText(deadline);

        //음식이 냉장인지 냉동인지 입력
        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fridge_type = "cold";
                cold.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.yellow));
                frozen.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightgray));
            }
        });

        frozen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fridge_type = "frozen";
                cold.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightgray));
                frozen.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.yellow));
            }
        });


        // 취소 버튼을 누르면 액티비티 종료
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        // 확인 버튼을 누르면 액티비티 종료
        completeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = foodName.getText().toString();
                String count = foodCount.getText().toString();
                String deadline = deadLine.getText().toString();

                if (name.isEmpty() || count.isEmpty() || deadline.isEmpty() || fridge_type.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "식재료 정보를 모두 입력하세요!", Toast.LENGTH_SHORT).show();
                    return; // completeBTN 버튼이 눌리지 않도록 리턴
                }else{
                    cartDB = database.getReference().child("user").child(userid).child("fridge").child(name);
                    cartDB.child("food_name").setValue(name);
                    cartDB.child("food_count").setValue(count);
                    cartDB.child("fridge_type").setValue(fridge_type);
                    cartDB.child("food_date").setValue(changeDate);
                    uploadToFirebase(imageUri);
                    // 입력값이 유효한 경우에만 액티비티 종료
                    finish();
                }
            }
        });

        // 상품 추가 버튼을 누르면 FoodSearch 액티비티로 이동
        plusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeSearchIntent = new Intent(getApplicationContext(), FoodSearch.class);
                startActivity(fridgeSearchIntent);
                startActivityForResult(fridgeSearchIntent, 3);

            }
        });

        // 선택한 상품의 이름 설정
        String productName = getIntent().getStringExtra("productName");
        foodName.setText(productName);

        //스캔하여 얻은 값
        String scannedBarcode = getIntent().getStringExtra("barcode");
        if(scannedBarcode != null ) {
            // 파이어베이스 데이터베이스의 "Product" 경로에서 바코드 값을 가져옴
            barcodedb = database.getReference().child("Product").child("barcode").child(scannedBarcode);
            // 스캔한 바코드 값과 파이어베이스 안에 있는 바코드 값이 같으면 불러냄
            //Query query = barcodedb.orderByChild("barcode").equalTo(scannedBarcode);
            barcodedb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Product product = snapshot.getValue(Product.class);
                        if (product != null) {
                            String name = product.getPRDT_NM() + " ";
                            foodName.setText((String) name);
                            String img = product.getImg();
                            Glide.with(getApplicationContext()).load(img).into(foodImg);
                        }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(FridgePlus.this, "ERROR!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //오늘 날짜
        long today = MaterialDatePicker.todayInUtcMilliseconds();

        // 캘린더 버튼을 누르면 Select_date 액티비티로 이동
        calendarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 날짜 선택 제약 조건 설정
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
                constraintsBuilder.setValidator(DateValidatorPointForward.now());

                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("유통기한을 선택해주세요").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setCalendarConstraints(constraintsBuilder.build()).build();

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        endD = new Date(selection);

                        changeDate = df.format(endD);

                        deadLine.setText(datePicker.getHeaderText());
                        Toast.makeText(getApplicationContext(), changeDate, Toast.LENGTH_SHORT).show();


                    }
                });

                datePicker.show(getSupportFragmentManager(),"Material_Date_Picker");

            }
        });
    }

    // onActivityResult: FoodSearch 액티비티에서 결과를 받아올 때 호출되는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("이름");
                int img = data.getIntExtra("이미지", 0);

                foodName.setText(name);
                foodImg.setImageResource(img);
            }
        }
    }

    private void checkCameraPermissionOpenCamera() {
        if (ActivityCompat.checkSelfPermission(FridgePlus.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FridgePlus.this,
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
