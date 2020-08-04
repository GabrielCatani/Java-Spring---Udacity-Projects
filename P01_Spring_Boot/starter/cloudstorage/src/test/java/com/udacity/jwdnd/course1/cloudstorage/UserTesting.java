package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.PageObjects.*;
import com.udacity.jwdnd.course1.cloudstorage.model.FilesModel;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.event.annotation.AfterTestMethod;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTesting {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private static LoginPage login;
    private static SignupPage signup;
    private HomePageFiles homePageFiles;
    private NotePage note;
    private CredentialPage credential;


    @BeforeAll
    public static void beforeall() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(chromeOptions);
        signup = new SignupPage(driver);
        login = new LoginPage(driver);
    }


    @AfterAll
    public static void afterAll() {
        driver.quit();
    }


    @Test
    @Order(1)
    public void validateSignUpLogin() {
        driver.get("http://localhost:" + port + "/signup");
        signup.signup();
        driver.get("http://localhost:" + port + "/login");

        login.Login();

        driver.get("http://localhost:" + port + "/home");
        String expectedURL = driver.getCurrentUrl();
        String actualURL = "http://localhost:" + port + "/home";
        Assertions.assertEquals(expectedURL, actualURL);
    }


    @Test
    @Order(2)
    public void validateFileUpload() throws InterruptedException {
        driver.get("http://localhost:" + port + "/home");
        homePageFiles = new HomePageFiles(driver);
        homePageFiles.uploadFile();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("success")));
        homePageFiles.successClick();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fileName")));
        Assertions.assertEquals("Home-Udacity.pdf", homePageFiles.getFileNameUploaded());
    }


    @Test
    @Order(3)
    public void validateCreateNote() throws InterruptedException {
        driver.navigate().to("http://localhost:" + port + "/home");
        note = new NotePage(driver);

        note.goToNotes();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addNote")));
        note.showNoteForm();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
        note.fillNote();
        note.createNote();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("success"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab"))).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("noteTitle"), "Test title"));


        Assertions.assertEquals("Test title", note.getNoteTitle().toString());
    }

    @Test
    @Order(6)
    public void validateCreateCredential() throws InterruptedException {
        driver.get("http://localhost:" + port + "/signup");
        signup.signup();
        driver.get("http://localhost:" + port + "/login");

        login.Login();

        driver.get("http://localhost:" + port + "/home");
        credential = new CredentialPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("nav-credentials-tab"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("addCredential")));
        credential.showCredentialForm();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
        credential.fillCredential();
        credential.createCredential();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("success"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab"))).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("credentialUrl"), "http://localhost:8080/login"));

        Assertions.assertEquals("http://localhost:8080/login", credential.getCredentialUrl().toString());
    }

    @Test
    @Order(9)
    public void validateUnathorizedAccess() throws InterruptedException {
        driver.get("http://localhost:" + port + "/home");
        login.Logout();

        driver.get("http://localhost:" + port + "/home");
        String expectedURL = driver.getCurrentUrl();
        String actualURL = "http://localhost:" + port + "/login";
        Assertions.assertEquals(expectedURL, actualURL);
    }

    @Test
    @Order(7)
    public void validateEditCredential(){
        driver.navigate().to("http://localhost:" + port + "/home");
        credential = new CredentialPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("nav-credentials-tab"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("editCredential"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("credential-url")));
        credential.editCredential();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("success"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("nav-credentials-tab"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("credentialUrl"))).click();

        Assertions.assertEquals("teste", credential.getCredentialUrl().toString());

    }

    @Test
    @Order(4)
    public void validateEditNote(){
        driver.navigate().to("http://localhost:" + port + "/home");
        note = new NotePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("nav-notes-tab"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("editButton"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("note-title")));
        note.editNote();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("success"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("nav-notes-tab"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("noteTitle"))).click();

        Assertions.assertEquals("Editado", note.getNoteTitle().toString());

    }

    @Test
    @Order(5)
    public void validateDeleteNote(){
        driver.navigate().to("http://localhost:" + port + "/home");
        note = new NotePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("nav-notes-tab"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("deleteButton"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("success"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("nav-notes-tab"))).click();

        Assertions.assertEquals(null, note.getNoteTitle());

    }

    @Test
    @Order(8)
    public void validateDeleteCredential(){
        driver.navigate().to("http://localhost:" + port + "/home");
        credential = new CredentialPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("nav-credentials-tab"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("credentialDelete"))).click();

        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("success"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.id("nav-credentials-tab"))).click();

        Assertions.assertEquals(null, credential.getCredentialUrl());

    }

}

