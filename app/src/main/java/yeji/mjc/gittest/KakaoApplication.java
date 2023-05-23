package yeji.mjc.gittest;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Kakao SDK 초기화
        KakaoSdk.init(this, "64551003f034bdc438ee13c677e73f8a");
    }
}
