package yeji.mjc.gittest.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.comunity.Life_Fragment;
import yeji.mjc.gittest.mypage.Bellset;
import yeji.mjc.gittest.mypage.UserInfoChange;
import yeji.mjc.gittest.LoginActivity;

public class MyPagesujin extends Fragment {
    Button logoutBtn;

    @Override
    public void setEnterTransition(@Nullable Object transition) {
        super.setEnterTransition(transition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_pagesujin, container, false);
        View User = view.findViewById(R.id.User);
        //View Friend = view.findViewById(R.id.Friend);
        View Bell = view.findViewById(R.id.Bell);
        //View Tutorial = view.findViewById(R.id.Tutorial);

        logoutBtn = view.findViewById(R.id.logoutButton);
        //로그아웃 버튼 기능 부여
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 유저의 토큰 값 삭제 됨
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class); // 다시 로그인 화면으로 돌아감
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)); // 뒤로 가기 눌러도 로그아웃 전으로 못 돌아가게 설정
                        return null;
                    }
                });
            }
        });

        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mpcontainer, new UserInfoChange()).commit();
                return;
            }
        });

        Bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mpcontainer, new Bellset()).commit();
                return;
            }
        });
        return view;
    }
}