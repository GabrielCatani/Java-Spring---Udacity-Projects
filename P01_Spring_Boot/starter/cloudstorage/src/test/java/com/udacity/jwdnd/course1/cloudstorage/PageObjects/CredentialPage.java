package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CredentialPage {

    @FindBy(id = "addCredential")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlForm;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameForm;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordForm;

    @FindBy(id = "credentialSaveChange")
    private WebElement saveChangesCredentials;

    @FindBy(id = "credentialUrl")
    private WebElement credentialUrl;

    @FindBy(id = "credentialUsername")
    private WebElement credentialUsername;

    @FindBy(id = "credentialPassword")
    private WebElement credentialPassword;

    @FindBy(id = "nav-tab")
    private WebElement navTab;

    @FindBy(id = "nav-tab")
    private WebElement Tab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "success")
    private WebElement success;

    public CredentialPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void showCredentialForm() {
        addCredentialButton.click();
    }

    public void goToCredentials(){
        this.credentialsTab.click();
    }

    public void fillCredential(){
        credentialUrlForm.sendKeys("http://localhost:8080/login");
        credentialUsernameForm.sendKeys("user");
        credentialPasswordForm.sendKeys("1234");
    }

    public void createCredential(){
        saveChangesCredentials.click();
    }

    public String getCredentialUrl(){

        try {
            return credentialUrl.getText();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    public void clickSuccess(){ success.click();}

    public void editCredential(){
        credentialUrlForm.clear();
        credentialUrlForm.sendKeys("teste");
        saveChangesCredentials.click();
    }
}
