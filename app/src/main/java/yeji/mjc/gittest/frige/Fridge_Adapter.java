package yeji.mjc.gittest.frige;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import yeji.mjc.gittest.FoodItem;
import yeji.mjc.gittest.R;

public class Fridge_Adapter extends RecyclerView.Adapter<Fridge_recycle_holder> {

    Fridge_Adapter fridge_adapter;
    ArrayList<Fridge_Item> fridgeItems;
    int count = 0;


    public Fridge_Adapter(ArrayList<Fridge_Item> fridgeItems) {
        this.fridgeItems = fridgeItems;
    }

    public Fridge_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fridge_recyclerview, parent, false);
        return new Fridge_recycle_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Fridge_recycle_holder holder, int position) {

        int itemPosition = holder.getAdapterPosition();
        //아이템의 위치를 동적으로 가져옴.
        fridge_adapter = this; // fridge_adapter 객체 초기화

        holder.food_img.setImageResource(fridgeItems.get(position).getFood_img());
        holder.food_name.setText(fridgeItems.get(position).getFood_name());
        holder.food_count.setText(fridgeItems.get(position).getFood_count());
        holder.food_date.setText(fridgeItems.get(position).getFood_date());

        //재료 수 더하기
        holder.count_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                holder.food_count.setText(count + "개");
            }
        });

        //재료 수 감소하기
        holder.count_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) { //식재료 수가 0 또는 음수일때 감소하지 않도록
                    count--;
                    holder.food_count.setText(count + "개");
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
        NAME_ASCENDING, //Food_name을 기준으로 오름차순
        // 추가적인 정렬 기준은 이곳에 추가
    }

    private SortType currentSortType = SortType.NAME_ASCENDING; //현재 정렬 기준

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
            // 추가적인 정렬 기준에 따라 case를 추가
        }
    }

    // 현재 정렬 기준을 설정하는 메서드
    public void setSortType(SortType sortType) {
        currentSortType = sortType;
        sortItems();
        notifyDataSetChanged(); //정렬 결과를 반영.
    }


    private void removeItem(int position) {
    }

    @Override
    public int getItemCount() {
        return fridgeItems.size();
    }

}
