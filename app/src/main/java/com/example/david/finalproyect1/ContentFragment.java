package com.example.david.finalproyect1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by David on 05/04/2016.
 */
public class ContentFragment extends Fragment {

    private TextView textViewTitle;
    private TextView textViewDate;
    private TextView textViewContent;

    public static ContentFragment newInstance(Note note){
        final ContentFragment contentFragment = new ContentFragment();
        final Bundle params = new Bundle();
        params.putString("title",note.getTitle());
        params.putString("content", note.getContent());
        params.putLong("creationTimestamp", note.getCreationTimestamp());
        contentFragment.setArguments(params);
        return contentFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_content,container,false);
        textViewTitle =(TextView)view.findViewById(R.id.content_fragment_title);
        textViewDate =(TextView)view.findViewById(R.id.content_fragment_date);
        textViewContent =(TextView)view.findViewById(R.id.content_fragment_content);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showNote();
    }

    private void showNote(){
        final Bundle arguments = getArguments();
        textViewTitle.setText(arguments.getString("title"));
        textViewContent.setText(arguments.getString("content"));
        textViewDate.setText(String.valueOf(arguments.getLong("creationTimestamp")));
    }
}
