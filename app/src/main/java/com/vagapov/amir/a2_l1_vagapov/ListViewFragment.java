package com.vagapov.amir.a2_l1_vagapov;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.vagapov.amir.a2_l1_vagapov.NoteList.getNoteList;


public class ListViewFragment extends Fragment {

    private NavigatorNote navigator;
    private RecycleViewAdapterNote adapter;
    private List<Note> noteList;
    private SparseBooleanArray sba;
    private MenuItem openItem;
    private MenuItem editItem;
    private MenuItem deleteItem;
    private TextView textViewAddNote;
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (NavigatorNote) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_fragment, container, false);

        initUI(view);

        updateAdapter();

        setItemHelper();

        addNoteBtn();

        return view;
    }

    private void showSnackBar(Note note) {

        final Note mNote = note;
        Snackbar.make(getView(), getString(R.string.snackbar_delete_note, note.getTitle()),
                Snackbar.LENGTH_LONG).setAction(R.string.confirm, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteList.getNoteList(getContext()).deleteNote(mNote.getUUID());
                updateAdapter();
            }
        }).show();

    }

    private void setItemHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                NoteList.getNoteList(getContext()).moveNote(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Note note = adapter.getNote(viewHolder.getAdapterPosition());
                ArrayList<Note> notes = (ArrayList<Note>) getNoteList(getContext()).getNotes();
                notes.remove(viewHolder.getAdapterPosition());

                showSnackBar(note);
                mRecyclerView.setAdapter(adapter);
                adapter.setNotes(notes);
                adapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void addNoteBtn() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.createNote();
            }
        });
    }


    private void updateAdapter() {

        Log.d("MyLog", "update");
        noteList = getNoteList(getContext()).getNotes();
        if (adapter == null) {
            adapter = new RecycleViewAdapterNote(getContext(), noteList);
            mRecyclerView.setAdapter(adapter);
        } else {
            mRecyclerView.setAdapter(adapter);
            adapter.setNotes(noteList);
            adapter.notifyDataSetChanged();
        }
        textViewSetVisible();
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab = view.findViewById(R.id.fab);
        textViewAddNote = view.findViewById(R.id.text_view_add_note);
        textViewSetVisible();
    }

    private void textViewSetVisible() {
        if (noteList == null || noteList.isEmpty()) {
            textViewAddNote.setVisibility(View.VISIBLE);
        } else {
            textViewAddNote.setVisibility(View.INVISIBLE);
        }
    }


    private void ItemSetVisible() {
        if (sba == null || sba.size() < 1) {
            deleteItem.setVisible(false);
            openItem.setVisible(false);
            editItem.setVisible(false);
        } else {
            deleteItem.setVisible(true);
            int sbaCountTrue = 0;
            for (int i = 0; i < sba.size(); i++) {
                int key = sba.keyAt(i);
                if (sba.get(key)) {
                    sbaCountTrue++;
                }
            }
            if (sbaCountTrue == 0) {
                deleteItem.setVisible(false);
            }
            if (sbaCountTrue != 1) {
                openItem.setVisible(false);
                editItem.setVisible(false);
            } else {
                openItem.setVisible(true);
                editItem.setVisible(true);
            }
        }
    }


    private void findMenuItem(Menu menu) {
        editItem = menu.findItem(R.id.edit_note);
        openItem = menu.findItem(R.id.open_note);
        deleteItem = menu.findItem(R.id.delete_notes);
    }


    private boolean setIMenuItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_note:
                itemOpenNote();
                return true;
            case R.id.edit_note:
                itemEditNote();
                return true;
            case R.id.delete_notes:
                itemDeleteNotes();
                return true;
            default:
                return false;
        }
    }

    private void itemEditNote() {
        for (int i = noteList.size(); i >= 0; i--) {
            if (sba.get(i)) {
                navigator.editNote(adapter.getNote(i).getUUID());
            }
        }
        refreshDataMenu();
    }

    private void itemDeleteNotes() {
        for (int i = noteList.size() - 1; i >= 0; i--) {
            if (sba.get(i)) {
                getNoteList(getContext()).deleteNote(adapter.getNote(i).getUUID());
            }
        }
        adapter.notifyDataSetChanged();
        updateAdapter();
        refreshDataMenu();
    }

    private void refreshDataMenu() {
        sba.clear();
        sba = null;
        ItemSetVisible();
    }

    private void itemOpenNote() {
        for (int i = noteList.size(); i >= 0; i--) {
            if (sba.get(i)) {
                navigator.openNote(adapter.getNote(i).getUUID());
            }
        }
        refreshDataMenu();
    }

}
