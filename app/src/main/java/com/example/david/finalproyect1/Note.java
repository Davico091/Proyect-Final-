package com.example.david.finalproyect1;

/**
 * Created by David on 05/04/2016.
 */
public class Note {
    private final long id;
    private final String title;
    private final String content;
    private final long creationTimestamp;
    private final long modificationTimestamp;
    private  boolean selected;

    public Note(long id, String title, String content, long creationTimestamp, long modificationTimestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
        this.modificationTimestamp = modificationTimestamp;
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

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public long getModificationTimestamp() {
        return modificationTimestamp;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationTimestamp=" + creationTimestamp +
                ", modificationTimestamp=" + modificationTimestamp +
                ", selected=" + selected +
                '}';
    }
}
