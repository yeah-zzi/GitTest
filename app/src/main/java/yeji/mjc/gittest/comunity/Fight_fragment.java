package yeji.mjc.gittest.comunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import yeji.mjc.gittest.R;


public class Fight_fragment extends Fragment implements AddfightListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.food_fight, container, false);

        return view;
    }

    @Override
    public void onItemClicked(New_fight fight_fragment) {
        Intent fridgePlusIntent = new Intent(getActivity(), New_fight_sub.class);
        startActivity(fridgePlusIntent);
    }


//  test
}