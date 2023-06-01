package yeji.mjc.gittest.cart;


import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.RadioButton;

import android.widget.ImageButton;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import yeji.mjc.gittest.FoodBattle_IMG_Upload;
import yeji.mjc.gittest.MainActivity;
import yeji.mjc.gittest.R;

public class Cartsujin extends Fragment {

    public RecyclerView recyclerView;
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();

    public FoodAdapter foodAdapter;


    ImageButton MartSearch;
    Button AddBtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cart_main, container, false);
        recyclerView = view.findViewById(R.id.FoodListRV);
        AddBtn = view.findViewById(R.id.AddBtn);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CartPlusintent = new Intent(getContext(), CartPlus.class);
                startActivity(CartPlusintent);
            }
        });
        //recyclerView.setHasFixedSize(true);
      RadioButton radioButton = view.findViewById(R.id.AllSelectBT);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((RadioButton) v).isChecked();
                if (isChecked) {
                    foodAdapter.selectAll();
//                    AlertDialog.Builder cartDelete = new AlertDialog.Builder(getContext());
//                    cartDelete.setMessage("전부 삭제하시겠습니까?");
//                    cartDelete.setPositiveButton("취소", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    cartDelete.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                           foodAdapter.removeAllItem();
//                        }
//                    });
//                    cartDelete.show();
                } else {
                    foodAdapter.deselectAll();
                }
            }
        });

        foodAdapter = new FoodAdapter(foodItems);
        recyclerView.setAdapter(foodAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                switch (direction) {
                    case ItemTouchHelper.LEFT:

                        //삭제할 아이템
                        FoodItem deleteItem = foodItems.get(position);

                        //삭제 기능
                        foodItems.remove(position);
                        foodAdapter.removeItem(position);
                        foodAdapter.notifyItemRemoved(position);
                        foodAdapter.notifyDataSetChanged();

                        //복구
                        Snackbar.make(recyclerView, deleteItem.getName(), Snackbar.LENGTH_LONG)
                                .setAction("복구", new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view) {
                                        foodItems.add(position, deleteItem);
                                        foodAdapter.addItem(position,deleteItem);
                                        foodAdapter.notifyItemInserted(position);
                                    }
                                }).show();
                        break;

                }
            }
            // 스와이프 속성 설정
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX,dY,actionState,isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.RED) // 스와이프 왼쪽으로 밀때 바탕화면 설정
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_outline_24) // 아이콘 설정
                        .addSwipeLeftLabel("삭제")
                         .setSwipeLeftLabelColor(Color.WHITE) // 문구 색상 설정
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


    }).attachToRecyclerView(recyclerView);


        MartSearch = view.findViewById(R.id.MartSearch); // 마트 찾기 버튼

        MartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Cart_Map.class);
                startActivity(intent);
            }
        });



        return view;
    }

    public void onStart(){
        super.onStart();

        for(int i=0;i<9;i++){
            foodItems.add(new FoodItem("오징어", "0개",R.drawable.bread,false));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter(new FoodAdapter(foodItems));
    }

}