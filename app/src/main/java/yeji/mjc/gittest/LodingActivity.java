package yeji.mjc.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.user.UserApiClient;

public class LodingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loding);

        startLoding();
    }

    private void startLoding() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // 유저 토큰 정보가 있다면, 자동로그인 하여 Main으로 넘어감
                UserApiClient.getInstance().accessTokenInfo((tokenInfo, error) -> {
                    if (error != null) {

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                    } else if (tokenInfo != null) {

                        Log.i("토큰 확인", "토큰 정보 보기 성공" +
                                "\n회원번호: " + tokenInfo.getId() +
                                "\n만료시간: " + tokenInfo.getExpiresIn() + "초");

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                    return null;
                });

                finish();
            }
        }, 2000);
    }
}
