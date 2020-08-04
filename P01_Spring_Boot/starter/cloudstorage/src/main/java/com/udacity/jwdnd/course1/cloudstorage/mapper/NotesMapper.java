package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NotesModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<NotesModel> getNotesById(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void    deleteNoteById(Integer noteid);

    @Select("SELECT notetitle FROM NOTES WHERE noteid = #{noteid}")
    String getNoteTitle(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    NotesModel getNote(Integer noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)" +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userid})")
    int insertNote(NotesModel notesModel);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    void    updateNote(NotesModel notesModel);
}
