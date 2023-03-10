package testrunner;

import controller.Transaction;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;

import java.io.IOException;

public class TransactionTestRunner extends Setup {

    Transaction transaction;
    @Test(priority = 11,description = "Can not deposit to Agent if Agent number if not registered")

   public void depositToInvalidAgent() throws IOException {
        transaction = new Transaction();
        transaction.depositToInvalidAgent();
        Assert.assertTrue(transaction.getMessage().contains("Account does not exist"));

    }



    @Test(priority = 12,description = "Deposit to valid registered agent successfully")


    public void depositToAgent() throws IOException {
        transaction = new Transaction();
        transaction.depositToAgent();
    }
    @Test(priority = 13,description = "Can not deposit to customer if balance is insufficient")

    public void depositToInvalidCustomer() throws IOException, ConfigurationException {
        transaction = new Transaction();
        transaction.depositInsufficientToCustomer1();
        Assert.assertTrue(transaction.getMessage().contains("Insufficient balance"));

    }
    @Test(priority = 14,description = "Deposit to customer successfully")


    public void depositToCustomer1() throws IOException, ConfigurationException {
        transaction = new Transaction();
        transaction.depositToCustomer1();
    }
    @Test(priority = 15,description = "Can not check customer balance if phone number is invalid")


    public void checkInvalidCustomerBalance() throws IOException {
        transaction = new Transaction();
        transaction.checkInvalidCustomerBalance();
        Assert.assertTrue(transaction.getMessage().contains("User not found"));

    }
    @Test(priority = 16,description = "Check customer balance successfully with valid customer number")



    public void checkCustomerBalance() throws IOException {
        transaction = new Transaction();
        transaction.checkCustomerBalance();
        Assert.assertTrue(transaction.getMessage().contains("User balance"));
    }
    @Test(priority = 17,description = "Transaction not found if search by invalid trnxID")


    public void searchByInvalidTrnxID() throws IOException {
        transaction = new Transaction();
        transaction.checkCustomerStatementByInvalidTrnxId();
        Assert.assertTrue(transaction.getMessage().contains("Transaction not found"));

    }
    @Test(priority = 18,description = "Search by valid trnxID transaction successfully found")

    public void searchByTrnxID() throws IOException {
        transaction = new Transaction();
        transaction.checkCustomerStatementByTrnxId();
    }
    @Test(priority = 19,description = "Can not withdraw by customer if agent number is invalid")
    public void withdrawByCustomerToInvalidAgent() throws IOException, ConfigurationException {
        transaction=new Transaction();
        transaction.withdrawByCustomerToInvalidAgent();
        Assert.assertTrue(transaction.getMessage().contains("Account does not exist"));

    }

    @Test(priority = 20,description = "Customer can withdraw successfully to valid agent")
    public void withdrawByCustomer() throws IOException, ConfigurationException {
        transaction=new Transaction();
        transaction.withdrawByCustomer();
        Assert.assertTrue(transaction.getMessage().contains("990"));

    }
    @Test(priority = 21,description = "Customer can not send monet to another customer if another customer number is invalid")


    public void sendMoneyByCustomerToInvalidCustomer() throws IOException, ConfigurationException {
        transaction=new Transaction();
        transaction.sendMoneyToAnotherInvalidCustomer();
        Assert.assertTrue(transaction.getMessage().contains("From/To Account does not exist"));

    }


   @Test(priority = 22,description = "Customer can send money to another valid customer successfully")
    public void sendMoneyByCustomer() throws IOException, ConfigurationException {
        transaction=new Transaction();
        transaction.sendMoneyToAnotherCustomer();
        Assert.assertTrue(transaction.getMessage().contains("485"));

    }
    @Test(priority = 23,description = "Can not check statement if phone number is invalid")

    public void checkStatementByWrongNumber() throws IOException {
        transaction = new Transaction();
        transaction.checkCustomerStatementByWrongNumber();
        Assert.assertTrue(transaction.getMessage().contains("User not found"));
    }
    @Test(priority = 24,description = "Customer can check statement successfully if phone number is valid")

    public void checkCustomerStatement() throws IOException {
        transaction = new Transaction();
        transaction.checkCustomerStatement();
        Assert.assertTrue(transaction.getMessage().contains("Transaction list"));

    }





}
