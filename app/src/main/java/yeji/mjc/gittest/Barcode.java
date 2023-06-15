package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

import yeji.mjc.gittest.frige.FridgePlus;

public class Barcode extends AppCompatActivity {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    public String barcode;


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


        //연속해서 바코드 스캔
        barcodeScannerView.decodeContinuous(new BarcodeCallback() {  // BarcodeCallback >> 바코드 스캔 결과처리
            @Override
            public void barcodeResult(BarcodeResult result) {
                String barcode = readBarcode(result.toString());
                Intent intent = new Intent(Barcode.this, FridgePlus.class);
                intent.putExtra("barcode", barcode);
                startActivity(intent);
                finish();
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

    public String readBarcode(String barcode) {

        //Toast.makeText(Barcode.this, barcode, Toast.LENGTH_SHORT).show();
        return barcode;
    }
}
