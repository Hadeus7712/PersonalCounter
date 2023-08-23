package com.example.personalcounter;

import static com.example.personalcounter.MainActivity.dateTimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

        textDays.setText("");
        for(int i = 0; i < 31; i++){

            int sum = SumByDay(new DateTime(dateTime.getYear(), dateTime.getMonth(), 1 + i));

            if(sum != 0)
                textDays.append((i + 1) +") " + sum + "\n");
        }
    }

    private void ByTimeOutput(DateTime dateTime){

        int daySumCounter = 0;
        textView.setText("");

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
        finish();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }



    public List GetCurrentTimes(DateTime dateTime){
        List<DateTime> result = new ArrayList<>();

        int count = 0;

        for(int i = dateTimes.size() - 1; i >=  0 ; i--){
            if(dateTimes.get(i).getYear() == dateTime.getYear() &&
                    dateTimes.get(i).getMonth() == dateTime.getMonth() &&
                    dateTimes.get(i).getDay() == dateTime.getDay()){
                result.add(dateTimes.get(i));
            }
        }

        Collections.reverse(result);

        return result;
    }


    public void DeleteFunc(View view){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_layout);
        dialog.show();

        List<DateTime> currentTimes = GetCurrentTimes(dateTime);


        Button button = dialog.findViewById(R.id.closeDeleteLayoutButton);

        ListView listView = dialog.findViewById(R.id.deleteLayoutListView);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentTimes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int position, long l) {

                DateTime dateTime1 = currentTimes.get(position);

                List<DateTime> result = JSONHelper.importFromJSON(getBaseContext(), "data.json");
                currentTimes.remove(dateTime1);
                result.remove(dateTime1);
                JSONHelper.exportToJSON(getBaseContext(), result, "data.json");

                adapter.notifyDataSetChanged();
                dateTimes = JSONHelper.importFromJSON(getBaseContext(), "data.json");

                EachDayOutput(dateTime);
                ByTimeOutput(dateTime);
                monthSum.setText("Sum in " + Months.values()[dateTime.getMonth() - 1] + ": " + SumByMonth(dateTime));


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }



}