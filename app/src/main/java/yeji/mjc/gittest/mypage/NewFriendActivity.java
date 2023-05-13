package yeji.mjc.gittest.mypage;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yeji.mjc.gittest.R;

public class NewFriendActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<NewFriendItem> nFItems = new ArrayList<NewFriendItem>();
        nFItems.add(new NewFriendItem(R.drawable.ic_round_face_24,"Sujin","대결 1회 진행중"));
        nFItems.add(new NewFriendItem(R.drawable.ic_round_face_24,"Sujin","대결 1회 진행중"));
        nFItems.add(new NewFriendItem(R.drawable.ic_round_face_24,"Sujin","대결 1회 진행중"));
        nFItems.add(new NewFriendItem(R.drawable.ic_round_face_24,"Sujin","대결 1회 진행중"));
        nFItems.add(new NewFriendItem(R.drawable.ic_round_face_24,"Sujin","대결 1회 진행중"));
        nFItems.add(new NewFriendItem(R.drawable.ic_round_face_24,"Sujin","대결 1회 진행중"));
        nFItems.add(new NewFriendItem(R.drawable.ic_round_face_24,"Sujin","대결 1회 진행중"));



        RecyclerView recyclerView = findViewById(R.id.newFriendRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NewFriendAdapter(getApplicationContext(),nFItems));
    }

    @Override
    public void onClick(View view) {

    }
}
