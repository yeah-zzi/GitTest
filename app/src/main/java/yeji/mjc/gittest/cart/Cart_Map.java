package yeji.mjc.gittest.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yeji.mjc.gittest.R;

public class Cart_Map extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener{

    private static final String BASE_URL = "https://dapi.kakao.com/";
    private static final String API_KEY = "KakaoAK 70357dd8e199f5bba2ab2b9e05864431";   // REST API 키

    private static final String TAG = "Cart_Map";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};

    private MapListAdapter listAdapter;
    private ArrayList<MapListLayout> listItems = new ArrayList<>(); // 데이터를 담기 위한 array 리스트
    private RecyclerView recyclerView;

    private int pageNumber = 1;      // 검색 페이지 번호
    private String keyword = "";        // 검색 키워드
    private int radius = 1000;        // 검색 범위(1m 단위)
    private EditText et_search_field; // 텍스트 창
    private Button btn_search, btn_prevPage, btn_nextPage;          // 검색 버튼, 이전, 다음
    private TextView tv_pageNumber;

    private MapView mapView;
    private MapPoint mapPoint;
    private ViewGroup mapViewContainer;

    // 위도 경도
    private String currentLatitude;
    private String currentLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_map);

        recyclerView = findViewById(R.id.rv_list); // 리사이클러뷰 초기화
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new MapListAdapter(listItems);
        recyclerView.setAdapter(listAdapter);

        listAdapter.setItemClickListener(new MapListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(listItems.get(position).getY()), Double.parseDouble(listItems.get(position).getX()));
                mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true);
            }
        });

        btn_search = findViewById(R.id.btn_search);
        btn_prevPage = findViewById(R.id.btn_prevPage);
        btn_nextPage = findViewById(R.id.btn_nextPage);
        tv_pageNumber = findViewById(R.id.tv_pageNumber);

        et_search_field = findViewById(R.id.et_search_field);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = et_search_field.getText().toString();
                pageNumber = 1;
                //mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(currentLatitude),Double.parseDouble(currentLatitude));
                //MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

                searchKeyword(keyword, pageNumber,currentLatitude, currentLongitude);
            }
        });

        btn_prevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNumber--;
                tv_pageNumber.setText(Integer.toString(pageNumber));
                searchKeyword(keyword, pageNumber, currentLatitude, currentLongitude);
            }
        });
        btn_nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageNumber++;
                tv_pageNumber.setText(Integer.toString(pageNumber));
                searchKeyword(keyword, pageNumber, currentLatitude, currentLongitude);
            }
        });

        // 권한ID를 가져옵니다
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        int permission2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permission3 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        // 권한이 열려있는지 확인
        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED || permission3 == PackageManager.PERMISSION_DENIED) {
            // 마쉬멜로우 이상버전부터 권한을 물어본다
            if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                requestPermissions(
                        new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1000);
            }
            return;
        }

        // 지도 띄우기
        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_View);
        mapViewContainer.addView(mapView);
        // 현재 위치 이벤트 및 맵 이벤트 리스너 설정
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationEventListener(this);
        // 현재 위치 업데이트 시작
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }


    // 키워드 검색 함수
    private void searchKeyword(String keyword, int page, String lat, String lon) {
        Retrofit retrofit = new Retrofit.Builder()                  // Retrofit 구성
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KakaoAPI api = retrofit.create(KakaoAPI.class);                         // 통신 인터페이스를 객체로 생성
        Log.d("test ","lng : "+lat);
        Log.d("test ","lon : "+lon);
        Call<ResultSearchKeyword> call = api.getSearchKeyword(API_KEY, keyword, lat, lon, radius, page);   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(new Callback<ResultSearchKeyword>(){
            @Override
            public void onResponse(Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("test ","Body : "+response.body());
                Log.d("test ","Raw : "+response.raw());
                addItemsAndMarkers(response.body());
            }

            @Override
            public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: "+t.getMessage());
            }
        });
    }

    private void addItemsAndMarkers(ResultSearchKeyword searchResult) {
        if(searchResult != null && searchResult.getDocuments()!=null){
            listItems.clear(); // 리스트 초기화
            mapView.removeAllPOIItems(); // 지도의 마커 모두 제거
            for(Place document : searchResult.getDocuments()){
                MapListLayout item = new MapListLayout(
                        document.getPlace_name(),
                        document.getRoad_address_name(),
                        document.getAddress_name(),
                        document.getX(),
                        document.getY());
                listItems.add(item);

                MapPOIItem point = new MapPOIItem();

                point.setItemName(document.getPlace_name());
                point.setMapPoint(MapPoint.mapPointWithGeoCoord(
                        Double.parseDouble(document.getY()),
                        Double.parseDouble(document.getX())));
                point.setMarkerType(MapPOIItem.MarkerType.BluePin);
                point.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                mapView.addPOIItem(point);

            }
            listAdapter.notifyDataSetChanged();

            btn_nextPage.setEnabled(!searchResult.getMeta().getIs_end());  // 페이지가 더 있을 경우 다음 버튼 활성화
            btn_prevPage.setEnabled(pageNumber != 1);                  // 1페이지가 아닐 경우 이전 버튼 활성화
        }else{
            // 검색 결과 없음
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show();
        }
    }

    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (check_result == false) {
                finish();
            }
        }
    }

    // 현재 위치 업데이트 시 호출되는 콜백 메서드
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        Log.i(TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, v));

//        MapPoint currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
//        //이 좌표로 지도 중심 이동
//        mapView.setMapCenterPoint(currentMapPoint, true);
        // 현재 위치 좌표 가져오기
        currentLatitude = Double.toString(mapPointGeo.latitude);
        currentLongitude = Double.toString(mapPointGeo.longitude);

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }
    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }
    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }
    @Override
    public void onMapViewInitialized(MapView mapView) {

    }
    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }
    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }
    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }
    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }
    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }
    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }
    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }
    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}