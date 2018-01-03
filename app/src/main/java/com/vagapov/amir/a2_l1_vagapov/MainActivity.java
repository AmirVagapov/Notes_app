package com.vagapov.amir.a2_l1_vagapov;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;



import com.vagapov.amir.a2_l1_vagapov.widget.WidgetNotes;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NavigatorNote {

    private FragmentManager fm;
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

        fm = getSupportFragmentManager();
        /*fragment = fm.findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = new ListViewFragment();
            fm.beginTransaction().replace(R.id.container, fragment).commit();

        }*/

        catchIntent();
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
        //fm.beginTransaction().addToBackStack(null).add(R.id.container, fragment).commit();
    }


    @Override
    public void editNote(UUID id) {
        fragment = EditNoteFragment.newInstance(id);
        adapter.replaceFirstFragment(fragment, getString(R.string.edit_task));
        viewPager.setAdapter(adapter);
        //fm.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
    }

    @Override
    public void createNote() {
        fragment = EditNoteFragment.newInstance();
        adapter.replaceFirstFragment(fragment, getString(R.string.describe_note));
        viewPager.setAdapter(adapter);
        //fm.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
    }

    @Override
    public void backToList() {
        fragment = new ListViewFragment();
        adapter.replaceFirstFragment(fragment, getString(R.string.your_notes));
        viewPager.setAdapter(adapter);
        //fm.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
    }


}
