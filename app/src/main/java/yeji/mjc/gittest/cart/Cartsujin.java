package yeji.mjc.gittest.cart;


import android.graphics.Canvas;
import android.graphics.Color;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;

import android.widget.ImageButton;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.UserData;

public class Cartsujin extends Fragment {

    public RecyclerView recyclerView, cartrecyclerView;
    public ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
    public ArrayList<CartItem> cartItems = new ArrayList<CartItem>();

    public FoodAdapter foodAdapter;
    public CartAdapter cartAdapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference cartdb;
    ArrayList<CartItem> items;
    String userid;

    ImageButton MartSearch;
    Button AddBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //로그인 시 아이디값 변수 받아오기
        userid = UserData.getInstance().getUserid();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cart_main, container, false);
        recyclerView = view.findViewById(R.id.FoodListRV);
        cartrecyclerView = view.findViewById(R.id.cartrecycler);
        AddBtn = view.findViewById(R.id.AddBtn);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CartPlusintent = new Intent(getContext(), CartPlus.class);
                startActivity(CartPlusintent);
            }
        });
        //recyclerView.setHasFixedSize(true);
     CheckBox checkBox = view.findViewById(R.id.AllSelectBT);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox) v).isChecked();
                if (isChecked) {
                    foodAdapter.selectAll();
                    Intent cartDialog = new Intent(getActivity(),CartDialog.class);
                    startActivity(cartDialog);
                } else {
                    foodAdapter.deselectAll();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

                        // 아이템을 cartdb에서 삭제
                        DatabaseReference cartDbRef = database.getReference().child("user").child(userid).child("cart");
                        cartDbRef.child(deleteItem.food_name).removeValue();


                        // 아이템을 cartdeleteditems에 추가 (삭제된 아이템 저장)
                        DatabaseReference cartDeletedItemsRef = database.getReference().child("user").child(userid).child("cartdeleteditems");
                        String itemName = deleteItem.getName();
                        cartDeletedItemsRef.child(itemName).setValue(deleteItem);


                        //복구
                        Snackbar.make(recyclerView, deleteItem.getName(), Snackbar.LENGTH_LONG)
                                .setAction("복구", new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view) {

                                        DatabaseReference cartDbRef1 = database.getReference().child("user").child(userid).child("cart");
                                        // cartItem 클래스랑 똑같이 맞춰서 넣어야함
                                        CartItem decartItem = new CartItem(deleteItem.getName(), deleteItem.getNum(), deleteItem.getImg());
                                        cartDbRef1.child(itemName).setValue(decartItem);

                                            foodItems.add(position, deleteItem);
                                            foodAdapter.addItem(position, deleteItem);
                                            foodAdapter.notifyItemInserted(position);


                                        // 복구된 아이템을 cartdb에 추가 (복구)
                                        DatabaseReference cartDbRef = database.getReference().child("user").child(userid).child("cartdeleteditems");
                                        cartDbRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                                                //foodItems.clear();   //기존 배열리스트가 존재하지 않게 초기화
                                                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                                    FoodItem decartItem = snapshot1.getValue(FoodItem.class);
                                                    foodItems.add(decartItem);
                                                }
                                                recyclerView.getAdapter().notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                //디비를 가져오다 오류 발생시
                                            }
                                        });

                                        // cartdeleteditems에서 아이템 제거
                                        cartDeletedItemsRef.child(itemName).removeValue();
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

        //파이어베이스에 저장되어 있는 카트
        cartdb = database.getReference().child("user").child(userid).child("cart");
        cartdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                foodItems.clear();   //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    CartItem cartItem = snapshot1.getValue(CartItem.class);
                    FoodItem foodItem =
                            new FoodItem(cartItem.getFood_name(),cartItem.getFood_count(),cartItem.getFood_img(),false);
                    foodItems.add(foodItem);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });

        return view;
    }

}