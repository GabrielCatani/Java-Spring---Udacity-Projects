package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FilesModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilesService {

    private final FilesMapper filesMapper;

    public FilesService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public boolean fileExists(String filename){
        if(filesMapper.getFile(filename) != null){
            return true;
        }
        return false;
    }

    public void saveFile(MultipartFile file, Integer userId){
        try {
            filesMapper.saveFile(new FilesModel(null, file.getOriginalFilename(),
                    file.getContentType(), file.getSize(),
                    userId, file.getBytes()));
        }catch(IOException e){
            //add proper logging
            e.printStackTrace();
        }
    }

    public List<FilesModel> getFilesByUserId(Integer userId){
        return filesMapper.getAllFiles(userId);
    }

    public FilesModel getFilesById(Integer fileId){ return filesMapper.getFileById(fileId);}

    public void deleteFile(Integer fileId){ filesMapper.deleteFileById(fileId);}

    public String getFileName(Integer fileId){ return filesMapper.getFileName(fileId);}

}
