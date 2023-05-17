package yeji.mjc.gittest;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Kakao SDK 초기화
        KakaoSdk.init(this, "17d02b02412588cc1e7d721a2982c0a3");
    }
}
