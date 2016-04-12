package com.example.david.finalproyect1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by David on 08/04/2016.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    private final List<Note> notes;
    private final LayoutInflater layoutInflater;

    NoteAdapter(final Context context, List<Note> notes){
        super(context,android.R.layout.simple_list_item_multiple_choice,notes);
        this.notes=notes;
        layoutInflater = LayoutInflater.from(getContext());
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        CustomHolder customHolder =null;

        if(convertView ==null){
            vi = layoutInflater.inflate(R.layout.note_element,parent,false);
            customHolder = new CustomHolder();
            customHolder.date =(TextView)vi.findViewById(R.id.note_creation_date);
            customHolder.title=(TextView)vi.findViewById(R.id.note_title);

            vi.setTag(customHolder);
            vi.setTag(R.id.note_title, customHolder.title);
            vi.setTag(R.id.note_creation_date, customHolder.date);


        }
        else {
            customHolder = (CustomHolder)vi.getTag();

        }

        final Note note= getItem(position);
        customHolder.title.setText(note.getTitle());
        customHolder.date.setText(Util.parseDate(String.valueOf(note.getCreationTimestamp())));

        return vi;
    }
    public class CustomHolder {
        TextView title;
        TextView date;
    }
}
