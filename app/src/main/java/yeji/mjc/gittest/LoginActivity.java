package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        btn1=findViewById(R.id.start);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btn2=findViewById(R.id.start2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });


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

        updateKakaoLoginUi();
    }
    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                //로그인 되어 있는 경우
                if( user != null) {

                    String userId = user.getId().toString();
                    String userEmail = user.getKakaoAccount().getEmail().toString();
                    String userNickname = user.getKakaoAccount().getProfile().getNickname().toString();
                    String userImg = user.getKakaoAccount().getProfile().getProfileImageUrl().toString();

                    // 유저의 아이디
                    Log.d(TAG,"kakao 유저 id : " + userId);
                    // 유저의 어카운트정보에 이메일
                    Log.d(TAG,"kakao 유저 email : " + userEmail);
                    // 유저의 어카운트 정보의 프로파일에 닉네임
                    Log.d(TAG,"kakao 유저 닉네임 : " + userNickname);
                    // 유저의 이미지 URL을 불러옵니다.
                    Log.d(TAG, "kakao 유저 프사 url :  " + userImg);


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
//                    loginButton.setVisibility(View.VISIBLE);
                }
                return null;
            }
        });
    }
}

