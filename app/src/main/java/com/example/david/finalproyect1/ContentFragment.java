package com.example.david.finalproyect1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by David on 05/04/2016.
 */
public class ContentFragment extends Fragment implements View.OnClickListener{

    private TextView textViewTitle;
    private TextView textViewDate;
    private TextView textViewContent;
    private TextView textViewId;
    private Button buttonEdit;
    NoteSQLiteHelper noteSQLiteHelper ;

    public static ContentFragment newInstance(Note note){
        final ContentFragment contentFragment = new ContentFragment();
        final Bundle params = new Bundle();
        params.putParcelable("note", note);
        contentFragment.setArguments(params);
        return contentFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_content,container,false);
        textViewTitle =(EditText)view.findViewById(R.id.content_fragment_title);
        textViewDate =(TextView)view.findViewById(R.id.content_fragment_date);
        textViewContent =(EditText)view.findViewById(R.id.content_fragment_content);
        noteSQLiteHelper = new NoteSQLiteHelper(getActivity().getApplicationContext());
        buttonEdit = (Button)view.findViewById(R.id.button_edit_note);
        buttonEdit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showNote();
    }

    private void showNote(){
        final Bundle arguments = getArguments();
        Note note = arguments.getParcelable("note");
        textViewTitle.setText(note.getTitle());
        textViewContent.setText(note.getContent());
        textViewDate.setText(note.getDate());
    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.button_edit_note){
            editNote();
       }
    }
    private void editNote(){

        long id = ((Note)getArguments().getParcelable("note")).get_id();
        String title = textViewTitle.getText().toString();
        String content = textViewContent.getText().toString();

        ContentValues edited_note = new ContentValues();
        edited_note.put("content",content);
        edited_note.put("title", title);

        final ContentResolver contentResolver = getActivity().getContentResolver();
        int status= contentResolver.update(NoteContract.URI, edited_note,String.valueOf(id),null);

        if(status>0){
            if(((MainActivity)getActivity()).isLargeView){
                ListFragment listFragment = (ListFragment)getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.LIST_FRAGMENT_TAG);
                listFragment.updateList();
            }
         Util.showMessage(getContext(),"Note edited");

        }
    }
}
