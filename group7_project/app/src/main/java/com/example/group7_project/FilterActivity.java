package com.example.group7_project;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class FilterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    public static final String EXTRA_SUSFILTER =
            "com.example.group7_project.EXTRA_SUSFILTER";
    ImageButton button_back;
    SwitchCompat sus_toggle;
    private boolean sus_checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        button_back = findViewById(R.id.button_filter_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyFilter(sus_checked);
            }
        });

        sus_toggle = findViewById(R.id.toggle_filter_sus);
        sus_toggle.setOnCheckedChangeListener(this);

        if (getIntent().getBooleanExtra(MainActivity.EXTRA_MAIN_SUSFILTER, false)) {
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
            sus_toggle.setChecked(true);
        }

    }

    private void applyFilter(boolean isSus_checked) {
        Intent data = new Intent();
        data.putExtra(EXTRA_SUSFILTER, isSus_checked);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            sus_checked = true;
        } else {
            sus_checked = false;
        }
    }
}