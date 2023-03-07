package com.example.personalcounter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import static com.example.personalcounter.MainActivity.dateTimes;

import java.time.LocalDateTime;

/**
 * Implementation of App Widget functionality.
 */
public class ButtonWidget extends AppWidgetProvider {

    private static final String SYNC_CLICKED = "WidgetClick";
    private static int count = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        ComponentName componentName = new ComponentName(context, ButtonWidget.class);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.button_widget);
        remoteViews.setOnClickPendingIntent(R.id.imageView, getPendingSelfIntent(context, SYNC_CLICKED));

        appWidgetManager.updateAppWidget(componentName, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(SYNC_CLICKED.equals(intent.getAction())){

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.button_widget);
            remoteViews.setImageViewResource(R.id.imageView, R.drawable.pngegg2);




            ComponentName componentName = new ComponentName(context, ButtonWidget.class);
            appWidgetManager.updateAppWidget(componentName, remoteViews);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }


            remoteViews.setImageViewResource(R.id.imageView, R.drawable.pngegg);
            appWidgetManager.updateAppWidget(componentName, remoteViews);

            dateTimes = JSONHelper.importFromJSON(context, "data.json");

            dateTimes.add(new DateTime(LocalDateTime.now()));

            JSONHelper.exportToJSON(context, dateTimes, "data.json");
            JSONHelper.exportToJSON(context, dateTimes, "backup.json");

            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
        }

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action){
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}