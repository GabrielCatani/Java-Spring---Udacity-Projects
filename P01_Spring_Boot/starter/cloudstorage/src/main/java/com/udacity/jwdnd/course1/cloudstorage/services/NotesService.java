package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NotesModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List<NotesModel> getAllNotesById(Integer userId){
        return notesMapper.getNotesById(userId);
    }

    public void deleteNote(Integer noteid){
        notesMapper.deleteNoteById(noteid);
    }

    public int addNote(NotesModel notesModel, Integer userid){
        return notesMapper.insertNote(new NotesModel(null,
                                        notesModel.getNoteTitle(),
                                        notesModel.getNoteDescription(),
                                        userid));
    }

    public NotesModel getNoteById(Integer noteId){
        return notesMapper.getNote(noteId);
    }

    public String getTitle(Integer noteId){
        return notesMapper.getNoteTitle(noteId);
    }

    public void editNote(NotesModel notesModel){ notesMapper.updateNote(notesModel);}

}
