package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import yeji.mjc.gittest.firebase.UserFirebase;
import yeji.mjc.gittest.register.Register;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private DatabaseReference mDatabase;
    public ImageButton loginButton;
    public String userId,userEmail,userName,userImg;

//    public Button logoutButton;
//    public TextView nickName;
//    public ImageView profile;

    //임시 시작버튼
    Button btn1, btn2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);

        loginButton = (ImageButton) findViewById(R.id.loginButton);
//        logoutButton = findViewById(R.id.logoutButton);
//        nickName = findViewById(R.id.nickName);
//        profile = findViewById(R.id.profile);

        //임시 시작버튼 연결
        btn1 = findViewById(R.id.start);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn2 = findViewById(R.id.start2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        //firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();


        // 유저 토큰 정보가 있다면, 자동로그인 하여 Main으로 넘어감
        UserApiClient.getInstance().accessTokenInfo((tokenInfo, error) -> {
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show();
                updateKakaoLoginUi();
            } else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show();

                updateKakaoLoginUi();
//                //전역변수에 현재 유저 정보 넣기
//                UserData.getInstance().setUserid(userId);
//                UserData.getInstance().setUserimg(userImg);
//                UserData.getInstance().setUsername(userName);

                Log.i(TAG, "토큰 정보 보기 성공" +
                        "\n회원번호: " + tokenInfo.getId() +
                        "\n만료시간: " + tokenInfo.getExpiresIn() + "초");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
            return null;
        });

        // 카카오로그인 토큰 발급
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if (oAuthToken != null) {
                    //로그인이 되었을 때 처리할 일들 (토큰 가져오기, 유효기간 지난 경우 토큰 갱신됨)
                    Log.i("user 토큰 발급 : ", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                }
                if (throwable != null) {
                    // 결과 오류 시, 처리할 부분
                    Log.w(TAG, "토큰 발급 오류 : " + throwable.getLocalizedMessage());
                }
                updateKakaoLoginUi();
                Intent loginIntent = new Intent(LoginActivity.this, Register.class);
                //loginIntent.putExtra("userId", userId);
                startActivity(loginIntent);
                finish();
                return null;
            }
        };

        // 로그인 버튼 클릭리스너
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("checkLogin", "성공");
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                    //카카오톡 설치, 카카오톡으로 로그인
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                    //카카오톡 미설치, 카카오계정으로 로그인
                }
            }
        });
    }

    //유저 정보 받아오는 메소드
    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                //로그인 되어 있는 경우
                if (user != null) {

                    userId = user.getId().toString();
                    userEmail = user.getKakaoAccount().getEmail().toString();
                    userName = user.getKakaoAccount().getProfile().getNickname().toString();
                    userImg = user.getKakaoAccount().getProfile().getProfileImageUrl().toString();

                    //전역변수에 현재 유저 정보 넣기
                    UserData.getInstance().setUserid(userId);
                    UserData.getInstance().setUserimg(userImg);
                    UserData.getInstance().setUsername(userName);

                    // 유저의 아이디
                    Log.d(TAG, "kakao 유저 id : " + userId);
                    // 유저의 어카운트정보에 이메일
                    Log.d(TAG, "kakao 유저 email : " + userEmail);
                    // 유저의 어카운트 정보의 프로파일에 닉네임
                    Log.d(TAG, "kakao 유저 닉네임 : " + userName);
                    // 유저의 이미지 URL을 불러옵니다.
                    Log.d(TAG, "kakao 유저 프사 url :  " + userImg);

                    //유저 정보 hashmap 만들기
                    HashMap userInfo = new HashMap<>();
                    userInfo.put("Name", userName);
                    userInfo.put("Email", userEmail);
                    userInfo.put("profileImg", userImg);

                    writeNewUser(userId, userName, userEmail, userImg);


//                    nickName.setText(user.getKakaoAccount().getProfile().getNickname()); // 받아온 닉네임
//                    Glide.with(profile).load(user.getKakaoAccount().getProfile().getProfileImageUrl()).circleCrop().into(profile);

//                    Intent loginIntent = new Intent(LoginActivity.this, Register.class);
//                    loginIntent.putExtra("userId", userId);
//                    startActivity(loginIntent);
//                    finish();

                    Toast.makeText(LoginActivity.this, user.getId() + "로그인되었습니다!", Toast.LENGTH_SHORT).show();
                } else {
                }
                return null;
            }
        });
    }

    //파이어베이스 유저 정보 저장 메소드
    private void writeNewUser(String userId, String name, String email, String profileImg) {
        UserFirebase user = new UserFirebase(userId, name, email, profileImg);

        mDatabase.child("user").child(userId).child("user_info").setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(LoginActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(LoginActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //파이어베이스 유저 정보 읽기 메소드
    private void readUser() {
        mDatabase.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.getValue(User.class) != null) {
                    User post = dataSnapshot.getValue(User.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(LoginActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 파이어베이스 유저정보 불러오기 error 시 로그 찍음
                Log.w("FireBaseData", "loadPost:onCancelled", error.toException());
            }
        });
    }

}

