package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.google.common.net.HttpHeaders;
import com.udacity.jwdnd.course1.cloudstorage.model.FilesModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


@Controller
@RequestMapping()
public class FilesController {

    private final FilesService filesService;
    private final UserService userService;


    public FilesController(FilesService filesService, UserService userService) {
        this.filesService = filesService;
        this.userService = userService;
    }

    @PostMapping("file/upload")
    public String fileUpload(@RequestParam("fileUpload") MultipartFile file, Model model){
        String error = null;
        User user = userService.getUser((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(file.getSize() == 0){
            error = "File selected is empty. File not uploaded";
        }

        if(filesService.fileExists(file.getOriginalFilename())){
            error = "File with same name already uploaded. Change file name, or choose another file.";
        }

        if(error == null){
            filesService.saveFile(file, user.getUserid());
            String success = file.getOriginalFilename() + " uploaded successfully!";
            model.addAttribute("success", success);
        }else{
            model.addAttribute("error", error);
        }
        model.addAttribute("files", filesService.getFilesByUserId(user.getUserid()));

        return "result";
    }

    @GetMapping("file/download/{fileId}")
    public ResponseEntity downloadFile(@PathVariable Integer fileId){
        FilesModel filesModel = filesService.getFilesById(fileId);
        return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(filesModel.getContenttype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filesModel.getFilename() + "\"")
                    .body(filesModel.getFiledata());

    }

    @GetMapping("file/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model){
        String fileName = filesService.getFileName(fileId);
        filesService.deleteFile(fileId);
        String success = fileName + " deleted successfully!";
        model.addAttribute("success", success);

        return "result";
    }


}
