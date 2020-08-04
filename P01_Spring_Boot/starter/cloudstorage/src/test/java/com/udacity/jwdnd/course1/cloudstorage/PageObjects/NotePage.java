package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {

    @FindBy(id = "addNote")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleForm;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionForm;

    @FindBy(id = "noteSaveChange")
    private WebElement saveChangesNotes;

    @FindBy(id = "noteTitle")
    private WebElement noteTitle;

    @FindBy(id = "noteDescription")
    private WebElement getNoteDescription;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "success")
    private WebElement success;

    public NotePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void showNoteForm() {
        addNoteButton.click();
    }

    public void goToNotes(){
        noteTab.click();
    }

    public void fillNote(){
        noteTitleForm.sendKeys("Test title");
        noteDescriptionForm.sendKeys("test description");
    }

    public void createNote(){
        saveChangesNotes.click();
    }

    public String getNoteTitle(){

        try {
            return noteTitle.getText();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    public void clickSuccess(){ success.click();}

    public void editNote(){
        noteTitleForm.clear();
        noteTitleForm.sendKeys("Editado");
        saveChangesNotes.click();
    }
}
