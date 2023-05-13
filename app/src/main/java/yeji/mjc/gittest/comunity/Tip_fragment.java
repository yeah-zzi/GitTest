package yeji.mjc.gittest.comunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;


public class Tip_fragment extends Fragment implements SelectListener{

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter_tip;
    public ArrayList<TipItem> tipItems = new ArrayList<TipItem>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tip_fragment, container, false);
        recyclerView = view.findViewById(R.id.tipRecyclerView);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    public void onStart(){
        super.onStart();

        for(int i=0;i<9;i++){
            tipItems.add(new TipItem("Geuli**","4","5","식빵을 기름으로 없애보세요",R.drawable.bread));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TipAdapter(getActivity(),tipItems,this));
    }


    @Override
    public void onItemClicked(TipItem tip_fragment) {
        Intent fridgePlusIntent = new Intent(getActivity(), TipComment.class);
        startActivity(fridgePlusIntent);
    }
}