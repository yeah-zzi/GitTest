package yeji.mjc.gittest.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CalloutBalloonAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yeji.mjc.gittest.R;

public class Cart_Map extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener{

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
    private Button btn_mart, btn_convenience, btn_market, btn_supermarket;  // 마트, 편의점, 시장, 대형마트 검색 버튼
    private TextView tv_pageNumber;
    private ConstraintLayout resultLayout;
    private ImageButton btn_locationIn, btn_locationOut;

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

        btn_locationIn = findViewById(R.id.location_in);
        btn_locationOut = findViewById(R.id.location_out);

        listAdapter.setItemClickListener(new MapListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(listItems.get(position).getY()), Double.parseDouble(listItems.get(position).getX()));
                mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true);
                // 리스트 내의 검색된 아이템을 클릭하면 자동으로 현위치 이동되지 않도록 해줌 (TrackingModeOff)
                // 검색 장소로 화면 이동
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                btn_locationIn.setVisibility(View.INVISIBLE);
                btn_locationOut.setVisibility(View.VISIBLE); // 현위치 out 버튼 활성화

            }
        });

        //검색 위치로 화면이동 시(location_out), 버튼 클릭이벤트로 현위치로 화면 이동
        btn_locationOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                btn_locationOut.setVisibility(View.INVISIBLE);
                btn_locationIn.setVisibility(View.VISIBLE); // 다시 현위치 버튼 활성화
            }
        });

        btn_search = findViewById(R.id.btn_search);
        btn_prevPage = findViewById(R.id.btn_prevPage);
        btn_nextPage = findViewById(R.id.btn_nextPage);
        tv_pageNumber = findViewById(R.id.tv_pageNumber);

        et_search_field = findViewById(R.id.et_search_field);
        resultLayout = findViewById(R.id.result_Layout);

        btn_mart = findViewById(R.id.btn_mart);
        btn_convenience = findViewById(R.id.btn_convenience);
        btn_market = findViewById(R.id.btn_market);
        btn_supermarket = findViewById(R.id.btn_supermarket);

        // 마트 버튼 클릭리스너
        btn_mart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch(btn_mart.getText().toString());
            }
        });
        // 편의점 버튼 클릭리스너
        btn_convenience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch(btn_convenience.getText().toString());
            }
        });
        // 시장 버튼 클릭리스너
        btn_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch(btn_market.getText().toString());
            }
        });
        // 대형마트 버튼 클릭리스너
        btn_supermarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch(btn_supermarket.getText().toString());
            }
        });

        // 사용자가 직접 검색창에 검색어 입력 시
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = et_search_field.getText().toString();
                pageNumber = 1;
                //mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(currentLatitude),Double.parseDouble(currentLatitude));
                //MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

                searchKeyword(keyword, pageNumber,currentLatitude, currentLongitude);
                resultLayout.setVisibility(View.VISIBLE);
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
        // 커스텀 말풍선 등록
        mapView.setCalloutBalloonAdapter(new CustomBalloonAdapter(getLayoutInflater()));
        // 마커 클릭 이벤트 리스너 등록
        mapView.setPOIItemEventListener(this);
    }

    // 검색어 버튼 클릭시 메소드
    private void goSearch (String searchPlace) {
        keyword = searchPlace; // 버튼의 텍스트로 검색어를 받아옴
        et_search_field.setText(searchPlace);
        pageNumber = 1;
        searchKeyword(keyword, pageNumber,currentLatitude, currentLongitude);
        resultLayout.setVisibility(View.VISIBLE);
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

                // setUserObject()를 사용하기 위해 주소와 전화번호를 Object 형태로 저장
                Map<String, Object> customData = new HashMap<>();
                customData.put("address",document.getAddress_name());
                customData.put("phone",document.getPhone());

                point.setItemName(document.getPlace_name());
                point.setMapPoint(MapPoint.mapPointWithGeoCoord(
                        Double.parseDouble(document.getY()),
                        Double.parseDouble(document.getX())));
                point.setUserObject(customData); // setUserObject()는 Object형태로 값을 보내야 됨
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
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        // 마커 클릭 시
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        // 말풍선 클릭 시
        // 이 함수도 작동하지만 밑에 있는 함수에서 작성
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem poiItem, MapPOIItem.CalloutBalloonButtonType buttonType) {
        // 말풍선 클릭 시
        String ph="";

        // Object 형태로 받아온 주소와 전화번호를 각 키(address, phone) 값으로 추출
        Object addressPhone = poiItem.getUserObject();
        if(addressPhone instanceof Map){
            Map<?,?> map = (Map<?, ?>) addressPhone;
            ph = (String) map.get("phone");
            if(ph.length() == 0)
                ph = "";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] itemList = {ph, "취소"};
        builder.setTitle(poiItem.getItemName());
        String telPhone = ph;
        builder.setItems(itemList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if(!telPhone.equals("")){
                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+ telPhone));
                            startActivity(myIntent);
                        }
                        break;
                    case 1:
                        dialog.dismiss();   // 대화상자 닫기
                        break;
                }
            }
        });
        builder.show();
    }

    // 커스텀 말풍선 클래스
    private class CustomBalloonAdapter implements CalloutBalloonAdapter {
        private View mCalloutBalloon;
        private TextView name, address, phone;
        private String add = "", ph = "";   // getUserObject()에서 받아온 주소와 전화번호 저장

        @SuppressLint("LongLogTag")
        public CustomBalloonAdapter(LayoutInflater inflater){
            mCalloutBalloon = inflater.inflate(R.layout.cart_map_balloon, null);
            name = mCalloutBalloon.findViewById(R.id.ball_tv_name);
            address = mCalloutBalloon.findViewById(R.id.ball_tv_address);
            phone = mCalloutBalloon.findViewById(R.id.ball_tv_phone);
            Log.d("test : 1. BalloonAdapter",mCalloutBalloon+"");
        }

        // 마커 클릭 시 나오는 말풍선
        @SuppressLint("LongLogTag")
        @Override
        public View getCalloutBalloon(MapPOIItem poiItem){

            // Object 형태로 받아온 주소와 전화번호를 각 키(address, phone) 값으로 추출
            Object addressPhone = poiItem.getUserObject();
            if(addressPhone instanceof Map){
                Map<?,?> map = (Map<?, ?>) addressPhone;
                add = (String) map.get("address");
                ph = (String) map.get("phone");
            }

            Log.d("test : 2. getCalloutBalloon getUserObject",poiItem.getUserObject().toString());
            Log.d("test : 2. getCalloutBalloon 주소", add);
            Log.d("test : 2. getCalloutBalloon 전화번호", ph);
            name.setText(poiItem != null ? poiItem.getItemName() : ""); // 해당 마커의 정보 이용 가능
            address.setText(poiItem != null ? add : "");
            phone.setText(poiItem != null ? ph : "");

            return mCalloutBalloon;
        }

        // 말풍선 클릭 시
        @Override
        public View getPressedCalloutBalloon(MapPOIItem mapPOIItem) {
            return mCalloutBalloon;
        }
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
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
    // 맵 한번 클릭하면
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        resultLayout.setVisibility(View.GONE); // 검색 리스트 안보여지게 함
    }
    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
    }
    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
    }
    @Override
    // 맵 위로 스와이프하면
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        resultLayout.setVisibility(View.VISIBLE); // 검색 리스트 다시 보여지게 함
    }
    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }
    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}
