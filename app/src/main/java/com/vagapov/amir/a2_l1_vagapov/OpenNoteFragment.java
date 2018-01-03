package com.vagapov.amir.a2_l1_vagapov;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

import static com.vagapov.amir.a2_l1_vagapov.NoteList.getNoteList;


public class OpenNoteFragment extends Fragment {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private Button addressButton;
    private static final String ADDRESS_GEO = "geo:0, 0?z=20&q=";
    private UUID id;
    private NavigatorNote navigator;
    private MenuItem openNote;

    private static final String ARGS_ID = "id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (UUID) getArguments().getSerializable(ARGS_ID);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = (NavigatorNote) context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);
        menu.findItem(R.id.open_note).setVisible(false);
        menu.findItem(R.id.back_item).setVisible(true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_note:
                navigator.editNote(id);
                return true;
            case R.id.delete_notes:
                getNoteList(getContext()).deleteNote(id);
                getFragmentManager().popBackStack();
                getFragmentManager().beginTransaction().commit();
                return true;
            case R.id.back_item:
                navigator.backToList();
                return true;
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.open_note_fragment, container, false);

        initUI(view);

        fillNoteDescription();

        btnGetAddressOnClick();

        return view;
    }


    private void btnGetAddressOnClick() {
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = ADDRESS_GEO +  addressButton.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
                Intent chooser = Intent.createChooser(intent, getResources().getString(R.string.chooser_intent_text));
                startActivity(chooser);
            }
        });
    }

    private void fillNoteDescription() {
        titleTextView.setText(getNoteList(getContext()).getNote(id).getTitle());
        descriptionTextView.setText(getNoteList(getContext()).getNote(id).getDescription());
        addressButton.setText(getNoteList(getContext()).getNote(id).getAddress());
        if(addressButton.getText().equals("")){
            addressButton.setEnabled(false);
        } else{
            addressButton.setEnabled(true);
        }
    }

    private void initUI(View view) {
        titleTextView = view.findViewById(R.id.open_note_title_text_view);
        descriptionTextView = view.findViewById(R.id.open_note_description_text_view);
        addressButton = view.findViewById(R.id.button_address);
    }

    public static OpenNoteFragment newInstance(UUID id){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_ID, id);
        OpenNoteFragment fragment = new OpenNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
