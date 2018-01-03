package com.vagapov.amir.a2_l1_vagapov.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.vagapov.amir.a2_l1_vagapov.MainActivity;
import com.vagapov.amir.a2_l1_vagapov.Note;
import com.vagapov.amir.a2_l1_vagapov.NoteList;
import com.vagapov.amir.a2_l1_vagapov.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class NotesWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetNotesFactory(this.getApplicationContext(), intent);
    }


    public class WidgetNotesFactory implements RemoteViewsService.RemoteViewsFactory {

        Context context;
        private List<Note> mNoteList;

        WidgetNotesFactory(Context context, Intent intent){
            this.context = context;
        }

        @Override
        public void onCreate() {
            mNoteList = new ArrayList<>();
        }

        @Override
        public void onDataSetChanged() {
            mNoteList = NoteList.getNoteList(context).getNotes();
        }


        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mNoteList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            rv.setTextViewText(R.id.text_view_widget_item, mNoteList.get(i).getTitle());

            Intent intentDelete = new Intent(context, WidgetNotes.class);
            intentDelete.putExtra(WidgetNotes.ID_EXTRA, mNoteList.get(i).getUUID());
            intentDelete.setType(WidgetNotes.DELETE_NOTE);
            rv.setOnClickFillInIntent(R.id.widget_list_item_delete, intentDelete);

            Intent intentText = new Intent(context, MainActivity.class);
            intentText.putExtra(WidgetNotes.ID_EXTRA, mNoteList.get(i).getUUID());
            rv.setOnClickFillInIntent(R.id.widget_list_item_edit, intentText);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
