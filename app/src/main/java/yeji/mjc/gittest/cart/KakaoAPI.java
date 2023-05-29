package yeji.mjc.gittest.cart;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoAPI {
    @GET("v2/local/search/keyword.json")                               // Keyword.json의 정보를 받아옴
    Call<ResultSearchKeyword> getSearchKeyword(                      // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김
     @Header("Authorization") String key,                          // 카카오 API 인증키 [필수]
     @Query("query") String query,                                 // 검색을 원하는 질의어 [필수]
     @Query("y") String y,
     @Query("x") String x,
     @Query("radius") int radius,
     @Query("page") int page
    );
}