package yeji.mjc.gittest.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class MapListAdapter extends RecyclerView.Adapter<MapListAdapter.ViewHolder> {

    // OnItemClickListener 참조 변수 선언
    private OnItemClickListener itemClickListener;

    // OnItemClickListener 인터페이스 선언
    public interface OnItemClickListener{
        void onClick(View v, int position);
    }
    // OnItemClickListener 전달 메소드
    public void setItemClickListener(OnItemClickListener onItemClickListener){
        this.itemClickListener = onItemClickListener;
    }

    //리스트는 무조건 데이터를 필요로함
    private ArrayList<MapListLayout> itemList;

    public MapListAdapter(ArrayList<MapListLayout> arrayList) { //Main_class와 연동하기 위한 adapter 파라미터
        itemList = arrayList;
    }

    //껍데기만 만듬. 1번 실행
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_map_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    //껍데기에 데이터 바인딩. 2번 실행
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(itemList.get(position).getName());
        holder.road.setText(itemList.get(position).getRoad());
        holder.address.setText(itemList.get(position).getAddress());

        // 아이템 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClick(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    //ViewHolder : 뷰들의 책꽂이
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, road, address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_list_name);
            road = itemView.findViewById(R.id.tv_list_road);
            address = itemView.findViewById(R.id.tv_list_address);
        }

    }
}
