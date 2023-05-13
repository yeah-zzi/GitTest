package yeji.mjc.gittest.mypage;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import yeji.mjc.gittest.R;

public class AddFriend extends AppCompatActivity {

    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        search=findViewById(R.id.search);
        search.bringToFront();

        }
    }



