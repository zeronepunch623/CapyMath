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

public class composeNumbers extends AppCompatActivity {

    @SuppressLint("UnsafeIntentLaunch")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_numbers);

        // View components used
        TextView tv_RandomNum = findViewById(R.id.tv_randomNum);
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

        // Array list used for storing random numbers and user inputs
        final int numOptions = 4, numInput = 2, bound = 10;
        ArrayList<Integer> options = new ArrayList<>(numOptions);
        ArrayList<Integer> userInputList = new ArrayList<>(numInput);

        // Get a random number to be composed, with pictures indicating the number
        int randomNum = random.nextInt(bound) + 1;
        String randomNumString = Integer.toString(randomNum);

        // Set the instruction message and random number
        tv_Instruction.setText(R.string.instruction_Compose);
        StringBuilder numPic = new StringBuilder().append(randomNumString).append("\n");

        for(int j = 0; j < randomNum; j++)
            numPic.append("* ");

        tv_RandomNum.setText(numPic);

        // Generate answers according to the random number to be composed
        int option_Buffer;

        do{

            option_Buffer = random.nextInt(randomNum);

        }while(option_Buffer == randomNum);

        // Number to be composed = option_Buffer + diff (answers)
        int diff = randomNum - option_Buffer;
        options.add(option_Buffer);
        options.add(diff);

        // Get 2 more random numbers for options
        for(int i = 0; i < numOptions - numInput; i++){

            // Ensuring each number is different to each other
            do{

                option_Buffer = random.nextInt(10);

            }while(options.contains(option_Buffer));

            options.add(option_Buffer);

        }

        // Shuffle options to avoid answers always at first 2 options
        Collections.shuffle(options);

        // Generate button for options, and show user inputs
        for(int i = 0; i < numOptions; i++){

            // Get a random number from the shuffled options, with pictures to indicate the number
            int bt_Value = options.get(i);
            String numString = Integer.toString(bt_Value);

            // Find the latest unoccupied space to show option by using current index
            tv_Option = findViewById(tv_option_id[i]);
            tv_Option.setText(numString);

            // Find the latest unoccupied button to show option;
            LinearLayout ll_Option = findViewById(ll_option_id[i]);

            // Hide unwanted pictures to indicate number
            int startIndex = bound - 1;
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
                if(index == numInput) {

                    // Clear previous user inputs
                    userInputList.clear();
                    index = 0;

                    // Clear previous occupied space to show user inputs
                    for (int j = 0; j < numInput; j++) {

                        TextView tv_UserInput = findViewById(tv_input_id[j]);
                        tv_UserInput.setText("");

                    }

                }

                // Get latest unoccupied space to show latest user input
                TextView tv_UserInput = findViewById(tv_input_id[index]);
                tv_UserInput.setText(numString);

                // Record user input
                userInputList.add(bt_Value);
                index++;

                // If user has done sorting, check result automatically
                if (index == numInput) {

                    // Check if user inputs can compose to the random number
                    int userInputSum = 0;
                    for(int j = 0; j < numInput; j++)
                        userInputSum += userInputList.get(j);

                    boolean isCorrect = (userInputSum == randomNum);

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

            Intent intent = new Intent(composeNumbers.this, MainActivity.class);
            startActivity(intent);

        });

    }

}