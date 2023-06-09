package yeji.mjc.gittest.frige;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import yeji.mjc.gittest.FoodItem;
import yeji.mjc.gittest.R;

public class Fridge_Adapter extends RecyclerView.Adapter<Fridge_recycle_holder> {

    Fridge_Adapter fridge_adapter;
    ArrayList<Fridge_Item> fridgeItems;
    int count = 0;
    String userid="2830097009";

    FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 저장소 객체
    DatabaseReference cartdb;



    private final int[] gaugeColors = {
            Color.rgb(225, 0, 0),
            Color.rgb(255, 191, 0),
            Color.rgb(100, 191, 0),
            Color.rgb(33, 121, 240)
    };

    private int calculateProgress(String food_date) {

        String numberString = food_date.substring(2);
        int number = Integer.parseInt(numberString);

        if (number <= 3) {
            return 20;
        } else if (number <= 15) {
            return 40;
        } else if (number <= 30) {
            return 60;
        } else {
            return 76;
        }
    }


    public Fridge_Adapter(ArrayList<Fridge_Item> fridgeItems) throws ParseException {
        this.fridgeItems = fridgeItems;
    }

    public Fridge_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fridge_recyclerview, parent, false);
        return new Fridge_recycle_holder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull Fridge_recycle_holder holder, int position) {

        Fridge_Item fridgeItem = fridgeItems.get(position);
        String foodDate = fridgeItem.getFood_date();

        int progress = calculateProgress(foodDate);

        fridgeItem.setProgress(progress);

        progress = fridgeItems.get(position).getProgress();
        int colorIndex = progress / 25;
        int gaugeColor = gaugeColors[colorIndex];

        cartdb = database.getReference().child("user").child(userid).child("cart");

        holder.progressbar.setProgress(progress);
        holder.progressbar.setProgressTintList(ColorStateList.valueOf(gaugeColor));


        int itemPosition = holder.getAdapterPosition();
        //아이템의 위치를 동적으로 가져옴.
        fridge_adapter = this; // fridge_adapter 객체 초기화

        Glide.with(holder.itemView)
                .load(fridgeItems.get(position).getFood_img())
                .into(holder.food_img);
        holder.food_name.setText(fridgeItems.get(position).getFood_name());
        holder.food_count.setText(fridgeItems.get(position).getFood_count()+"개");
        holder.food_date.setText(fridgeItems.get(position).getFood_date());
        holder.progressbar.setProgress(fridgeItems.get(position).getProgress());


        //재료 수 더하기
        holder.count_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                int count = Integer.parseInt(fridgeItems.get(position).getFood_count());
                count++;
                fridgeItems.get(position).setFood_count(String.valueOf(count));
                holder.food_count.setText(String.valueOf(count)+"개");

                // 파이어베이스에서 해당 아이템의 값을 업데이트
                String itemName = fridgeItems.get(holder.getAdapterPosition()).getFood_name();
                int itemIndex = holder.getAdapterPosition(); // 아이템의 인덱스 확인
                if (itemIndex >= 0 && itemIndex < fridgeItems.size()) {
                    DatabaseReference cartdb = database.getReference().child("user").child(userid).child("fridge");
                    cartdb.child(itemName).child("food_count").setValue(String.valueOf(count));
                }
            }
        });

        //재료 수 감소하기
        holder.count_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                int count = Integer.parseInt(fridgeItems.get(position).getFood_count());
                if(count>1) {
                    count--;
                    fridgeItems.get(position).setFood_count(String.valueOf(count));
                    holder.food_count.setText(String.valueOf(count)+"개");

                    // 파이어베이스에서 해당 아이템의 값을 업데이트
                    String itemName = fridgeItems.get(holder.getAdapterPosition()).getFood_name();
                    int itemIndex = holder.getAdapterPosition(); // 아이템의 인덱스 확인
                    if (itemIndex >= 0 && itemIndex < fridgeItems.size()) {
                        DatabaseReference cartdb = database.getReference().child("user").child(userid).child("fridge");
                        cartdb.child(itemName).child("food_count").setValue(String.valueOf(count));
                    }
                }
            }
        });

        // 아이템 롱클릭 시 버튼 가시성 변경
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.close.setVisibility(View.VISIBLE);
                holder.food_count.setVisibility(View.GONE);
                holder.count_plus.setVisibility(View.GONE);
                holder.count_minus.setVisibility(View.GONE);
                holder.food_date.setVisibility(View.GONE);
                holder.btn_cart.setVisibility(View.VISIBLE);
                holder.btn_delete.setVisibility(View.VISIBLE);


                //롱클릭의 후속 리스너..
                holder.btn_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemPosition != RecyclerView.NO_POSITION) {
                            cartdb.child(fridgeItems.get(position).getFood_name()).child("food_count").setValue(fridgeItems.get(position).getFood_count());
                            cartdb.child(fridgeItems.get(position).getFood_name()).child("food_img").setValue(fridgeItems.get(position).getFood_img()+"");
                            cartdb.child(fridgeItems.get(position).getFood_name()).child("food_name").setValue(fridgeItems.get(position).getFood_count()+"");

                            //아이템을 삭제했다면 나머지 식재료들은 원상태로 복구
                            holder.close.setVisibility(View.GONE);
                            holder.food_count.setVisibility(View.VISIBLE);
                            holder.count_plus.setVisibility(View.VISIBLE);
                            holder.count_minus.setVisibility(View.VISIBLE);
                            holder.food_date.setVisibility(View.VISIBLE);
                            holder.btn_cart.setVisibility(View.GONE);
                            holder.btn_delete.setVisibility(View.GONE);

                        }
                    }
                });

                //롱클릭의 후속 리스너..
                holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemPosition != RecyclerView.NO_POSITION) {

                            //해당 아이템 삭제
                            fridgeItems.remove(itemPosition);
                            fridge_adapter.removeItem(itemPosition);
                            fridge_adapter.notifyItemRemoved(itemPosition);
                            fridge_adapter.notifyDataSetChanged();

                            //아이템을 삭제했다면 나머지 식재료들은 원상태로 복구
                            holder.close.setVisibility(View.GONE);
                            holder.food_count.setVisibility(View.VISIBLE);
                            holder.count_plus.setVisibility(View.VISIBLE);
                            holder.count_minus.setVisibility(View.VISIBLE);
                            holder.food_date.setVisibility(View.VISIBLE);
                            holder.btn_cart.setVisibility(View.GONE);
                            holder.btn_delete.setVisibility(View.GONE);

                        }
                    }
                });

                return true;
            }
        });

        //X 버튼 누를시 원래 상태로 복구
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼 가시성 변경
                holder.close.setVisibility(View.GONE);
                holder.food_count.setVisibility(View.VISIBLE);
                holder.count_plus.setVisibility(View.VISIBLE);
                holder.count_minus.setVisibility(View.VISIBLE);
                holder.food_date.setVisibility(View.VISIBLE);
                holder.btn_cart.setVisibility(View.GONE);
                holder.btn_delete.setVisibility(View.GONE);

            }
        });
    }


    //정렬 기준을 나타내는 enum
    public enum SortType {
        NAME_ASCENDING, //이름순
        DATE_ASCENDING, //날짜순

    }

    private SortType currentSortType = SortType.NAME_ASCENDING;

    // 아이템 정렬 메서드
    private void sortItems() {
        switch (currentSortType) {
            case NAME_ASCENDING:
                Collections.sort(fridgeItems, new Comparator<Fridge_Item>() {
                    @Override
                    public int compare(Fridge_Item item1, Fridge_Item item2) {
                        return item1.getFood_name().compareTo(item2.getFood_name());
                    }
                });
                break;
            case DATE_ASCENDING:
                Collections.sort(fridgeItems, new Comparator<Fridge_Item>() {
                    @Override
                    public int compare(Fridge_Item item1, Fridge_Item item2) {
                        String date1 = item1.getFood_date().substring(2);
                        String date2 = item2.getFood_date().substring(2);
                        return Integer.compare(Integer.parseInt(date1), Integer.parseInt(date2));
                    }
                });
                break;


        }
    }

    public void setSortType(SortType sortType) {
        currentSortType = sortType;
        sortItems();
        notifyDataSetChanged();
    }


    private void removeItem(int position) {
    }

    @Override
    public int getItemCount() {
        return fridgeItems.size();
    }

}
