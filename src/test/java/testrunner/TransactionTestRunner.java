package testrunner;

import controller.Transaction;
import controller.User;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TransactionTestRunner extends Setup {
    User user;

    Transaction transaction;
    @BeforeTest
    public void doLogin() throws ConfigurationException, IOException {
        user=new User();
        JsonPath jsonResponse=user.callLoginAPI("salman@roadtocareer.net","1234");
        String message=jsonResponse.get("message");
        System.out.println(message);
        Assert.assertTrue(message.contains("Login successfully"));
    }
    @Test(priority = 11,description = "Can not deposit to Agent if Agent number if not registered")

   public void depositToInvalidAgent() throws IOException {
        transaction = new Transaction();
        transaction.depositToAgent("SYSTEM","2546874654",5000);
        Assert.assertTrue(transaction.getMessage().contains("Account does not exist"));

    }


    @Test(priority = 12,description = "Deposit to valid registered agent successfully")


    public void depositToAgent() throws IOException {
        transaction = new Transaction();
        transaction.depositToAgent("SYSTEM",prop.getProperty("agent_phone_number"),5000);
        Assert.assertTrue(transaction.getMessage().contains("Deposit successful"));
    }
    @Test(priority = 13,description = "Can not deposit to customer if balance is insufficient")

    public void depositToInvalidCustomer() throws IOException, ConfigurationException {
        transaction = new Transaction();
        transaction.depositToCustomer1(prop.getProperty("agent_phone_number"),prop.getProperty("customer1_phone_number"),100000000);
        Assert.assertTrue(transaction.getMessage().contains("Insufficient balance"));

    }
    @Test(priority = 14,description = "Deposit to customer successfully")


    public void depositToCustomer1() throws IOException, ConfigurationException {
        transaction = new Transaction();
        transaction.depositToCustomer1(prop.getProperty("agent_phone_number"),prop.getProperty("customer1_phone_number"),2000);
        Assert.assertTrue(transaction.getMessage().contains("Deposit successful"));
    }
   @Test(priority = 15,description = "Can not check customer balance if phone number is invalid")


    public void checkInvalidCustomerBalance() throws IOException {
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
        Assert.assertTrue(message.contains("User not found"));

    }
    @Test(priority = 16,description = "Check customer balance successfully with valid customer number")



    public void checkCustomerBalance() throws IOException {
        transaction = new Transaction();
        transaction.checkCustomerBalance();
        Assert.assertTrue(transaction.getMessage().contains("User balance"));
    }
    @Test(priority = 17,description = "Transaction not found if search by invalid trnxID")


    public void searchByInvalidTrnxID() throws IOException {
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
        Assert.assertTrue(message.contains("Transaction not found"));


    }
    @Test(priority = 18,description = "Search by valid trnxID transaction successfully found")

    public void searchByTrnxID() throws IOException {
        transaction = new Transaction();
        transaction.checkCustomerStatementByTrnxId();
        Assert.assertTrue(transaction.getMessage().contains("Transaction list"));
    }
    @Test(priority = 19,description = "Can not withdraw by customer if agent number is invalid")
    public void withdrawByCustomerToInvalidAgent() throws IOException, ConfigurationException {
        transaction=new Transaction();
        JsonPath jsonPath=transaction.withdrawByCustomer(prop.getProperty("customer1_phone_number"),"65894665464",1000);
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("Account does not exist"));

    }

    @Test(priority = 20,description = "Customer can withdraw successfully to valid agent")
    public void withdrawByCustomer() throws IOException, ConfigurationException {
        transaction=new Transaction();
        JsonPath jsonPath=transaction.withdrawByCustomer(prop.getProperty("customer1_phone_number"),prop.getProperty("agent_phone_number"),1000);
        int currentBalance = jsonPath.get("currentBalance");
        Utils.setEnvVariable("customer1_balance",jsonPath.get("currentBalance").toString());
        String.valueOf(currentBalance);
        Assert.assertTrue(String.valueOf(currentBalance).contains("990"));



    }
    @Test(priority = 21,description = "Customer can not send monet to another customer if another customer number is invalid")


    public void sendMoneyByCustomerToInvalidCustomer() throws IOException, ConfigurationException {
        transaction=new Transaction();
        JsonPath jsonPath=transaction.sendMoneyToAnotherCustomer(prop.getProperty("customer1_phone_number"),"65498698236",500);
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("From/To Account does not exist"));

    }


   @Test(priority = 22,description = "Customer can send money to another valid customer successfully")
    public void sendMoneyByCustomer() throws IOException, ConfigurationException {
        transaction=new Transaction();
        JsonPath jsonPath=transaction.sendMoneyToAnotherCustomer(prop.getProperty("customer1_phone_number"),prop.getProperty("customer2_phone_number"),500);
       int currentBalance = jsonPath.get("currentBalance");
       Utils.setEnvVariable("customer1_balance",jsonPath.get("currentBalance").toString());
       String.valueOf(currentBalance);
       Assert.assertTrue(String.valueOf(currentBalance).contains("485"));

    }
    @Test(priority = 23,description = "Can not check statement if phone number is invalid")

    public void checkStatementByWrongNumber() throws IOException {

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
        Assert.assertTrue(message.contains("User not found"));

    }
    @Test(priority = 24,description = "Customer can check statement successfully if phone number is valid")

    public void checkCustomerStatement() throws IOException {
        transaction = new Transaction();
        transaction.checkCustomerStatement();
        Assert.assertTrue(transaction.getMessage().contains("Transaction list"));

    }





}
