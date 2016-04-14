package com.example.david.finalproyect1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
        params.putLong("id", note.getId());
        params.putString("title",note.getTitle());
        params.putString("content", note.getContent());
        params.putString("date", note.getDate());
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
        textViewId =(TextView)view.findViewById(R.id.content_fragment_id);
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
        textViewId.setText(String.valueOf(arguments.getLong("id")));
        textViewTitle.setText(arguments.getString("title"));
        textViewContent.setText(arguments.getString("content"));
        textViewDate.setText(String.valueOf(arguments.getString("date")));
    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.button_edit_note){
            editNote();
       }
    }
    private void editNote(){

        SQLiteDatabase db = noteSQLiteHelper.getWritableDatabase();
        ContentValues note = new ContentValues();

        note.put("content",textViewContent.getText().toString());
        note.put("title", textViewTitle.getText().toString());
       int status= db.update(Util.DBNAME,note,"id = "+textViewId.getText().toString(),null);
        db.close();
        if(status>0){
         Util.showMessage(getContext(),"Note edited");
        }
    }
}
