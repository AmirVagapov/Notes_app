package com.vagapov.amir.a2_l1_vagapov;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.UUID;

import static com.vagapov.amir.a2_l1_vagapov.NoteList.getNoteList;

public class EditNoteFragment extends Fragment {

    private EditText titleEditText;
    private EditText descriptionEditText;
    @SuppressLint("StaticFieldLeak")
    private static EditText addressEditText;
    private Button buttonGetLocation;
    @SuppressLint("StaticFieldLeak")
    private static ProgressBar progressBar;
    private static final String ARGS_ID_EDIT = "id_args";
    private Note note;
    private NavigatorNote mNavigatorNote;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
        menu.findItem(R.id.open_note).setVisible(false);
        menu.findItem(R.id.delete_notes).setVisible(false);
        menu.findItem(R.id.edit_note).setVisible(false);
        menu.findItem(R.id.back_item).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back_item:
                mNavigatorNote.backToList();
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            UUID id = (UUID) getArguments().getSerializable(ARGS_ID_EDIT);
            note = getNoteList(getContext()).getNote(id);
        } else {
            note = Note.createNote();
            getNoteList(getContext()).addNote(note);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNavigatorNote = (NavigatorNote) context;
    }

    public static void setAddressEditText(String text) {
        EditNoteFragment.addressEditText.setText(text);
    }

    protected static void progressBarSetVisible(boolean visible) {
        progressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);

        InitUI(view);

        fillFields();

        btnGetLocationsOnClick();

        titleEditTextTextWatcher();

        descriptionEditTextTextWatcher();

        addressEditTextTextWatcher();

        return view;
    }

    private void addressEditTextTextWatcher() {
        addressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String address = addressEditText.getText().toString();
                note.setAddress(address);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void descriptionEditTextTextWatcher() {
        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String description = descriptionEditText.getText().toString();
                note.setDescription(description);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void titleEditTextTextWatcher() {
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String title = titleEditText.getText().toString();
                note.setTitle(title);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    private void btnGetLocationsOnClick() {
        buttonGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapLocation mapLocation = new MapLocation();
                Log.d("1111", "hi");
                mapLocation.execute(getActivity());
                Log.d("111", "hi");
            }
        });
    }

    private void fillFields() {
        titleEditText.setText(note.getTitle());
        descriptionEditText.setText(note.getDescription());
        addressEditText.setText(note.getAddress());
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void InitUI(View view) {
        titleEditText = view.findViewById(R.id.edit_note_title);
        descriptionEditText = view.findViewById(R.id.edit_note_description);
        addressEditText = view.findViewById(R.id.edit_note_address);
        buttonGetLocation = view.findViewById(R.id.button_get_location);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    public static EditNoteFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_ID_EDIT, id);
        EditNoteFragment fragment = new EditNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    public static EditNoteFragment newInstance() {
        return new EditNoteFragment();
    }



    @Override
    public void onDetach() {
        super.onDetach();
        NoteList.getNoteList(getContext()).updateNote(note);
        if((note.getTitle() == null) && (note.getDescription() == null)
                && (note.getAddress() == null)){
            NoteList.getNoteList(getContext()).deleteNote(note.getUUID());
        }
    }

}
