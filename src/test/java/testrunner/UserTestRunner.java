package testrunner;

import controller.Transaction;
import controller.User;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.UserModel;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class UserTestRunner extends Setup {
    private String message;
    User user;

   @Test(priority = 1,description = "Cannot login with invalid email and valid password")


    public void doLoginWithInvalidEmail() throws ConfigurationException, IOException {
        user = new User();
        JsonPath jsonResponse= user.callLoginAPI("salman@ggr","1234");
        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("User not found"));

    }
   @Test(priority = 2,description = "Cannot Login with valid email and wrong password ")


    public void doLoginWithWrongPassword() throws ConfigurationException, IOException {
        user = new User();
        JsonPath jsonResponse=user.callLoginAPI("salman@roadtocareer.net","123");
        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Password incorrect"));

    }



   @Test(priority = 3,description = "Can login successfully with valid email and password")
    public void doLogin() throws ConfigurationException, IOException {
        user=new User();
        JsonPath jsonResponse=user.callLoginAPI("salman@roadtocareer.net","1234");
        String token = jsonResponse.get("token");
        String message=jsonResponse.get("message");

        Utils.setEnvVariable("token",token);

        System.out.println(token);
        System.out.println(message);
        Assert.assertTrue(message.contains("Login successfully"));
    }

    @Test(priority = 4,description = "Can not create customer if email and phone already registered ")

    public void createCustomerWithExistingCreds() throws ConfigurationException, IOException {
        user = new User();
        JsonPath jsonPath= user.createCustomer1("Cicely Collier","starr.dickens@yahoo.com","1234","01700844539","010030056","Customer");
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("User already exists"));

    }

   @Test(priority = 5,description = "Can create Customer successfully with valid email and phone number")

    public void createCustomer() throws ConfigurationException, IOException {

        user=new User();
        Utils utils = new Utils();
        utils.generateRandomUser();
        JsonPath jsonPath= user.createCustomer1(utils.getName(),utils.getEmail(),"1234",utils.generatePhoneNumber(),"010030056","Customer");
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("User created"));
        Utils.setEnvVariable("customer1_id",jsonPath.get("user.id").toString());
        Utils.setEnvVariable("customer1_name",jsonPath.get("user.name"));
        Utils.setEnvVariable("customer1_email",jsonPath.get("user.email"));
        Utils.setEnvVariable("customer1_phone_number",jsonPath.get("user.phone_number"));
    }
    @Test(priority = 6,description = "Create another customer successfully")

    public void createAnotherCustomer() throws ConfigurationException, IOException {
        user = new User();
        Utils utils = new Utils();
        utils.generateRandomUser();
        JsonPath jsonPath=user.createCustomer2(utils.getName(),utils.getEmail(),"1234",utils.generatePhoneNumber(),"010030056","Customer");
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("User created"));
        Utils.setEnvVariable("customer2_id",jsonPath.get("user.id").toString());
        Utils.setEnvVariable("customer2_name",jsonPath.get("user.name"));
        Utils.setEnvVariable("customer2_email",jsonPath.get("user.email"));
        Utils.setEnvVariable("customer2_phone_number",jsonPath.get("user.phone_number"));
    }
    @Test(priority = 7,description = "Can not create Agent if email and phone number already registered")


    public void createAgentWithExistingCreds() throws ConfigurationException, IOException {
        user = new User();
        JsonPath jsonPath=user.createAgent("Raul Gleason","mee.adams@hotmail.com","1234","01700737689","010030056","Agent");
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("User already exists"));

    }

    @Test(priority = 8,description = "Create Agent successfully with valid email and phone number")
    public void createAgent() throws ConfigurationException, IOException {
        user = new User();
        Utils utils = new Utils();
        utils.generateRandomUser();
        JsonPath jsonPath=user.createAgent(utils.getName(),utils.getEmail(),"1234",utils.generatePhoneNumber(),"010030056","Agent");
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("User created"));
        Utils.setEnvVariable("agent_id",jsonPath.get("user.id").toString());
        Utils.setEnvVariable("agent_name",jsonPath.get("user.name"));
        Utils.setEnvVariable("agent_email",jsonPath.get("user.email"));
        Utils.setEnvVariable("agent_phone_number",jsonPath.get("user.phone_number"));
    }
    @Test(priority = 9,description = "Can not find user by search using phone number if phone number is not registered")


    public void searchUserByInvalidPhoneNumber() throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/user/search/phonenumber/26459875646");






        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("User not found"));

    }

    @Test(priority = 10,description = "User found successfully by search using valid registered phone number")
    public void searchUserByPhoneNumber() throws ConfigurationException, IOException {
        user = new User();
        user.getUserByPhoneNumber();

    }





}
