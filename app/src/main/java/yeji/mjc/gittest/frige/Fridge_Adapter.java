package yeji.mjc.gittest.frige;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class Fridge_Adapter extends RecyclerView.Adapter<Fridge_recycle_holder> {

    //Context context;
    ArrayList<Fridge_Item> fridgeItems;
    int count = 0;

    public Fridge_Adapter(ArrayList<Fridge_Item> fridgeItems) {
        this.fridgeItems = fridgeItems;
    }

    public Fridge_recycle_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fridge_recyclerview, parent, false);
        return new Fridge_recycle_holder(v);
    }

    //롱클릭한 Item을 삭제할 때 사용할 메소드 구현
    public void removeItem(int itemPosition) {
        //removeItem() 메소드 내에서 position이 유효한 범위인지를 검사
        if (itemPosition >= 0 && itemPosition < fridgeItems.size()) {
            fridgeItems.remove(itemPosition);
            notifyItemRemoved(itemPosition);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull Fridge_recycle_holder holder, int position) {

        int itemPosition = holder.getAdapterPosition();
        //아이템의 위치를 동적으로 가져옴.

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

                holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemPosition != RecyclerView.NO_POSITION) {
                            removeItem(itemPosition);
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

    @Override
    public int getItemCount() {
        return fridgeItems.size();
    }

}
