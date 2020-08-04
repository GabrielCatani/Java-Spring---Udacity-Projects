package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.ui.Model;

import java.io.File;
import java.io.InputStream;

public class HomePageFiles {


    @FindBy(id = "fileUpload")
    private WebElement file;

    @FindBy(id = "upload")
    private WebElement uploadButton;

    @FindBy(id = "viewFile")
    private WebElement viewFileLink;

    @FindBy(id = "deleteFile")
    private WebElement deleteFileLink;

    @FindBy(id = "fileName")
    private WebElement fileNameForm;

    @FindBy(id = "success")
    private WebElement success;

    @FindBy(id = "error")
    private WebElement error;

    public HomePageFiles(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void uploadFile(){
        File f = new File(getClass().getClassLoader().getResource("Home-Udacity.pdf").getFile()
        );
        file.sendKeys(f.getPath());
        uploadButton.click();
    }


    public void deleteFile(){
        deleteFileLink.click();
    }

    public String getFileNameUploaded(){
        return fileNameForm.getText();
    }

    public WebElement getFileNameForm() {
        return fileNameForm;
    }

    public void successClick(){
        success.click();
    }
}
