package com.example.david.finalproyect1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by David on 05/04/2016.
 */
public class ListFragment extends Fragment {

    private final static int TEST_NOTES_SIZE = 100;
    private ListView listView;
    private ListFragmentInterface listFragmentInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list,container,false);
        listView = (ListView)view.findViewById(R.id.listview_elements);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setAdapter(new NoteAdapter(getContext(), getTestData(TEST_NOTES_SIZE)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listFragmentInterface!=null){
                    listFragmentInterface.onSelectedNote(((NoteAdapter)parent.getAdapter()).getItem(position));
                }
            }
        });

    }

    private ArrayList<Note> getTestData(final int size){
        final ArrayList<Note> notes = new ArrayList<>();
        for (int i =0;i<size;i++){
            final Note note = new Note(i+1,"Title "+(i+1),"Content "+(i+1),System.currentTimeMillis(),System.currentTimeMillis());
            notes.add(note);
        }
        return notes;
    }

    public void setListFragmentInterface(final ListFragmentInterface listFragmentInterface){
        this.listFragmentInterface=listFragmentInterface;
    }

    public static class NoteAdapter extends ArrayAdapter<Note>{

        private final ArrayList<Note> notes;
        private final LayoutInflater layoutInflater;
        boolean[] checkboxState = new boolean[notes.size()];

        NoteAdapter(final Context context, ArrayList<Note> notes){
            super(context,0,notes);
            this.notes=notes;
            layoutInflater =LayoutInflater.from(getContext());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View vi=convertView;
            CustomHolder customHolder =null;

            if(convertView ==null){
                vi = layoutInflater.inflate(R.layout.note_element,parent,false);
                customHolder = new CustomHolder();
                customHolder.checkboxItem = (CheckBox)vi.findViewById(R.id.checkbox_item);
                customHolder.date =(TextView)vi.findViewById(R.id.note_creation_date);
                customHolder.title=(TextView)vi.findViewById(R.id.note_title);
                vi.setTag(customHolder);
            }
            else {
                customHolder = (CustomHolder)vi.getTag();
            }

            final Note note= getItem(position);
            customHolder.title.setText(note.getTitle());
            customHolder.date.setText(String.valueOf(note.getCreationTimestamp()));
            customHolder.checkboxItem.setTag(notes.get(position));
//            if(notes.get){
//
//            }
            customHolder.checkboxItem.setChecked(false);
            return vi;
        }

        public class CustomHolder{
            TextView title;
            TextView date;
            CheckBox checkboxItem;
        }

    }

    public static interface ListFragmentInterface {
        public void onSelectedNote(final Note note);
    }
}