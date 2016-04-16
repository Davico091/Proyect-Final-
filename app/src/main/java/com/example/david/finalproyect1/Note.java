package com.example.david.finalproyect1;

/**
 * Created by David on 05/04/2016.
 */
public class Note {

    private  long id;
    private  String title;
    private  String content;
    private  String date;
    private  boolean selected;

    public Note(){

    }
    public Note(long id, String title, String content,String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date=date;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getDate() {
        return date;
    }
}
