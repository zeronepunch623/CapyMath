package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceHeight = displayMetrics.heightPixels;
        int deviceWidth = displayMetrics.widthPixels;
        int targetHeight, targetWidth, marginLeft, marginTop, marginRight;

        ImageView iv_logo = findViewById(R.id.home_logo);

        targetHeight = deviceHeight / 3;
        marginTop = deviceHeight / 9;
        marginLeft = marginRight = deviceWidth / 8;

        LinearLayout.LayoutParams iv_logo_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, targetHeight);
        iv_logo_params.setMargins(marginLeft, marginTop, marginRight, 0);
        iv_logo.setLayoutParams(iv_logo_params);

        LinearLayout ll_button = findViewById(R.id.ll_button);
        LinearLayout.LayoutParams ll_button_params = (LinearLayout.LayoutParams) ll_button.getLayoutParams();
        targetWidth = (int) (deviceWidth * 0.7);

        ll_button_params.width = targetWidth;
        ll_button_params.setMargins(marginLeft, marginTop, marginRight, 0);
        ll_button.setLayoutParams(ll_button_params);

        Button bt_CompareNumbers = findViewById(R.id.bt_compare);
        Button bt_OrderNumbers = findViewById(R.id.bt_order);
        Button bt_ComposeNumbers = findViewById(R.id.bt_compose);

        bt_CompareNumbers.setOnClickListener(e -> {

            Intent intent = new Intent(MainActivity.this, compareNumbers.class);
            startActivity(intent);

        });

        bt_OrderNumbers.setOnClickListener(e -> {

            Intent intent = new Intent(MainActivity.this, orderNumbers.class);
            startActivity(intent);

        });

        bt_ComposeNumbers.setOnClickListener(e -> {

            Intent intent = new Intent(MainActivity.this, composeNumbers.class);
            startActivity(intent);

        });

    }

}