package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FilesModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Array;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    FilesModel getFile(String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    FilesModel getFileById(Integer fileId);

    @Select("SELECT filename FROM FILES WHERE fileId = #{fileId}")
    String getFileName(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<FilesModel> getAllFiles(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            " VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    int saveFile(FilesModel filesModel);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFileById(Integer fileId);

}
