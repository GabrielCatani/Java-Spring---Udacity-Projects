package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "logout")
    private WebElement logoutButton;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void Login(){
        usernameField.sendKeys("gabriel");
        passwordField.sendKeys("12345");
        submitButton.click();
    }

    public void Logout(){
        logoutButton.click();
    }

}
