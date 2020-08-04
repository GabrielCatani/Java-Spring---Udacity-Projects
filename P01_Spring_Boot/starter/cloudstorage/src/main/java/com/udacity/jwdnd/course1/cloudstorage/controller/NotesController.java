package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NotesModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotesController {

    private final UserService userService;
    private final NotesService notesService;

    public NotesController(UserService userService, NotesService notesService) {
        this.userService = userService;
        this.notesService = notesService;
    }

    @PostMapping("notes/save")
    public String addNote(NotesModel notesModel, Model model){
        String error = null;
        User user = userService.getUser((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if(notesModel.getNoteTitle().isEmpty()){
            error = "Note without a title.";
        }

        if(notesModel.getNoteId() != null){
            notesService.editNote(notesModel);
            String success = notesModel.getNoteTitle() + " uploaded successfully!";
            model.addAttribute("success", success);
            return "result";
        }

        if(error == null){
            notesService.addNote(notesModel, user.getUserid());
            String success = notesModel.getNoteTitle() + " uploaded successfully!";
            model.addAttribute("success", success);
        }else{
            model.addAttribute("error", error);
        }
        model.addAttribute("notes", notesService.getAllNotesById(user.getUserid()));

        return "result";
    }

    @GetMapping("notes/edit/{noteId}")
    public String editNote(@PathVariable Integer noteId, Model model){
       NotesModel notesModel =  notesService.getNoteById(noteId);
       model.addAttribute("noteTitle", notesModel.getNoteTitle());

       return "result";
    }

    @GetMapping("notes/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model){
        String title =  notesService.getTitle(noteId);
        notesService.deleteNote(noteId);

        String success = title + " deleted successfully!";
        model.addAttribute("success", success);

        return "result";
    }
}
