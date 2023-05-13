package yeji.mjc.gittest.comunity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yeji.mjc.gittest.R;

public class Life_Fragment extends Fragment {

    private ArrayList<LifePostItem> list = new ArrayList<>(); //리사이클러뷰에 추가할 아이템 리스트
    private LifePost_Adapter adapter; // 어뎁터
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_life, container, false);

        list.add(new LifePostItem("좁아도 OK! 똑똑하게 싱크대 쓰는 법", "걸어서 사용할 수 있는 것은 되도록 걸어서 사용하는 것이 주방을 넓게 사용하는 방법 중 하나인데요. 조리도구나 키친타올 등 걸 수 있는 것들은 모두 걸 수 있도록 아이디어를 내면 좋아요.",
                R.drawable.post1));
        list.add(new LifePostItem("좁아도 OK! 똑똑하게 싱크대 쓰는 법", "걸어서 사용할 수 있는 것은 되도록 걸어서 사용하는 것이 주방을 넓게 사용하는 방법 중 하나인데요. 조리도구나 키친타올 등 걸 수 있는 것들은 모두 걸 수 있도록 아이디어를 내면 좋아요.",
                R.drawable.post1));
        list.add(new LifePostItem("좁아도 OK! 똑똑하게 싱크대 쓰는 법", "걸어서 사용할 수 있는 것은 되도록 걸어서 사용하는 것이 주방을 넓게 사용하는 방법 중 하나인데요. 조리도구나 키친타올 등 걸 수 있는 것들은 모두 걸 수 있도록 아이디어를 내면 좋아요.",
                R.drawable.post1));
        list.add(new LifePostItem("좁아도 OK! 똑똑하게 싱크대 쓰는 법", "걸어서 사용할 수 있는 것은 되도록 걸어서 사용하는 것이 주방을 넓게 사용하는 방법 중 하나인데요. 조리도구나 키친타올 등 걸 수 있는 것들은 모두 걸 수 있도록 아이디어를 내면 좋아요.",
                R.drawable.post1));
        list.add(new LifePostItem("좁아도 OK! 똑똑하게 싱크대 쓰는 법", "걸어서 사용할 수 있는 것은 되도록 걸어서 사용하는 것이 주방을 넓게 사용하는 방법 중 하나인데요. 조리도구나 키친타올 등 걸 수 있는 것들은 모두 걸 수 있도록 아이디어를 내면 좋아요.",
                R.drawable.post1));
        list.add(new LifePostItem("좁아도 OK! 똑똑하게 싱크대 쓰는 법", "걸어서 사용할 수 있는 것은 되도록 걸어서 사용하는 것이 주방을 넓게 사용하는 방법 중 하나인데요. 조리도구나 키친타올 등 걸 수 있는 것들은 모두 걸 수 있도록 아이디어를 내면 좋아요.",
                R.drawable.post1));
        list.add(new LifePostItem("좁아도 OK! 똑똑하게 싱크대 쓰는 법", "걸어서 사용할 수 있는 것은 되도록 걸어서 사용하는 것이 주방을 넓게 사용하는 방법 중 하나인데요. 조리도구나 키친타올 등 걸 수 있는 것들은 모두 걸 수 있도록 아이디어를 내면 좋아요.",
                R.drawable.post1));
        list.add(new LifePostItem("좁아도 OK! 똑똑하게 싱크대 쓰는 법", "걸어서 사용할 수 있는 것은 되도록 걸어서 사용하는 것이 주방을 넓게 사용하는 방법 중 하나인데요. 조리도구나 키친타올 등 걸 수 있는 것들은 모두 걸 수 있도록 아이디어를 내면 좋아요.",
                R.drawable.post1));

        recyclerView = (RecyclerView) view.findViewById(R.id.life_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LifePost_Adapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }


}

