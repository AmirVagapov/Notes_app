package com.vagapov.amir.a2_l1_vagapov;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecycleViewAdapterNote extends RecyclerView.Adapter<RecycleViewAdapterNote.RecycleViewHolderNote> {


    private NavigatorNote mNavigatorNote;
    private List<Note> notes;
    private Context context;


    RecycleViewAdapterNote(Context context, List<Note> notes){
        this.context = context;
        this.notes = notes;
        mNavigatorNote = (NavigatorNote) context;
    }

    void setNotes(List<Note> notes){
        this.notes = notes;
    }



    Note getNote(int position){
        return notes.get(position);
    }

    @Override
    public RecycleViewHolderNote onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
        return new RecycleViewHolderNote(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderNote holder, int position) {
        holder.textViewTitle.setText(notes.get(position).getTitle());
        holder.getNoteFromAdapter(notes.get(position));
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    public ArrayList<Note> onItemMove(int fromPosition, int toPosition) {
        if(fromPosition < toPosition){
            for(int i = fromPosition; i < toPosition; i++){
                Collections.swap(notes, i, i + 1);
            }
        }else {
            for (int i = fromPosition; i < toPosition; i--) {
                Collections.swap(notes, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return (ArrayList<Note>) notes;
    }

    class RecycleViewHolderNote extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private Note note;


        private void getNoteFromAdapter(Note note){
            this.note = note;
        }

        private RecycleViewHolderNote(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.list_item_text_view);
            itemView.findViewById(R.id.ripple).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNavigatorNote.openNote(note.getUUID());

                }
            });
        }
    }
}
