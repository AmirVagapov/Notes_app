package com.vagapov.amir.a2_l1_vagapov;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.vagapov.amir.a2_l1_vagapov.widget.WidgetNotes;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NavigatorNote {


    private static int VIEW_PAGER_POSITION = 0;
    private static final int ANOTHER_FRAGMENT = 1;
    private static final int VIEW_PAGER_EXIT = 100;

    private Fragment fragment;
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    public static final String OPEN_MAIN_ACTIVITY_EDIT = "open_edit";
    public static final String ADD_NOTE = "addNote";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tablayout);


        viewPager = findViewById(R.id.container);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListViewFragment(), getString(R.string.your_notes));
        adapter.addFragment(new MapLocationfromNoteFragment(), getString(R.string.your_notes_on_map));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


        catchIntent();
    }

    @Override
    public void onBackPressed() {
        if(VIEW_PAGER_POSITION == VIEW_PAGER_EXIT){
            super.onBackPressed();
        }else{
            backToList();
        }

    }

    private void catchIntent() {
        if (getIntent().getType() != null && getIntent().getType().equalsIgnoreCase(OPEN_MAIN_ACTIVITY_EDIT)) {
            Intent intent = getIntent();
            UUID id = (UUID) intent.getSerializableExtra(WidgetNotes.ID_EXTRA);
            editNote(id);
        } else if (getIntent().getType() != null && getIntent().getType().equalsIgnoreCase(ADD_NOTE)) {
            createNote();
        }
    }


    @Override
    public void openNote(UUID id) {
        fragment = OpenNoteFragment.newInstance(id);
        adapter.replaceFirstFragment(fragment, getString(R.string.your_note));
        viewPager.setAdapter(adapter);
        VIEW_PAGER_POSITION = ANOTHER_FRAGMENT;
    }


    @Override
    public void editNote(UUID id) {
        fragment = EditNoteFragment.newInstance(id);
        adapter.replaceFirstFragment(fragment, getString(R.string.edit_task));
        viewPager.setAdapter(adapter);
        VIEW_PAGER_POSITION = ANOTHER_FRAGMENT;
    }

    @Override
    public void createNote() {
        fragment = EditNoteFragment.newInstance();
        adapter.replaceFirstFragment(fragment, getString(R.string.describe_note));
        viewPager.setAdapter(adapter);
        VIEW_PAGER_POSITION = ANOTHER_FRAGMENT;
    }

    @Override
    public void backToList() {
        fragment = new ListViewFragment();
        adapter.replaceFirstFragment(fragment, getString(R.string.your_notes));
        viewPager.setAdapter(adapter);
        VIEW_PAGER_POSITION = VIEW_PAGER_EXIT;
    }


}
