package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import model.UserModel;
import org.apache.commons.configuration.ConfigurationException;
import setup.Setup;
import utils.Utils;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
@Getter
@Setter
public class Transaction extends Setup {
    public Transaction() throws IOException {
        initConfig();
    }
    private String message;
    private int currentBalance;



    public void depositToAgent(String from_account,String to_account,int amount){
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(from_account,to_account,amount);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/deposit");



        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        System.out.println(message);
        setMessage(message);
        //System.out.println(jsonPath.get().toString());



    }







    public void depositToCustomer1(String from_account,String to_account,int amount) throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(from_account,to_account,amount);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/deposit");



        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        System.out.println(message);
        Utils.setEnvVariable("customer1_trnxID",jsonPath.get("trnxId"));
        setMessage(message);
        




    }

    public JsonPath checkCustomerBalance(String phone_number){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/transaction/balance/"+phone_number+"");


        return res.jsonPath();


    }


    public JsonPath checkCustomerStatementByTrnxId(String trnx_id){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/transaction/search/"+trnx_id+"");




        return res.jsonPath();

    }



    public JsonPath withdrawByCustomer(String from_account,String to_account,int amount) throws ConfigurationException {

        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(from_account,to_account,amount);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/withdraw");



        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");

        return res.jsonPath();



    }




    public JsonPath sendMoneyToAnotherCustomer(String from_account,String to_account,int amount) throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(from_account,to_account,amount);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/sendmoney");


        return res.jsonPath();

    }




    public JsonPath checkCustomerStatement(String phone_number){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/transaction/statement/"+phone_number+"");



        return res.jsonPath();

    }




}

