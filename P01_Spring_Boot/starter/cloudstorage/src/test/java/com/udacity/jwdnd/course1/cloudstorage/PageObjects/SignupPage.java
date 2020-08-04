package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement userNameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void signup(){
        firstNameField.sendKeys("Gabriel");
        lastNameField.sendKeys("Catani");
        userNameField.sendKeys("gabriel");
        passwordField.sendKeys("12345");
        submitButton.click();
    }


}
