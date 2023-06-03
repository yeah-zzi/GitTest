package yeji.mjc.gittest.comunity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import yeji.mjc.gittest.R;

public class Comm_kind extends AppCompatActivity implements View.OnClickListener {

    TextView life,tip;

    String com_kind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_community_kind);

        //뒤 배경을 반투명하게 하려고
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true);
            window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
            window.setStatusBarColor(Color.parseColor("#80000000"));
        }
        overridePendingTransition(0,0);

        life = findViewById(R.id.life);
        tip = findViewById(R.id.tip);

        //전페이지에서 커뮤니티 이름을 받아온다
        Intent dateIntent = getIntent();
        com_kind = dateIntent.getStringExtra("커뮤니티 종류");

        if(com_kind.equals("생활정보")){
            life.setTextColor(Color.parseColor("#FADA80"));
            tip.setTextColor(Color.GRAY);
        }else{
            life.setTextColor(Color.GRAY);
            tip.setTextColor(Color.parseColor("#FADA80"));
        }

        life.setOnClickListener(this);
        tip.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.life:
                com_kind = "생활정보";
                life.setTextColor(Color.parseColor("#FADA80"));
                tip.setTextColor(Color.GRAY);
                break;
            case R.id.tip:
                com_kind = "이거어때";
                life.setTextColor(Color.GRAY);
                tip.setTextColor(Color.parseColor("#FADA80"));
                break;
        }
        Intent kindIntent = new Intent(getApplicationContext(), PlusComm.class);
        kindIntent.putExtra("커뮤니티 종류", com_kind);
        setResult(RESULT_OK,kindIntent);

        finish();
    }
}
