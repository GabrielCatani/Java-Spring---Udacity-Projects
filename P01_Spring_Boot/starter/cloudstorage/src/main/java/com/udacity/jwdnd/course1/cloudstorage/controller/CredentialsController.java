package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NotesModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CredentialsController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialsController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("credentials/save")
    public String addCredential(CredentialsModel credentialsModel, Model model){
        String error = null;
        User user = userService.getUser((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if(credentialsModel.getUsername().isEmpty()){
            error = "credentials without Username.";
        }

        if(credentialsModel.getCredentialId() != null){
            credentialService.editCredentials(credentialsModel);
            String success = credentialsModel.getUrl() + " uploaded successfully!";
            model.addAttribute("success", success);
            return "result";
        }

        if(error == null){
            credentialService.saveCredential(credentialsModel, user.getUserid());
            String success = credentialsModel.getUrl() + " uploaded successfully!";
            model.addAttribute("success", success);
        }else{
            model.addAttribute("error", error);
        }
        model.addAttribute("notes", credentialService.getCredentialByUserId(user.getUserid()));

        return "result";
    }

    @GetMapping("credentials/edit/{credentialId}")
    public String editCredential(@PathVariable Integer credentialId, Model model){
        CredentialsModel credentialsModel =  credentialService.getCredentialById(credentialId);
        model.addAttribute("credential", credentialsModel.getUrl());

        return "result";
    }

    @GetMapping(value = "/decryptPassword")
    @ResponseBody
    public Map<String, String> decodePassword(@RequestParam Integer credentialId){
        CredentialsModel credential = credentialService.decryptPassword(credentialId);
        String plainPassword = credential.getPassword();

        Map<String, String> response = new HashMap<>();
        response.put("plainPassword", plainPassword);
        return response;
    }

    @GetMapping("credentials/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model){
        String title =  credentialService.getCredentialById(credentialId).getUrl();
        credentialService.deleteCredential(credentialId);

        String success = title + " deleted successfully!";
        model.addAttribute("success", success);

        return "result";
    }
}
