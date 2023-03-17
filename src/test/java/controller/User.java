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



    public JsonPath callLoginAPI(String email, String password) throws ConfigurationException {
        UserModel loginModel=new UserModel(email, password);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .body(loginModel)
                        .when()
                        .post("/user/login");



        return  res.jsonPath();

    }
    


    public JsonPath createCustomer1(String name, String email,String password,String phone_number,String nid,String role) throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();
        UserModel regModel = new UserModel(name,email,password,phone_number,nid,role);

        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(regModel)
                        .post("/user/create");



        return res.jsonPath();


    }



    public JsonPath createCustomer2(String name, String email,String password,String phone_number,String nid,String role) throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();
        UserModel regModel = new UserModel(name,email,password,phone_number,nid,role);

        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(regModel)
                        .post("/user/create");




        return res.jsonPath();


    }





    public JsonPath createAgent(String name, String email,String password,String phone_number,String nid,String role) throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel regModel = new UserModel(name,email,password,phone_number,nid,role);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(regModel)
                        .post("/user/create");



        return res.jsonPath();



    }
    public JsonPath getUserByPhoneNumber(String phone_number) throws ConfigurationException, IOException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/user/search/phonenumber/"+phone_number+"");



     
        return res.jsonPath();

    }









}




