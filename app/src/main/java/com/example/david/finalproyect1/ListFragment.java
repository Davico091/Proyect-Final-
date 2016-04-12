package com.example.david.finalproyect1;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 05/04/2016.
 */
public class ListFragment extends Fragment implements View.OnClickListener{

    private final static int TEST_NOTES_SIZE = 100;
    private ListView listView;
    private ArrayList<Note> list_items;
    private ArrayList<Note> list_items_selected;
    private ListFragmentInterface listFragmentInterface;
    private NoteAdapter noteAdapter;
    NoteSQLiteHelper noteSQLiteHelper ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView)view.findViewById(R.id.listview_elements);
        list_items_selected = new ArrayList<>();
        noteSQLiteHelper = new NoteSQLiteHelper(getActivity().getApplicationContext(),Util.DBNAME,null,1);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(this);
        list_items = getDBNotes();
        noteAdapter =new NoteAdapter(getContext(),list_items);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setAdapter(noteAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle(getString(R.string.message_items_selected,listView.getCheckedItemCount()));
                list_items_selected.add(list_items.get(position));
            }
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.main, menu);
                return true;
            }
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.remove_option:
                        removeOption(list_items_selected);
                        mode.finish();
                        Util.showMessage(getActivity(),getString(R.string.message_item_removed));
                        return  true;
                    default:
                        return false;
                }
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                list_items_selected = new ArrayList<Note>();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listFragmentInterface != null) {
                    listFragmentInterface.onSelectedNote(((NoteAdapter) parent.getAdapter()).getItem(position));
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                list_items_selected = new ArrayList<>();
                return true;
            }

        });

    }
    public void setListFragmentInterface(final ListFragmentInterface listFragmentInterface){
        this.listFragmentInterface=listFragmentInterface;
    }
    public void addNewNote(String title,String content) {
        SQLiteDatabase db = noteSQLiteHelper.getWritableDatabase();
        ContentValues new_note = new ContentValues();
        new_note.put("title", title);
        new_note.put("content", content);
        db.insert("DBNOTE", null, new_note);
        db.close();
        noteAdapter.clear();
        noteAdapter.addAll(getDBNotes());
    }
    private void removeOption(ArrayList<Note> list_items_selected) {
        int count =0;
        noteAdapter.clear();
        noteSQLiteHelper = new NoteSQLiteHelper(getActivity().getApplicationContext(),Util.DBNAME,null,1);
        SQLiteDatabase db = noteSQLiteHelper.getWritableDatabase();
        for(Note note : list_items_selected){
            db.delete(Util.DBNAME, "id="+note.getId(), null);
            count++;
        }
        noteAdapter.addAll(getDBNotes());
        db.close();
    }
    public NoteAdapter getNoteAdapter() {
        return noteAdapter;
    }
    public void setNoteAdapter(NoteAdapter noteAdapter) {
        this.noteAdapter = noteAdapter;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fab){
            showDialog();
        }
    }
    public void showDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View dialogView = layoutInflater.inflate(R.layout.note_form, null);
        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.note_form_title);
        final EditText editTextContent = (EditText) dialogView.findViewById(R.id.note_form_content);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                addNewNote(editTextTitle.getText().toString(), editTextContent.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setView(dialogView);
        builder.create();
        builder.show();
    }
    public static interface ListFragmentInterface {
        public void onSelectedNote(final Note note);
    }
    public ArrayList<Note> getDBNotes(){
        noteSQLiteHelper = new NoteSQLiteHelper(getActivity().getApplicationContext(),Util.DBNAME,null,1);
        SQLiteDatabase db = noteSQLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DBNOTE", null);
        ArrayList<Note> notes = new ArrayList<>();

        while (cursor.moveToNext()){
            Note note = new Note(cursor.getInt(0),cursor.getString(1),cursor.getString(2),System.currentTimeMillis(),System.currentTimeMillis());
            notes.add(note);
        }
        db.close();

        return notes;
    }
}
