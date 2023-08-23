package com.example.personalcounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import android.Manifest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static List<DateTime> dateTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isStoragePermissionGranted()){
            onDestroy();
        }

        dateTimes = new ArrayList<>();

        if(!isFilePresent(this, "data.json"))
            JSONHelper.exportToJSON(this, dateTimes, "data.json");

        dateTimes = JSONHelper.importFromJSON(this, "data.json");

        LastToday();
        SumToday();


    }

    @Override
    protected void onResume() {
        super.onResume();

        LastToday();
        SumToday();
    }

    private void LastToday(){
        TextView lastToday = findViewById(R.id.lastToday);


        DateTime now;
        DateTime last;

        if(dateTimes.size() > 0){
            last = dateTimes.get(dateTimes.size() - 1);
        }
        else{
            last = new DateTime(0, 0, 0);
        }


        now = new DateTime(LocalDateTime.now());

        if(last.getYear() == now.getYear() &&
                last.getMonth() == now.getMonth() &&
                last.getDay() == now.getDay()){
            lastToday.setText("Last time: " + last.getHour() + ":" + last.getMinute() + "." + last.getSecond());
        }
        else{
            lastToday.setText("Not yet");
        }

        //Log.v("test", "" + dateTimes.get(dateTimes.size() - 1));
    }

    private void SumToday(){
        TextView sumToday = findViewById(R.id.todaySum);

        DateTime now = new DateTime(LocalDateTime.now());

        int sum = 0;

        for (int i = 0; i < dateTimes.size(); i++) {
            if(now.getYear() == dateTimes.get(i).getYear() &&
                now.getMonth() == dateTimes.get(i).getMonth() &&
                now.getDay() == dateTimes.get(i).getDay()){
                sum++;
            }
        }

        sumToday.setText("Today: " + sum);

    }

    public void OpenStatistics(View view){
        Intent intent = new Intent(this, StatisticActivity.class);
        startActivity(intent);
    }

    public void Add(View view){

        dateTimes = JSONHelper.importFromJSON(this, "data.json");

        dateTimes.add(new DateTime(LocalDateTime.now()));

        JSONHelper.exportToJSON(this, dateTimes, "data.json");
        JSONHelper.exportToJSON(this, dateTimes, "backup.json");

        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();

        LastToday();
        SumToday();


    }


/*    public void ResetData(){
        dateTimes.clear();
        JSONHelper.exportToJSON(this, dateTimes, "data.json");
    }

    public void Reset(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ResetData();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/


    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }



    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Ad","Permission is granted");
                return true;
            } else {

                Log.v("Ad","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Ad","Permission is granted");
            return true;
        }
    }

    public void OpenSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}