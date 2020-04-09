package com.innovvscript.stamurairating;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.innovvscript.stamurairating.db.DbHandler;
import com.innovvscript.stamurairating.db.HistoryItem;
import java.text.SimpleDateFormat;

public class SliderActivity extends AppCompatActivity {

    private TextView rating;
    private SharedPreferences prefs;
    private int min, max;
    private int currentRating;
    private AppCompatSeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sider);
        rating = findViewById(R.id.rating);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        min = prefs.getInt("MIN", 0);
        max = prefs.getInt("MAX", 9);
        rating.setText("Rating: " + min);       // Rating text to show currently selected rating
        setUpSlider();
    }

//    On history clicked
    public void onHistory(View v){
     startActivity(new Intent(this, HistoryActivity.class));
    }

//    On submit clicked
    public void submit(View v){
        HistoryItem item = new HistoryItem();
        item.setRating(currentRating + " (" + min + "-" + max + ")");
        item.setTime(getCurrentTimeFormatted());
        new DbHandler(this).addHistoryItem(item);
        seekBar.setProgress(min);       // Reset slider
        Toast.makeText(this, "Rating Submitted !", Toast.LENGTH_SHORT).show();
    }

    public String getCurrentTimeFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a, MMM dd yyyy");
        return formatter.format(System.currentTimeMillis());
    }

//    Change range
    public void onChangeRange(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.change_alert, null);
        builder.setView(view);
        final AlertDialog optionsDialog = builder.create();
        final AutoCompleteTextView min = view.findViewById(R.id.min);
        final AutoCompleteTextView max = view.findViewById(R.id.max);
        min.setText(String.valueOf(this.min));
        max.setText(String.valueOf(this.max));
        TextView save = view.findViewById(R.id.save);
        TextView cancel = view.findViewById(R.id.cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minInt = Integer.parseInt(min.getText().toString());
                int maxInt = Integer.parseInt(max.getText().toString());
                if(maxInt > 9) {
                    Toast.makeText(SliderActivity.this, "Max cannot be greater than 9", Toast.LENGTH_SHORT).show();
                    return;
                }else if(minInt >= maxInt){
                    Toast.makeText(SliderActivity.this, "Invalid range", Toast.LENGTH_SHORT).show();
                    return;
                }
//               Apply changes
                prefs.edit().putInt("MIN", minInt)
                        .putInt("MAX", maxInt).apply();
                SliderActivity.this.min = minInt;
                SliderActivity.this.max = maxInt;
                seekBar.setMax(maxInt);
                setMin();

                optionsDialog.hide();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsDialog.hide();
            }
        });
        optionsDialog.show();
    }

    private void setMin(){
        if(Build.VERSION.SDK_INT >= 26)
            seekBar.setMin(min);
        else{ seekBar.setProgress(min); }
    }

    private void setUpSlider(){
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(max);
        setMin();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rating.setText("Rating: " + progress);
                currentRating = progress;
                if(Build.VERSION.SDK_INT < 26)
                    if(progress < min)
                        seekBar.setProgress(min);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
