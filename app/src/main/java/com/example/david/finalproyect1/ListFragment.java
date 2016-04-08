package com.example.david.finalproyect1;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 05/04/2016.
 */
public class ListFragment extends Fragment {

    private final static int TEST_NOTES_SIZE = 100;
    private ListView listView;
    private ArrayList<Note> list_items;
    private ArrayList<Note> list_items_selected = new ArrayList<>();
    private ListFragmentInterface listFragmentInterface;
    private NoteAdapter noteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list,container,false);
        listView = (ListView)view.findViewById(R.id.listview_elements);
        noteAdapter =new NoteAdapter(getContext(), getTestData(TEST_NOTES_SIZE));
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
    private ArrayList<Note> getTestData(final int size){

        if(list_items!=null){
            return list_items;
        }
        else {
            list_items = new ArrayList<>();
            for (int i =0;i<size;i++){
                final Note note = new Note(i+1,"Title "+(i+1),"Content "+(i+1),System.currentTimeMillis(),System.currentTimeMillis());
                list_items.add(note);
            }
            return list_items;
        }
    }

    public void setListFragmentInterface(final ListFragmentInterface listFragmentInterface){
        this.listFragmentInterface=listFragmentInterface;
    }

    public void addNewNote() {
        Note note = new Note(1,"titulo nuevo","contenido nuevo",System.currentTimeMillis(),System.currentTimeMillis());
        noteAdapter.add(note);
    }

    public static interface ListFragmentInterface {
        public void onSelectedNote(final Note note);
    }
    private void removeOption(ArrayList<Note> list_items_selected) {
        int count =0;
        for(Note note : list_items_selected){
            noteAdapter.remove(note);
            count++;
        }
    }

    public NoteAdapter getNoteAdapter() {
        return noteAdapter;
    }

    public void setNoteAdapter(NoteAdapter noteAdapter) {
        this.noteAdapter = noteAdapter;
    }
}
