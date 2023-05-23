package yeji.mjc.gittest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

import yeji.mjc.gittest.FoodSearch.FoodSearch;
import yeji.mjc.gittest.FoodSearch.FridgePlus;
import yeji.mjc.gittest.firebase.Product;

public class Barcode extends AppCompatActivity {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Barcodedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode);

        barcodeScannerView = (DecoratedBarcodeView) findViewById(R.id.barcode);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(this.getIntent(), savedInstanceState);
        capture.decode(); // 바코드 인식 시작

        Barcodedb = database.getReference().child("Product"); // 바코드 가져오기

        //연속해서 바코드 스캔
        barcodeScannerView.decodeContinuous(new BarcodeCallback() {  // BarcodeCallback >> 바코드 스캔 결과처리
            @Override
            public void barcodeResult(BarcodeResult result) {
                readBarcode(result.toString()); // 바코드 스캔 결과 출력
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
                BarcodeCallback.super.possibleResultPoints(resultPoints);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    public void readBarcode(String barcode){

        // "products" 경로에서 해당 바코드의 제품 정보를 읽음
        Barcodedb.child("CD_NO").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 제품 정보를 가져와서 원하는 작업을 수행
                if (dataSnapshot.exists()) {
                    // 제품 이름과 이미지 경로를 가져옴
                    Product productName = dataSnapshot.child("PRDT_NM").getValue(Product.class);
                    AllergyItem image = dataSnapshot.child("Img").getValue(AllergyItem.class);


                } else {
                    // 해당 바코드의 제품 정보가 없을 때
                    Toast.makeText(Barcode.this, "해당 바코드의 제품 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 읽기가 취소되었을 때 처리할 작업을 수행
                Log.e("Firebase", "Failed to read product: " + databaseError.getMessage());
            }
        });

        Intent intent = new Intent(this, FridgePlus.class);
        intent.putExtra("productName", "안녕");
        startActivity(intent);
    }

}
