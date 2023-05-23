package yeji.mjc.gittest.FoodSearch;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import yeji.mjc.gittest.AllergyItem;
import yeji.mjc.gittest.R;
import yeji.mjc.gittest.SelectListener;
import yeji.mjc.gittest.firebase.AllergyFirebase;

public class Allergy extends Activity implements View.OnClickListener, SelectListener {

    //리사이클러뷰 변수 선언
    public RecyclerView allergyrecyclerView;
    public ArrayList<AllergyItem> allergyitems = new ArrayList<AllergyItem>();
    private ChildEventListener mChild;

    //리사이클러뷰 변수 선언
    public RecyclerView recyclerView;
    public RecyclerView.Adapter foodSearchAdapter, allergyAdapter;
    public ArrayList<FoodSearchItem> items = new ArrayList<FoodSearchItem>();

    //파이어베이스에서 데이터베이스 가져오기
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    DatabaseReference allergyDB;

    ColorStateList def;
    TextView vegetable, fruit, meet, seafood, milk, drink, select;
    String userid = "임시용 유저 아이디1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allergy_search);

        //설치 메뉴바 구현
        vegetable = findViewById(R.id.vegetable);
        fruit = findViewById(R.id.fruit);
        meet = findViewById(R.id.meet);
        seafood = findViewById(R.id.seafood);
        milk = findViewById(R.id.milk);
        drink = findViewById(R.id.drink);

        vegetable.setOnClickListener(this);
        fruit.setOnClickListener(this);
        meet.setOnClickListener(this);
        seafood.setOnClickListener(this);
        milk.setOnClickListener(this);
        drink.setOnClickListener(this);

        select = findViewById(R.id.select);
        def = fruit.getTextColors();

        //리사이클러뷰구현
        recyclerView = findViewById(R.id.foodSearchRecyclerView);
        recyclerView.setHasFixedSize(true);

        //초기 화면인 메뉴가 채소로 되어있을 때의 아이템 추가
        items.add(new FoodSearchItem(R.drawable.potato, "감자"));
        items.add(new FoodSearchItem(R.drawable.gazi, "가지"));
        items.add(new FoodSearchItem(R.drawable.sweetpotato, "고구마"));
        items.add(new FoodSearchItem(R.drawable.chilli, "고추"));
        items.add(new FoodSearchItem(R.drawable.sweetpumpkin, "단호박"));
        items.add(new FoodSearchItem(R.drawable.carrot, "당근"));
        items.add(new FoodSearchItem(R.drawable.greenonion, "대파"));
        items.add(new FoodSearchItem(R.drawable.galic, "마늘"));
        items.add(new FoodSearchItem(R.drawable.radish, "무"));
        items.add(new FoodSearchItem(R.drawable.napacabbage, "배추"));
        items.add(new FoodSearchItem(R.drawable.mushroom, "버섯"));
        items.add(new FoodSearchItem(R.drawable.broccoli, "브로콜리"));
        items.add(new FoodSearchItem(R.drawable.sangchu, "상추"));
        items.add(new FoodSearchItem(R.drawable.spinach, "시금치"));
        items.add(new FoodSearchItem(R.drawable.asparagus, "아스파라거스"));
        items.add(new FoodSearchItem(R.drawable.squash, "애호박"));
        items.add(new FoodSearchItem(R.drawable.kongnamul, "콩나물"));
        items.add(new FoodSearchItem(R.drawable.cabbage, "양배추"));
        items.add(new FoodSearchItem(R.drawable.onion, "양파"));
        items.add(new FoodSearchItem(R.drawable.corn, "옥수수"));
        items.add(new FoodSearchItem(R.drawable.cucumber, "오이"));
        items.add(new FoodSearchItem(R.drawable.chungyengchae, "청경채"));
        items.add(new FoodSearchItem(R.drawable.papurika, "파프리카"));
        items.add(new FoodSearchItem(R.drawable.pimang, "피망"));

        //리사이클러뷰에 매니저와 어댑터를 연결
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        foodSearchAdapter = new FoodSearchAdapter(this, items, this);
        recyclerView.setAdapter(foodSearchAdapter);

        allergyrecyclerView = findViewById(R.id.allergyRecycler);
        allergyrecyclerView.setHasFixedSize(true);

        //파이어베이스에 저장되어 있는 알러지정보를 유저아이디 정보를 통해 가져옴
        allergyDB = database.getReference().child("user").child(userid).child("allergy");
        //파이어베이스에 저장되어 있는 알러지정보가 변경되면 이벤트를 작동함
        allergyDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                allergyitems.clear();   //기존 리사이클러뷰의 아이템이 존재하지 않게 초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    //알러지의 자식 key가 있다면 반복해서 알러지 정보를 받아오고 리사이클러뷰에 추가 시킨다
                    AllergyItem allergyItem = snapshot1.getValue(AllergyItem.class);
                    allergyitems.add(allergyItem);
                }
                allergyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오다 오류 발생시
            }
        });

    }


    public void onStart() {
        super.onStart();
        //리사이클러뷰에 어댑터와 매니저를 지정한다
        allergyrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        allergyAdapter = new AllergyAdapter(allergyitems);
        allergyrecyclerView.scrollToPosition(allergyitems.size() - 1);
        allergyrecyclerView.setAdapter(allergyAdapter);

    }

    public void onClick(View view) {
        //알러지 종류 메뉴바에 야채를 선택할 경우
        if (view.getId() == R.id.vegetable) {
            //선택한 것이 야채임을 보여주는 애니메이션
            select.animate().x(10).setDuration(100);
            //기존 리사이클러뷰의 아이템이 존재하지 않게 초기화
            clear();
            //해당하는 아이템들 추가
            items.add(new FoodSearchItem(R.drawable.potato, "감자"));
            items.add(new FoodSearchItem(R.drawable.gazi, "가지"));
            items.add(new FoodSearchItem(R.drawable.sweetpotato, "고구마"));
            items.add(new FoodSearchItem(R.drawable.chilli, "고추"));
            items.add(new FoodSearchItem(R.drawable.sweetpumpkin, "단호박"));
            items.add(new FoodSearchItem(R.drawable.carrot, "당근"));
            items.add(new FoodSearchItem(R.drawable.greenonion, "대파"));
            items.add(new FoodSearchItem(R.drawable.galic, "마늘"));
            items.add(new FoodSearchItem(R.drawable.radish, "무"));
            items.add(new FoodSearchItem(R.drawable.napacabbage, "배추"));
            items.add(new FoodSearchItem(R.drawable.mushroom, "버섯"));
            items.add(new FoodSearchItem(R.drawable.broccoli, "브로콜리"));
            items.add(new FoodSearchItem(R.drawable.sangchu, "상추"));
            items.add(new FoodSearchItem(R.drawable.spinach, "시금치"));
            items.add(new FoodSearchItem(R.drawable.asparagus, "아스파라거스"));
            items.add(new FoodSearchItem(R.drawable.squash, "애호박"));
            items.add(new FoodSearchItem(R.drawable.kongnamul, "콩나물"));
            items.add(new FoodSearchItem(R.drawable.cabbage, "양배추"));
            items.add(new FoodSearchItem(R.drawable.onion, "양파"));
            items.add(new FoodSearchItem(R.drawable.corn, "옥수수"));
            items.add(new FoodSearchItem(R.drawable.cucumber, "오이"));
            items.add(new FoodSearchItem(R.drawable.chungyengchae, "청경채"));
            items.add(new FoodSearchItem(R.drawable.papurika, "파프리카"));
            items.add(new FoodSearchItem(R.drawable.pimang, "피망"));

            for (int i = 0; i < items.size(); i++) {
                foodSearchAdapter.notifyItemInserted(i);
            }
        } else if (view.getId() == R.id.fruit) {//알러지 종류 메뉴바에 과일을 선택할 경우
            int size = fruit.getWidth() + 10;
            select.animate().x(size).setDuration(100);
            //기존 리사이클러뷰의 아이템이 존재하지 않게 초기화
            clear();
            //해당하는 아이템들 추가
            items.add(new FoodSearchItem(R.drawable.apple, "사과"));
            items.add(new FoodSearchItem(R.drawable.strawberry, "딸기"));
            items.add(new FoodSearchItem(R.drawable.lemon, "레몬"));
            items.add(new FoodSearchItem(R.drawable.mango, "망고"));
            items.add(new FoodSearchItem(R.drawable.banana, "바나나"));
            items.add(new FoodSearchItem(R.drawable.cherry_tomato, "방울토마토"));
            items.add(new FoodSearchItem(R.drawable.pear, "배"));
            items.add(new FoodSearchItem(R.drawable.peach, "복숭아"));
            items.add(new FoodSearchItem(R.drawable.blueberries, "블루베리"));
            items.add(new FoodSearchItem(R.drawable.sukrue, "석류"));
            items.add(new FoodSearchItem(R.drawable.watermelon, "수박"));
            items.add(new FoodSearchItem(R.drawable.avocado, "아보카도"));
            items.add(new FoodSearchItem(R.drawable.fruit, "오렌지"));
            items.add(new FoodSearchItem(R.drawable.plum, "자두"));
            items.add(new FoodSearchItem(R.drawable.tomato, "토마토"));
            items.add(new FoodSearchItem(R.drawable.pineapple, "파인애플"));

            for (int i = 0; i < items.size(); i++) {
                foodSearchAdapter.notifyItemInserted(i);
            }
        } else if (view.getId() == R.id.meet) {//알러지 종류 메뉴바에 고기를 선택할 경우
            int size = fruit.getWidth() * 2 + 10;
            select.animate().x(size).setDuration(100);
            //기존 리사이클러뷰의 아이템이 존재하지 않게 초기화
            clear();
            //해당하는 아이템들 추가
            items.add(new FoodSearchItem(R.drawable.sajfkldfjasldkfjsdl, "닭가슴살"));
            items.add(new FoodSearchItem(R.drawable.dfdfd, "닭날개"));
            items.add(new FoodSearchItem(R.drawable.fdfddf, "닭다리"));
            items.add(new FoodSearchItem(R.drawable.erwe, "돼지고기"));
            items.add(new FoodSearchItem(R.drawable.fsaf, "베이컨"));
            items.add(new FoodSearchItem(R.drawable.fdsaf, "삼겹살"));
            items.add(new FoodSearchItem(R.drawable.fdsasaf, "소고기"));
            items.add(new FoodSearchItem(R.drawable.fdsfsd, "어묵"));
            items.add(new FoodSearchItem(R.drawable.yang, "양고기"));

            for (int i = 0; i < items.size(); i++) {
                foodSearchAdapter.notifyItemInserted(i);
            }
        } else if (view.getId() == R.id.seafood) {//알러지 종류 메뉴바에 해산물을 선택할 경우
            int size = fruit.getWidth() * 3 + 25;
            select.animate().x(size).setDuration(100);
            //기존 리사이클러뷰의 아이템이 존재하지 않게 초기화
            clear();
            //해당하는 아이템들 추가
            items.add(new FoodSearchItem(R.drawable.garivi, "가리비"));
            items.add(new FoodSearchItem(R.drawable.gare, "게"));
            items.add(new FoodSearchItem(R.drawable.rapsta, "랍스터"));
            items.add(new FoodSearchItem(R.drawable.muna, "문어"));
            items.add(new FoodSearchItem(R.drawable.myeahfka, "멸치"));
            items.add(new FoodSearchItem(R.drawable.godung, "생선"));
            items.add(new FoodSearchItem(R.drawable.daye, "새우"));
            items.add(new FoodSearchItem(R.drawable.food_squid, "오징어"));
            items.add(new FoodSearchItem(R.drawable.yesaon, "연어"));
            items.add(new FoodSearchItem(R.drawable.hfodn, "홍합"));

            for (int i = 0; i < items.size(); i++) {
                foodSearchAdapter.notifyItemInserted(i);
            }
        } else if (view.getId() == R.id.milk) {//알러지 종류 메뉴바에 유제품을 선택할 경우
            int size = fruit.getWidth() * 4 + 65;
            select.animate().x(size).setDuration(100);
            //기존 리사이클러뷰의 아이템이 존재하지 않게 초기화
            clear();
            //해당하는 아이템들 추가
            items.add(new FoodSearchItem(R.drawable.eggs, "계란"));
            items.add(new FoodSearchItem(R.drawable.breadfdfd, "빵"));
            items.add(new FoodSearchItem(R.drawable.milk, "우유"));
            items.add(new FoodSearchItem(R.drawable.xcvdsafsf, "야구르트"));
            items.add(new FoodSearchItem(R.drawable.yogurt, "요거트"));
            items.add(new FoodSearchItem(R.drawable.cheese, "치즈"));

            for (int i = 0; i < items.size(); i++) {
                foodSearchAdapter.notifyItemInserted(i);
            }
        } else if (view.getId() == R.id.drink) {//알러지 종류 메뉴바에 음료를 선택할 경우
            int size = fruit.getWidth() * 5 + 80;
            select.animate().x(size).setDuration(100);
            //기존 리사이클러뷰의 아이템이 존재하지 않게 초기화
            clear();
            //해당하는 아이템들 추가
            items.add(new FoodSearchItem(R.drawable.drink, "주스"));
            items.add(new FoodSearchItem(R.drawable.cola, "콜라"));

            for (int i = 0; i < items.size(); i++) {
                foodSearchAdapter.notifyItemInserted(i);
            }
        }
    }

    //리사이클러뷰의 아이템을 초기화해주는 함수
    public void clear() {
        int size = items.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                items.remove(0);
                foodSearchAdapter.notifyItemRemoved(0);

            }
        }
    }

    //알러지를 누르면 해당하는 알러지 정보를 사용자의 알러지 정보를 나타내는 리사이클러뷰에 추가한다
    @Override
    public void onItemClicked(FoodSearchItem myModel) {
        Toast.makeText(this, myModel.getFood_name(), Toast.LENGTH_SHORT).show();
        allergyitems.add(new AllergyItem(myModel.getFood_img(), myModel.getFood_name()));
        allergyrecyclerView.getAdapter().notifyDataSetChanged();

        addAllergy(myModel.getFood_name(), myModel.getFood_img());
    }

    //해당 알러지를 파이어베이스에 추가한다
    public void addAllergy(String name, int img) {
        AllergyFirebase allergyFirebase = new AllergyFirebase(name, img);
        databaseReference.child("user").child(userid).child("allergy").child(name).setValue(allergyFirebase);
    }

}