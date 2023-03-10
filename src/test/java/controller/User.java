package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import model.UserModel;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
@Getter
@Setter
public class User extends Setup {
    public User() throws IOException {
        initConfig();
    }


    private String message;



    public void callLoginApi() throws ConfigurationException {
        UserModel loginModel = new UserModel("salman@roadtocareer.net", "1234");
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .body(loginModel)
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(200).extract().response();


        JsonPath jsonPath = res.jsonPath();
        String token = jsonPath.get("token");
        String message = jsonPath.get("message");
        Utils.setEnvVariable("token", token);

        System.out.println(token);
        System.out.println(message);
        setMessage(message);

    }
    public void callLoginApiWithInvalidEmail() throws ConfigurationException {
        UserModel loginModel = new UserModel("salman@ggr", "1234");
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .body(loginModel)
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(404).extract().response();


        JsonPath jsonPath = res.jsonPath();

        String message = jsonPath.get("message");

        setMessage(message);

    }



    public void callLoginApiWithInvalidPassword() throws ConfigurationException {
        UserModel loginModel = new UserModel("salman@roadtocareer.net", "123");
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .body(loginModel)
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(401).extract().response();


        JsonPath jsonPath = res.jsonPath();

        String message = jsonPath.get("message");

        setMessage(message);

    }





    public void getUserByInvalidPhoneNumber() throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        ;
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/user/search/phonenumber/26459875646")
                        .then()
                        .assertThat().statusCode(404).extract().response();





        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        //System.out.println(jsonPath.get("user").toString());
        setMessage(message);

    }





    public void getUserByPhoneNumber() throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        ;
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/user/search/phonenumber/"+prop.getProperty("customer1_phone_number"))
                        .then()
                        .assertThat().statusCode(200).extract().response();



        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get("user").toString());

    }




    public void createCustomerWithExistingCreds() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel regModel = new UserModel("Cicely Collier", "starr.dickens@yahoo.com", "1234", "01700844539", "010030056", "Customer");
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(regModel)
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(208).extract().response();


        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get().toString());

        String message = jsonPath.get("message");

        setMessage(message);


    }





    public void createCustomer1() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel regModel = new UserModel(utils.getName(), utils.getEmail(), "1234", utils.generatePhoneNumber(), "010030056", "Customer");
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(regModel)
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();


        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get().toString());
        Utils.setEnvVariable("customer1_id",jsonPath.get("user.id").toString());
        Utils.setEnvVariable("customer1_name",jsonPath.get("user.name"));
        Utils.setEnvVariable("customer1_email",jsonPath.get("user.email"));
        Utils.setEnvVariable("customer1_phone_number",jsonPath.get("user.phone_number"));


    }



    public void createCustomer2() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel regModel = new UserModel(utils.getName(), utils.getEmail(), "1234", utils.generatePhoneNumber(), "010030056", "Customer");
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(regModel)
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();


        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get().toString());
        Utils.setEnvVariable("customer2_id",jsonPath.get("user.id").toString());
        Utils.setEnvVariable("customer2_name",jsonPath.get("user.name"));
        Utils.setEnvVariable("customer2_email",jsonPath.get("user.email"));
        Utils.setEnvVariable("customer2_phone_number",jsonPath.get("user.phone_number"));


    }




    public void createAgentWithExistingCreds() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel regModel = new UserModel("Raul Gleason", "mee.adams@hotmail.com", "1234", "01700737689", "010030056", "Agent");
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(regModel)
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(208).extract().response();


        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get().toString());

        String message = jsonPath.get("message");

        setMessage(message);


    }



    public void createAgent() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel regModel = new UserModel(utils.getName(), utils.getEmail(), "1234", utils.generatePhoneNumber(), "010030056", "Agent");
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(regModel)
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();


        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get().toString());
        Utils.setEnvVariable("agent_id",jsonPath.get("user.id").toString());
        Utils.setEnvVariable("agent_name",jsonPath.get("user.name"));
        Utils.setEnvVariable("agent_email",jsonPath.get("user.email"));
        Utils.setEnvVariable("agent_phone_number",jsonPath.get("user.phone_number"));


    }









}
