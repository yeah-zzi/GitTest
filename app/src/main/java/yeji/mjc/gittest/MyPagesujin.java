package yeji.mjc.gittest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import yeji.mjc.gittest.comunity.Life_Fragment;
import yeji.mjc.gittest.mypage.Bellset;
import yeji.mjc.gittest.mypage.UserInfoChange;

public class MyPagesujin extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_pagesujin, container, false);
        View User = view.findViewById(R.id.User);
        //View Friend = view.findViewById(R.id.Friend);
        View Bell = view.findViewById(R.id.Bell);
        //View Tutorial = view.findViewById(R.id.Tutorial);

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