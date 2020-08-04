package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void create_user_happy_path() throws Exception{
        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("thisIsHashed");

        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(r);

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        Assert.assertNotNull(u);
        Assert.assertEquals(0, u.getId());
        Assert.assertEquals("thisIsHashed", u.getPassword());
    }

    @Test
    public void find_by_id() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findById(0L)).thenReturn(Optional.of(user));

        final ResponseEntity<User> response = userController.findById(0L);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        User user1 = response.getBody();
        Assert.assertNotNull(user1);
        Assert.assertEquals(0, user1.getId());
        Assert.assertEquals("username", user1.getUsername());
        Assert.assertEquals("password", user1.getPassword());
    }

    @Test
    public void find_by_id_notfound() {
        when(userRepository.findById(0L)).thenReturn(Optional.empty());

        final ResponseEntity<User> response = userController.findById(0L);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void find_username() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findByUsername("user")).thenReturn(user);

        final ResponseEntity<User> response = userController.findByUserName("user");
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        User user1 = response.getBody();
        Assert.assertNotNull(user1);
        Assert.assertEquals(0, user1.getId());
        Assert.assertEquals("username", user1.getUsername());
        Assert.assertEquals("password", user1.getPassword());
    }

    @Test
    public void username_not_found() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        final ResponseEntity<User> response = userController.findByUserName("testuser");
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }


    @Test
    public void create_user_password_greater_length() throws Exception {
        when(bCryptPasswordEncoder.encode("pass")).thenReturn("hashedpassword");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("username");
        request.setPassword("pass");
        request.setConfirmPassword("pass");

        final ResponseEntity<User> response = userController.createUser(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());

    }

    @Test
    public void create_user_password_not_match() throws Exception {
        when(bCryptPasswordEncoder.encode("password")).thenReturn("hashedpassword");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setConfirmPassword("passWord");

        final ResponseEntity<User> response = userController.createUser(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }
}
