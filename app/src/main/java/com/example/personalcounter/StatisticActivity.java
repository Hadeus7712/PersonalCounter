package com.example.personalcounter;

import static com.example.personalcounter.MainActivity.dateTimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView textView;
    private  TextView textSum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);


        calendarView = findViewById(R.id.calendarView1);
        textView = findViewById(R.id.textStats);
        textSum = findViewById(R.id.textSum);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                textView.setText("");
                textSum.setText("");

                int year1 = year;
                int month1 = month + 1;
                int day1 = day;

                int count = 0;

                for(int i = 0; i < dateTimes.size(); i ++){
                    if(year1 == dateTimes.get(i).getYear() &&
                            month1 == dateTimes.get(i).getMonth() &&
                            day1 == dateTimes.get(i).getDay()) {
                        textView.append(dateTimes.get(i).getHour() + ":" + dateTimes.get(i).getMinute() + ":" + dateTimes.get(i).getSecond() + "\n");
                        count++;
                    }
                }

                if(count != 0){
                    textSum.setText("" + count);
                }

            }
        });
    }

    public void Return(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}