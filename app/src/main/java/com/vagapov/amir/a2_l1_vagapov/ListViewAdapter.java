package com.vagapov.amir.a2_l1_vagapov;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vagapov.amir.a2_l1_vagapov.widget.NotesWidgetService;

import java.util.List;


public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private  List<Note> mNotes;
    private TextView listTextView;



    ListViewAdapter(Context context, List<Note> notes) {
        mContext = context;
        mNotes = notes;
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }

    @Override
    public Object getItem(int i) {
        return mNotes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    void refreshAdapter(){
        notifyDataSetChanged();
    }


    Note getNote(int position){
        return mNotes.get(position);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_view, viewGroup,
                    false);
        }
        listTextView = view.findViewById(R.id.list_item_text_view);
        listTextView.setText(mNotes.get(i).getTitle());

        return view;
    }

    void setNotes(List<Note> notes) {
        mNotes = notes;
    }
}
