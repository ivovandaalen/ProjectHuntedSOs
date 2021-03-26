package com.example.hunted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hunted.police.PoliceActivity;
import com.example.hunted.thieves.ThievesActivity;

//First activity screen with police/thieves choice
public class MainActivity extends AppCompatActivity {
    Button buttonPolice;
    Button buttonThieves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPolice = (Button) findViewById(R.id.button_police);
        buttonThieves = (Button) findViewById(R.id.button_thieves);

        buttonPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPoliceActivity();
            }
        });
        buttonThieves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openThievesActivity();
            }
        });
    }

    public void openPoliceActivity(){
        Intent intent = new Intent(this, PoliceActivity.class);
        startActivity(intent);
    }

    public void openThievesActivity(){
        Intent intent = new Intent(this, ThievesActivity.class);
        startActivity(intent);
    }
}
