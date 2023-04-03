package com.oxymium.realestatemanager.misc;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.oxymium.realestatemanager.R;

// --------------------------
// MainActivity (DEFENCE ONLY)
// --------------------------

public class MainActivity extends AppCompatActivity {

    private TextView textViewMain;
    private TextView textViewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        ISSUE#1: Wrong ID reference
        SOLUTION: Replace with the correct TextView ID reference
            From
        this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
            To
        */
        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main);

        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

        this.configureTextViewMain();
        this.configureTextViewQuantity();
    }

    private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        // Modified to String resource reference for convenience
        this.textViewMain.setText(R.string.defence_string);
    }

    private void configureTextViewQuantity(){
        int quantity = Utils.convertDollarToEuro(100);
        this.textViewQuantity.setTextSize(20);

        /*
        ISSUE#2: setText() method requires a String object. Previously, was trying to setText with an int value
        SOLUTION: transform the int value into a String object with valueOf() method from JAVA's String class
            From
        this.textViewQuantity.setText(quantity);
            To
        */
        this.textViewQuantity.setText(String.valueOf(quantity));
    }

}
