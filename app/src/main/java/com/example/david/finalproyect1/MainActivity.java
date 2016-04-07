package com.example.david.finalproyect1;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements ListFragment.ListFragmentInterface {

    private FragmentManager fragmentManager;

    public static final String LIST_FRAGMENT_TAG = "list_fragment";
    public static final String CONTENT_FRAGMENT_TAG = "content_fragment";
    private Button buttonFromSave;
    private ListView listViewItems ;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonFromSave = (Button)findViewById(R.id.button_form_save);
        listViewItems = (ListView)findViewById(R.id.listview_elements);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            final ListFragment listFragment = new ListFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, listFragment, LIST_FRAGMENT_TAG).commit();
            listFragment.setListFragmentInterface(this);
        } else {
            final Fragment fragment = fragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG);
            if (fragment != null) {
                ((ListFragment) fragment).setListFragmentInterface(this);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.new_option:
                newOption();
                break;
            case R.id.edit_option:
                editOption();
                break;
            case R.id.remove_option:
                removeOption();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectedNote(Note note) {
        final ContentFragment contentFragment = ContentFragment.newInstance(note);
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, contentFragment, CONTENT_FRAGMENT_TAG).commit();
    }

    private void newOption() {
        showMessage("New");
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.note_form, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(dialogView);
        builder.create();
        builder.show();
    }

    private void editOption() {
        showMessage("Edit");
    }

    private void removeOption() {
        showMessage("Remove");
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}
