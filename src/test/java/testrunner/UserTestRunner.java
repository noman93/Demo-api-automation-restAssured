package testrunner;

import controller.Transaction;
import controller.User;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;

import java.io.IOException;

public class UserTestRunner extends Setup {
    User user;

    @Test(priority = 1,description = "Cannot login with invalid email and valid password")


    public void doLoginWithInvalidEmail() throws ConfigurationException, IOException {
        user = new User();
        user.callLoginApiWithInvalidEmail();
        Assert.assertTrue(user.getMessage().contains("User not found"));

    }
    @Test(priority = 2,description = "Cannot Login with valid email and wrong password ")


    public void doLoginWithWrongPassword() throws ConfigurationException, IOException {
        user = new User();
        user.callLoginApiWithInvalidPassword();
        Assert.assertTrue(user.getMessage().contains("Password incorrect"));

    }



    @Test(priority = 3,description = "Can login successfully with valid email and password")
    public void doLogin() throws ConfigurationException, IOException {
        user = new User();
        user.callLoginApi();
        Assert.assertTrue(user.getMessage().contains("Login successfully"));
    }

    @Test(priority = 4,description = "Can not create customer if email and phone already registered ")

    public void createCustomerWithExistingCreds() throws ConfigurationException, IOException {
        user = new User();
        user.createCustomerWithExistingCreds();
        Assert.assertTrue(user.getMessage().contains("User already exists"));

    }

    @Test(priority = 5,description = "Can create Customer successfully with valid email and phone number")

    public void createCustomer() throws ConfigurationException, IOException {
        user = new User();
        user.createCustomer1();
    }
    @Test(priority = 6,description = "Create another customer successfully")

    public void createAnotherCustomer() throws ConfigurationException, IOException {
        user = new User();
        user.createCustomer2();
    }
    @Test(priority = 7,description = "Can not create Agent if email and phone number already registered")


    public void createAgentWithExistingCreds() throws ConfigurationException, IOException {
        user = new User();
        user.createAgentWithExistingCreds();
        Assert.assertTrue(user.getMessage().contains("User already exists"));

    }

    @Test(priority = 8,description = "Create Agent successfully with valid email and phone number")
    public void createAgent() throws ConfigurationException, IOException {
        user = new User();
        user.createAgent();
    }
    @Test(priority = 9,description = "Can not find user by search using phone number if phone number is not registered")


    public void searchUserByInvalidPhoneNumber() throws ConfigurationException, IOException {
        user = new User();
        user.getUserByInvalidPhoneNumber();
        Assert.assertTrue(user.getMessage().contains("User not found"));

    }

    @Test(priority = 10,description = "User found successfully by search using valid registered phone number")
    public void searchUserByPhoneNumber() throws ConfigurationException, IOException {
        user = new User();
        user.getUserByPhoneNumber();

    }





}
