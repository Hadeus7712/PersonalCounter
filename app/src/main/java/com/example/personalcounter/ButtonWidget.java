package com.example.personalcounter;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import static com.example.personalcounter.MainActivity.dateTimes;

import java.time.LocalDateTime;

/**
 * Implementation of App Widget functionality.
 */
public class ButtonWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

        }
    }
}