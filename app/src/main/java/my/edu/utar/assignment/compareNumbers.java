package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class compareNumbers extends AppCompatActivity {

    @SuppressLint("UnsafeIntentLaunch")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_numbers);

        // View components used
        TextView tv_Instruction = findViewById(R.id.tv_instruction);
        TextView tv_Message = findViewById(R.id.tv_message);
        TextView tv_Input;
        ImageView iv_Option;
        LinearLayout ll_Option1 = findViewById(R.id.ll_option1);
        LinearLayout ll_Option2 = findViewById(R.id.ll_option2);
        Button bt_NextLevel = findViewById(R.id.bt_next);
        Button bt_Home = findViewById(R.id.bt_home);

        // Use list to store IDs of tv and iv (for simplifying code below)
        int[] tv_option_id = {R.id.tv_option1, R.id.tv_option2};
        int[][] iv_option_id = {
                {R.id.iv_option1_img1, R.id.iv_option1_img2, R.id.iv_option1_img3, R.id.iv_option1_img4, R.id.iv_option1_img5,
                        R.id.iv_option1_img6, R.id.iv_option1_img7, R.id.iv_option1_img8, R.id.iv_option1_img9, R.id.iv_option1_img10},
                {R.id.iv_option2_img1, R.id.iv_option2_img2, R.id.iv_option2_img3, R.id.iv_option2_img4, R.id.iv_option2_img5,
                        R.id.iv_option2_img6, R.id.iv_option2_img7, R.id.iv_option2_img8, R.id.iv_option2_img9, R.id.iv_option2_img10}
        };

        // Create random object for randomizing numbers and event
        Random random = new Random();

        // If true, user need to choose greater number and vice versa
        double p = random.nextDouble();
        boolean isGreater = p < 0.5;

        // Set the instruction message according to the result above
        if(isGreater)
            tv_Instruction.setText(R.string.instruction_More);
        else
            tv_Instruction.setText(R.string.instruction_Less);

        // Random number with pictures to indicate the numbers
        final int numOption = 2, bound = 11;
        int[] num = new int[numOption];
        int num2_buffer;

        // Get first random number
        num[0] = random.nextInt(bound);

        // Get second random number that is different from the first number
        do{

            num2_buffer = random.nextInt(bound);

        }while(num[0] == num2_buffer);

        num[1] = num2_buffer;

        // Generate pictures according to the number
        for(int i = 0; i < numOption; i++){

            // Get current number
            int currentNum = num[i];
            String numString = Integer.toString(currentNum);

            // Find the latest unoccupied space to show option by using current index
            tv_Input = findViewById(tv_option_id[i]);
            tv_Input.setText(numString);

            // Hide unwanted pictures to indicate number
            int startIndex = bound - 2;
            for(int j = startIndex; j >= currentNum; j--){

                // Find unwanted pictures by using current index, hide it
                iv_Option = findViewById(iv_option_id[i][j]);
                iv_Option.setVisibility(View.INVISIBLE);

            }

        }

        // Enable the option buttons to check result
        ll_Option1.setOnClickListener(e -> { checkAnswer(isGreater, num[0], num[1], tv_Message);});
        ll_Option2.setOnClickListener(e -> { checkAnswer(isGreater, num[1], num[0], tv_Message);});

        // Generate another randomized event
        bt_NextLevel.setOnClickListener(e -> {

            Intent intent = getIntent();
            finish();
            startActivity(intent);

        });

        // Go back to home
        bt_Home.setOnClickListener(e -> {

            Intent intent = new Intent(compareNumbers.this, MainActivity.class);
            startActivity(intent);

        });

    }

    // Check result function enabled by the buttons
    private void checkAnswer(boolean isGreater, int num1, int num2, TextView tv_Message){

        // Check if this event is to choose greater or smaller number, and determine whether user pick the right option
        if(isGreater && num1 > num2 || !isGreater && num1 < num2){

            tv_Message.setTextColor(Color.GREEN);
            tv_Message.setText(R.string.message_Correct);
            Toast.makeText(this, R.string.message_Correct, Toast.LENGTH_SHORT).show();

        // If user pick the wrong option
        }else{

            tv_Message.setTextColor(Color.RED);
            tv_Message.setText(R.string.message_Wrong);
            Toast.makeText(this, R.string.message_Wrong, Toast.LENGTH_SHORT).show();

        }

    }

}