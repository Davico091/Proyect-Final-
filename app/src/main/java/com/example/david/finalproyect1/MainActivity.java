package com.example.david.finalproyect1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements ListFragment.ListFragmentInterface {

    private FragmentManager fragmentManager;

    public static final String LIST_FRAGMENT_TAG = "list_fragment";
    public static final String CONTENT_FRAGMENT_TAG = "content_fragment";
    final Context context = this;
    private ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            listFragment = new ListFragment();
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
    public void onSelectedNote(Note note) {
        final ContentFragment contentFragment = ContentFragment.newInstance(note);
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, contentFragment, CONTENT_FRAGMENT_TAG).commit();
    }

    private void newOption() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.note_form, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(dialogView);
        builder.create();
        builder.show();
    }

}
