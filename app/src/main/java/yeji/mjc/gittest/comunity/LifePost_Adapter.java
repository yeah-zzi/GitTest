package yeji.mjc.gittest.comunity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class LifePost_Adapter extends RecyclerView.Adapter<LifePost_Adapter.ViewHolder> {

    private ArrayList<TipItem> items = null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_title;
        TextView text_script;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            text_title = itemView.findViewById(R.id.title_text);
            text_script = itemView.findViewById((R.id.script_text));
            imageView = itemView.findViewById(R.id.post_img);
        }
    }

    LifePost_Adapter(ArrayList<TipItem> list){
        items = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.life_post_items, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text_title.setText(items.get(position).getTitle());
        holder.text_script.setText(items.get(position).getContent());
        Glide.with(holder.itemView).load(items.get(position).getPost_img()).into(holder.imageView);

        //TODO : [holder.객체.setOnClickListener] 만들어야 함 --> 클릭 시, 게시물 상세페이지로 넘어가게
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
