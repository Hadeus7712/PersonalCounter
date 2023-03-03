package com.example.personalcounter;

import static com.example.personalcounter.MainActivity.dateTimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.Date;

public class StatisticActivity extends AppCompatActivity {

    private CalendarView calendarView;
    DateTime dateTime;
    private TextView textView;
    private  TextView textSum;

    private TextView monthSum;

    private TextView textDays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);


        calendarView = findViewById(R.id.calendarView1);
        textView = findViewById(R.id.textStats);
        textSum = findViewById(R.id.textSum);

        monthSum = findViewById(R.id.monthText);

        textDays = findViewById(R.id.textDays);

        dateTimes = JSONHelper.importFromJSON(this, "data.json");

        dateTime = new DateTime(LocalDateTime.now());
        EachDayOutput(dateTime);
        ByTimeOutput(dateTime);
        monthSum.setText("Sum in " + Months.values()[dateTime.getMonth() - 1] + ": " + SumByMonth(dateTime));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                textView.setText("");
                textSum.setText("");
                textDays.setText("");

                dateTime = new DateTime(year, month + 1, day);

                EachDayOutput(dateTime);

                ByTimeOutput(dateTime);


                monthSum.setText("Sum in " + Months.values()[month] + ": " + SumByMonth(dateTime));

            }
        });
    }

    private int SumByMonth(DateTime dateTime){

        int result = 0;

        for(int i = 0; i< dateTimes.size(); i++){
            if(dateTimes.get(i).getYear() == dateTime.getYear() &&
                    dateTimes.get(i).getMonth() == dateTime.getMonth())
                result++;
        }

        return result;
    }

    private int SumByDay(DateTime dateTime){

        int result = 0;

        for(int i = 0; i < dateTimes.size(); i++){
            if(dateTimes.get(i).getYear() == dateTime.getYear() &&
                    dateTimes.get(i).getMonth() == dateTime.getMonth() &&
                    dateTimes.get(i).getDay() == dateTime.getDay())
                result++;
        }

        return result;
    }

    private void EachDayOutput(DateTime dateTime){

        for(int i = 0; i < 31; i++){
            textDays.append((i + 1) +") " + SumByDay(new DateTime(dateTime.getYear(), dateTime.getMonth(), 1 + i)) + "\n");
        }
    }

    private void ByTimeOutput(DateTime dateTime){

        int daySumCounter = 0;

        for(int i = 0; i < dateTimes.size(); i ++) {
            if (dateTime.getYear() == dateTimes.get(i).getYear() &&
                    dateTime.getMonth() == dateTimes.get(i).getMonth() &&
                    dateTime.getDay() == dateTimes.get(i).getDay()){
                daySumCounter++;
                textView.append(daySumCounter + ") " + dateTimes.get(i).getHour() + ":" + dateTimes.get(i).getMinute() + ":" + dateTimes.get(i).getSecond() + "\n");
            }

        }

        if(daySumCounter != 0){
            textSum.setText("---------------\n Total By Day:" + SumByDay(dateTime));
        }
    }

    public void Return(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }







}