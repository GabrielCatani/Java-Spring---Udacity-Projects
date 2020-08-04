package com.udacity.jwdnd.course1.cloudstorage.model;

public class NotesModel {

    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userid;

    public NotesModel(Integer noteId, String noteTitle, String noteDescription, Integer userid) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteid(Integer noteid) {
        this.noteId = noteid;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserid() {
        return userid;
    }

}
