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


    public void depositToInvalidAgent(){
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel("SYSTEM","023154654",5000);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(404).extract().response();


        JsonPath jsonPath = res.jsonPath();
        System.out.println(jsonPath.get().toString());

        String message = jsonPath.get("message");

        setMessage(message);



    }

    public void depositToAgent(){
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel("SYSTEM",prop.getProperty("agent_phone_number"),5000);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(201).extract().response();


        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        System.out.println(message);
        //System.out.println(jsonPath.get().toString());



    }

    public void depositInsufficientToCustomer1() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(prop.getProperty("agent_phone_number"),prop.getProperty("customer1_phone_number"),500000);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(208).extract().response();


        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        setMessage(message);



    }





    public void depositToCustomer1() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(prop.getProperty("agent_phone_number"),prop.getProperty("customer1_phone_number"),2000);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(201).extract().response();


        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        Utils.setEnvVariable("customer1_trnxID",jsonPath.get("trnxId"));
        //System.out.println(message);
        //System.out.println(jsonPath.get().toString());




    }

    public void checkCustomerBalance(){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/transaction/balance/"+prop.getProperty("customer1_phone_number"))
                        .then()
                        .assertThat().statusCode(200).extract().response();




        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        /*System.out.println(message);
        System.out.println(jsonPath.get().toString());*/
        setMessage(message);







    }

    public void checkInvalidCustomerBalance(){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/transaction/balance/06546544454")
                        .then()
                        .assertThat().statusCode(404).extract().response();




        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        /*System.out.println(message);
        System.out.println(jsonPath.get().toString());*/
        setMessage(message);

    }

    public void checkCustomerStatementByTrnxId(){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/transaction/search/"+prop.getProperty("customer1_trnxID"))
                        .then()
                        .assertThat().statusCode(200).extract().response();



        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        /*System.out.println(message);
        System.out.println(jsonPath.get().toString());*/
        setMessage(message);

    }

    public void checkCustomerStatementByInvalidTrnxId(){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/transaction/search/TXN595")
                        .then()
                        .assertThat().statusCode(404).extract().response();



        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        /*System.out.println(message);
        System.out.println(jsonPath.get().toString());*/
        setMessage(message);

    }


    public void withdrawByCustomer() throws ConfigurationException {

        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(prop.getProperty("customer1_phone_number"),prop.getProperty("agent_phone_number"),1000);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/withdraw")
                        .then()
                        .assertThat().statusCode(201).extract().response();


        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get().toString());
        int currentBalance = jsonPath.get("currentBalance");
        Utils.setEnvVariable("customer1_balance",jsonPath.get("currentBalance").toString());
        setMessage(String.valueOf(currentBalance));


    }


    public void withdrawByCustomerToInvalidAgent() throws ConfigurationException {

        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(prop.getProperty("customer1_phone_number"),"65894665464",1000);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/withdraw")
                        .then()
                        .assertThat().statusCode(404).extract().response();


        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get().toString());
        String message = jsonPath.get("message");

        setMessage(message);


    }

    public void sendMoneyToAnotherCustomer() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(prop.getProperty("customer1_phone_number"),prop.getProperty("customer2_phone_number"),500);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/sendmoney")
                        .then()
                        .assertThat().statusCode(201).extract().response();


        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get().toString());
        int currentBalance = jsonPath.get("currentBalance");
        Utils.setEnvVariable("customer1_balance",jsonPath.get("currentBalance").toString());
        setMessage(String.valueOf(currentBalance));

    }


    public void sendMoneyToAnotherInvalidCustomer() throws ConfigurationException {
        Utils utils = new Utils();
        utils.generateRandomUser();

        UserModel transModel = new UserModel(prop.getProperty("customer1_phone_number"),"65498698236",500);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .body(transModel)
                        .when()
                        .post("/transaction/sendmoney")
                        .then()
                        .assertThat().statusCode(404).extract().response();


        JsonPath jsonPath = res.jsonPath();
        //System.out.println(jsonPath.get().toString());
        String message = jsonPath.get("message");

        setMessage(message);

    }


    public void checkCustomerStatementByWrongNumber(){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/transaction/statement/01489683465")
                        .then()
                        .assertThat().statusCode(404).extract().response();



        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        /*System.out.println(message);
        System.out.println(jsonPath.get().toString());*/
        setMessage(message);

    }


    public void checkCustomerStatement(){
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                        .when()
                        .get("/transaction/statement/"+prop.getProperty("customer1_phone_number"))
                        .then()
                        .assertThat().statusCode(200).extract().response();



        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        /*System.out.println(message);
        System.out.println(jsonPath.get().toString());*/
        setMessage(message);

    }





















}
