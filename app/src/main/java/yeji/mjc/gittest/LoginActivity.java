package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import yeji.mjc.gittest.register.Register;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public ImageButton loginButton;
//    public Button logoutButton;
//    public TextView nickName;
//    public ImageView profile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);

        loginButton = (ImageButton) findViewById(R.id.loginButton);
//        logoutButton = findViewById(R.id.logoutButton);
//        nickName = findViewById(R.id.nickName);
//        profile = findViewById(R.id.profile);



        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken, Throwable, Unit> callback = new  Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if(oAuthToken != null) {
                //로그인이 되었을 때 처리할 일들
                    Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                }
                if (throwable != null) {
                // 결과 오류 시, 처리할 부분
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                updateKakaoLoginUi();
                return null;
            }
        };
        // 로그인 버튼
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("checkLogin", "성공" );
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                    //카카오톡 설치, 카카오톡으로 로그인
                }else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                    //카카오톡 미설치, 카카오계정으로 로그인
                }
            }

        });
        // 로그 아웃 버튼
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
//                    @Override
//                    public Unit invoke(Throwable throwable) {
//                        updateKakaoLoginUi();
//                        return null;
//                    }
//                });
//            }
//        });

        updateKakaoLoginUi();
    }
    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                //로그인 되어 있는 경우
                if( user != null) {

                    // 유저의 아이디
                    Log.d(TAG,"invoke: id" + user.getId());
//                    // 유저의 어카운트정보에 이메일
                    Log.d(TAG,"invoke: nickname" + user.getKakaoAccount().getEmail());
//                    // 유저의 어카운트 정보의 프로파일에 닉네임
                    Log.d(TAG,"invoke: email" + user.getKakaoAccount().getProfile().getNickname());
                    // 유저의 이미지 URL을 불러옵니다.
                   // Log.d(TAG, "userimage " + user.getKakaoAccount().getProfile().getProfileImageUrl());

//                    // 유저의 어카운트 파일의 성별
//                    Log.d(TAG,"invoke: gerder" + user.getKakaoAccount().getGender());
//                    // 유저의 어카운트 정보에 나이
//                    Log.d(TAG,"invoke: age" + user.getKakaoAccount().getAgeRange());

//                    nickName.setText(user.getKakaoAccount().getProfile().getNickname()); // 받아온 닉네임
//                    Glide.with(profile).load(user.getKakaoAccount().getProfile().getProfileImageUrl()).circleCrop().into(profile);

                    //loginButton.setVisibility(View.GONE);

                    Intent loginIntent = new Intent(LoginActivity.this, Register.class);
                    startActivity(loginIntent);
                    finish();


                    Toast.makeText(LoginActivity.this, user.getId()+"로그인되었습니다!", Toast.LENGTH_SHORT).show();
                } else {
                    // 로그인이 되어 있지 않은 경우
//                    nickName.setText(null);
//                    profile.setImageBitmap(null);
                    loginButton.setVisibility(View.VISIBLE);
                }
                return null;
            }
        });
    }
}

