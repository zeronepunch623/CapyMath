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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class orderNumbers extends AppCompatActivity {

    @SuppressLint("UnsafeIntentLaunch")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_numbers);

        // View components used
        TextView tv_Instruction = findViewById(R.id.tv_instruction);
        TextView tv_Message = findViewById(R.id.tv_message);
        TextView tv_Option;
        Button bt_Delete = findViewById(R.id.bt_delete);
        Button bt_NextLevel = findViewById(R.id.bt_next);
        Button bt_Home = findViewById(R.id.bt_home);

        // Use list to store IDs of tv and iv (for simplifying code below)
        int[] tv_input_id = {R.id.tv_input1, R.id.tv_input2, R.id.tv_input3, R.id.tv_input4};
        int[] tv_option_id = {R.id.tv_option1, R.id.tv_option2, R.id.tv_option3, R.id.tv_option4};
        int[] ll_option_id = {R.id.ll_option1, R.id.ll_option2, R.id.ll_option3, R.id.ll_option4};
        int[][] iv_option_id = {
                {R.id.iv_option1_img1, R.id.iv_option1_img2, R.id.iv_option1_img3, R.id.iv_option1_img4, R.id.iv_option1_img5,
                        R.id.iv_option1_img6, R.id.iv_option1_img7, R.id.iv_option1_img8, R.id.iv_option1_img9, R.id.iv_option1_img10},
                {R.id.iv_option2_img1, R.id.iv_option2_img2, R.id.iv_option2_img3, R.id.iv_option2_img4, R.id.iv_option2_img5,
                        R.id.iv_option2_img6, R.id.iv_option2_img7, R.id.iv_option2_img8, R.id.iv_option2_img9, R.id.iv_option2_img10},
                {R.id.iv_option3_img1, R.id.iv_option3_img2, R.id.iv_option3_img3, R.id.iv_option3_img4, R.id.iv_option3_img5,
                        R.id.iv_option3_img6, R.id.iv_option3_img7, R.id.iv_option3_img8, R.id.iv_option3_img9, R.id.iv_option3_img10},
                {R.id.iv_option4_img1, R.id.iv_option4_img2, R.id.iv_option4_img3, R.id.iv_option4_img4, R.id.iv_option4_img5,
                        R.id.iv_option4_img6, R.id.iv_option4_img7, R.id.iv_option4_img8, R.id.iv_option4_img9, R.id.iv_option4_img10}
        };

        // Create random object for randomizing numbers and event
        Random random = new Random();

        // If true, user need to order number in ascending and vice versa
        double p = random.nextDouble();
        boolean isAsc = p < 0.5;       // if false, isDesc

        // Array list used for storing random numbers and user inputs
        final int arrSize = 4, bound = 11;
        ArrayList<Integer> numList = new ArrayList<>(arrSize);
        ArrayList<Integer> userInputList = new ArrayList<>(arrSize);

        // Get 4 random numbers and store them into numList
        for(int i = 0; i < arrSize; i++) {

            int randomNum;

            // Ensuring each number is different to each other
            do {

                randomNum = random.nextInt(bound);

            } while (numList.contains(randomNum));

            numList.add(randomNum);

        }

        // Set the instruction message and sort numList according to the result above
        if(isAsc){

            tv_Instruction.setText(R.string.instruction_Asc);
            numList.sort(null);

        }else{

            tv_Instruction.setText(R.string.instruction_Desc);
            numList.sort(Collections.reverseOrder());

        }

        // Create another array list to shuffle options
        ArrayList<Integer> randomNumList = new ArrayList<>(numList);
        Collections.shuffle(randomNumList);

        // Generate button for options, and show user inputs
        for(int i = 0; i < arrSize; i++) {

            // Get a random number from the shuffled options, with pictures to indicate the number
            int bt_Value = randomNumList.get(i);
            String numString = Integer.toString(bt_Value);

            // Find the latest unoccupied space to show option by using current index
            tv_Option = findViewById(tv_option_id[i]);
            tv_Option.setText(numString);

            // Find the latest unoccupied button to show option;
            LinearLayout ll_Option = findViewById(ll_option_id[i]);

            // Hide unwanted pictures to indicate number
            int startIndex = bound - 2;
            for(int j = startIndex; j >= bt_Value; j--){

                // Find unwanted pictures by using current index, hide it
                ImageView target_iv = findViewById(iv_option_id[i][j]);
                target_iv.setVisibility(View.INVISIBLE);

            }

            // Enable the option buttons to show user input and check result
            ll_Option.setOnClickListener(e -> {

                // Clear previous message
                tv_Message.setTextColor(Color.BLACK);
                tv_Message.setText("");

                // To check current number of user inputs
                int index = userInputList.size();

                // If user has done sorting and wish to attempt the question again
                if (index == arrSize) {

                    // Clear previous user inputs
                    userInputList.clear();
                    index = 0;

                    // Clear previous occupied space to show user inputs
                    for (int j = 0; j < arrSize; j++) {

                        TextView tv_UserInput = findViewById(tv_input_id[j]);
                        tv_UserInput.setText("");

                    }

                }

                // If user tries to pick option that is already picked, output message to remind
                if (userInputList.contains(bt_Value)){

                    tv_Message.setText(R.string.message_Repeat);
                    Toast.makeText(this, R.string.message_Repeat, Toast.LENGTH_SHORT).show();

                }
                // If user choose an unpicked option
                else {

                    // Get latest unoccupied space to show latest user input
                    TextView tv_UserInput = findViewById(tv_input_id[index]);
                    tv_UserInput.setText(numString);

                    // Record user input
                    userInputList.add(bt_Value);
                    index++;

                    // If user has done sorting, check result automatically
                    if (index == arrSize) {

                        // Check if user inputs are identical to sorted numList
                        boolean isCorrect = true;
                        for (int j = 0; j < arrSize; j++) {

                            if (!numList.get(j).equals(userInputList.get(j))) {
                                isCorrect = false;
                                break;
                            }

                        }

                        // Output message to show result
                        if (isCorrect){

                            tv_Message.setTextColor(Color.GREEN);
                            tv_Message.setText(R.string.message_Correct);
                            Toast.makeText(this, R.string.message_Correct, Toast.LENGTH_SHORT).show();

                        }else{

                            tv_Message.setTextColor(Color.RED);
                            tv_Message.setText(R.string.message_Wrong);
                            Toast.makeText(this, R.string.message_Wrong, Toast.LENGTH_SHORT).show();

                        }

                    }

                }

            });

        }

        // Delete latest user input and clear occupied space to show it
        bt_Delete.setOnClickListener(e -> {

            // Clear previous message
            tv_Message.setTextColor(Color.BLACK);
            tv_Message.setText("");

            // To check current number of user inputs
            int index = userInputList.size() - 1;

            // Check if user already pick any option
            if(index >= 0){

                // Delete latest user input
                userInputList.remove(index);

                // Look for the occupied space of the user input, clear it
                TextView tv_UserInput = findViewById(tv_input_id[index]);
                tv_UserInput.setText("");

            // User has not picked any option yet, output message to remind
            }else{

                tv_Message.setText(R.string.message_Empty);
                Toast.makeText(this, R.string.message_Empty, Toast.LENGTH_SHORT).show();

            }

        });

        // Generate another randomized event
        bt_NextLevel.setOnClickListener(e -> {

            Intent intent = getIntent();
            finish();
            startActivity(intent);

        });

        // Go back to home
        bt_Home.setOnClickListener(e -> {

            Intent intent = new Intent(orderNumbers.this, MainActivity.class);
            startActivity(intent);

        });

    }



}