package com.vagapov.amir.a2_l1_vagapov.widget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.vagapov.amir.a2_l1_vagapov.MainActivity;
import com.vagapov.amir.a2_l1_vagapov.NoteList;
import com.vagapov.amir.a2_l1_vagapov.R;

import java.util.UUID;

public class WidgetNotes extends AppWidgetProvider {


    public static final String UPDATE_WIDGET_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ITEM_ON_CLICK_ACTION = "android.appwidget.action.ITEM_ON_CLICK";
    public static final String ID_EXTRA = "id";
    public static final String DELETE_NOTE = "deleteNote";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int i :appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }

    }



    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = manager.getAppWidgetIds(new ComponentName(context, WidgetNotes.class));
        if(intent.getAction().equalsIgnoreCase(UPDATE_WIDGET_ACTION)){
            manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        }
        if(intent.getAction().equalsIgnoreCase(ITEM_ON_CLICK_ACTION)){
            UUID id = (UUID) intent.getSerializableExtra(ID_EXTRA);
            Intent activityIntent = new Intent(context, MainActivity.class);
            if(intent.getType() == null) {
                activityIntent.putExtra(ID_EXTRA, id);
                activityIntent.setType(MainActivity.OPEN_MAIN_ACTIVITY_EDIT);
                context.startActivity(activityIntent);
            }
            if(intent.getType() != null && intent.getType().equalsIgnoreCase(DELETE_NOTE)) {
                NoteList.getNoteList(context).deleteNote(id);
                manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
            } else if(intent.getType() != null && intent.getType().equalsIgnoreCase(MainActivity.ADD_NOTE)){
                activityIntent.setType(MainActivity.ADD_NOTE);
                context.startActivity(activityIntent);
            }
        }

        super.onReceive(context, intent);
        onUpdate(context, manager, appWidgetIds);
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        setAddClick(context, remoteViews);
        setList(remoteViews, context, appWidgetId);
        setListClick(remoteViews, context);
        refreshList(context, remoteViews);
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_view);
    }

    private void setAddClick(Context context, RemoteViews remoteViews) {
        Intent intent = new Intent(context, WidgetNotes.class);
        intent.setType(MainActivity.ADD_NOTE);
        intent.setAction(ITEM_ON_CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_image_btn_add, pendingIntent);
    }

    private void refreshList(Context context, RemoteViews remoteViews) {
        Intent intent = new Intent(context, WidgetNotes.class);
        intent.setAction(UPDATE_WIDGET_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.refresh_list_widget, pendingIntent);
    }


    private void setList(RemoteViews remoteViews, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, NotesWidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        remoteViews.setRemoteAdapter(R.id.widget_list_view, adapter);
        remoteViews.setEmptyView(R.id.widget_list_view, R.id.text_view_widget_empty);
    }

    void setListClick(RemoteViews remoteViews, Context context){
        Intent intent = new Intent(context, WidgetNotes.class);
        intent.setAction(ITEM_ON_CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setPendingIntentTemplate(R.id.widget_list_view, pendingIntent);
    }
}
