package com.example.personalcounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static List<DateTime> dateTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isStoragePermissionGranted();

        dateTimes = new ArrayList<>();

        if(!isFilePresent(this, "data.json"))
            JSONHelper.exportToJSON(this, dateTimes);
    }

    public void OpenStatistics(View view){
        Intent intent = new Intent(this, StatisticActivity.class);
        startActivity(intent);
    }

    public void Add(View view){

        dateTimes = JSONHelper.importFromJSON(this);

        dateTimes.add(new DateTime(LocalDateTime.now()));

        JSONHelper.exportToJSON(this, dateTimes);

        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();

    }


    public void Reset(View view){
        dateTimes.clear();
        JSONHelper.exportToJSON(this, dateTimes);
    }


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
}