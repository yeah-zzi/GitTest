package yeji.mjc.gittest.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import yeji.mjc.gittest.R;

public class Mypage_Post extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_post , container, false);
       View backbtn = view.findViewById(R.id.backbtn);
       View delete_post = view.findViewById(R.id.delete_post);
       View good_post = view.findViewById(R.id.good_post);

       backbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getFragmentManager().beginTransaction().replace(R.id.bellcontainer, new MyPagesujin()).commit();
               return;
           }
       });

       delete_post.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //TODO 유저가 업로드한 게시물 리사이클러뷰로 보여지는 XML 연결
           }
       });

       good_post.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //TODO 유저가 좋아요 누른 게시물 리사이클러뷰로 보여지는 XML 연결
           }
       });

        return view;
     }
}