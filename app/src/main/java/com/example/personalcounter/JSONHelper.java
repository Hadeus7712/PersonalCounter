package com.example.personalcounter;

import android.content.Context;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

public class JSONHelper {

    private static final String FILE_NAME = "data.json";

    static void exportToJSON(Context context, List<DateTime> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setDateTimes(dataList);
        String jsonString = gson.toJson(dataItems);

        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static List<DateTime> importFromJSON(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems.getDateTimes();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    private static class DataItems {
        private List<DateTime> dateTimes;

        List<DateTime> getDateTimes() {
            return dateTimes;
        }
        void setDateTimes(List<DateTime> dateTimes) {
            this.dateTimes = dateTimes;
        }
    }
}
