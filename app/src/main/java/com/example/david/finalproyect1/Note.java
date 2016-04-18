package com.example.david.finalproyect1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David on 05/04/2016.
 */
public class Note implements Parcelable {

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

    protected Note(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        date = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public long get_id() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(date);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", selected=" + selected +
                '}';
    }
}
