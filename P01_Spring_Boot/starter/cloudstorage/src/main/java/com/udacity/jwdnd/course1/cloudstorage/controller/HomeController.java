package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsModel;
import com.udacity.jwdnd.course1.cloudstorage.model.FilesModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NotesModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private FilesService filesService;

    @Autowired
    private NotesService notesService;

    @Autowired
    private CredentialService credentialService;

    @GetMapping("/home")
    public String home(Model model){
        User user = userService.getUser((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            List<FilesModel> files = filesService.getFilesByUserId(user.getUserid());
            model.addAttribute("files", files);
        }catch(NullPointerException e){}

        try {
            List<NotesModel> notes = notesService.getAllNotesById(user.getUserid());
            model.addAttribute("notes", notes);
        }catch (NullPointerException e){}

        try {
            List<CredentialsModel> credentials = credentialService.getCredentialByUserId(user.getUserid());
            model.addAttribute("credentials", credentials);
        }catch(NullPointerException e){}



        return "home";
    }
}
