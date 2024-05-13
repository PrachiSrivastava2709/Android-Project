package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Calendar;

public class scheduleForm extends AppCompatActivity {
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    int hour, minute;
    EditText medicineName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);

        alarmTimePicker = findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    // OnToggleClicked() method is implemented the time functionality
    public void OnToggleClicked(View view) {
        long time;
        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(scheduleForm.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            hour = alarmTimePicker.getHour();
            minute = alarmTimePicker.getMinute();

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            Intent intent = new Intent(this, AlarmReceiver.class);

            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);

            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (Calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }

            // Alarm rings continuously until toggle button is turned off
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
            // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(scheduleForm.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
    }

    public void addTime(View view){
        medicineName = findViewById(R.id.medName);
        String medName = medicineName.getText().toString();
        Toast.makeText(this, medName, Toast.LENGTH_SHORT).show();
        if (medName.matches("")){
            Toast.makeText(getApplicationContext(), "Enter Medicine Name", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("Medicine name", medName);
            i.putExtra("Time", hour + " : " + minute);
            startActivity(i);
        }
    }
}