package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarm = findViewById(R.id.alarm);
        showAlarm();
    }

    public void goToForm(View view){
        Intent i = new Intent(getApplicationContext(), scheduleForm.class);
        startActivity(i);
    }

    public void showAlarm(){
        Intent intent = getIntent();
        String medName = intent.getStringExtra("Medicine name");
        String time = intent.getStringExtra("Time");
        if (medName != null & time != null){
        alarm.setText(medName + "     " + time);
        }
    }
}