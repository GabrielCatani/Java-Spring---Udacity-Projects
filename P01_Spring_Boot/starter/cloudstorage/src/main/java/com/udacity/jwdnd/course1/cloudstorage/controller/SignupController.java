package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(){
        return"signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model){
        String signupError = null;

        if(!userService.isUsernameAvailable(user.getUsername())){
            signupError = "Username already exists. Please, choose another one.";
        }

        if(signupError == null){
            int usersAdded = userService.createUser(user);
            if(usersAdded == 0){
                signupError = "A problem signing you up has occurred. Please, try again";
            }
        }

        if(signupError == null){
            String singupSuccess = user.getUsername() + " successfully created! Please, continue to the ";
            model.addAttribute("signupSuccess", singupSuccess);
        }else{
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }

}
