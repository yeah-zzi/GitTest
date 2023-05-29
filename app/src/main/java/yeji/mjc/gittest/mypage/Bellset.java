package yeji.mjc.gittest.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import yeji.mjc.gittest.R;

public class Bellset extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_bellset , container, false);
       View backbtn = view.findViewById(R.id.backbtn);

       backbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getFragmentManager().beginTransaction().replace(R.id.bellcontainer, new MyPagesujin()).commit();
               return;
           }
       });

        return view;
     }
}