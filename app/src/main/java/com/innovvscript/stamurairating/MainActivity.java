package com.innovvscript.stamurairating;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialButton button = findViewById(R.id.button);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String s = "Rating " + prefs.getInt("MIN", 0) + "-" + prefs.getInt("MAX", 9);
        button.setText(s);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SliderActivity.class));
            }
        });
    }
}
