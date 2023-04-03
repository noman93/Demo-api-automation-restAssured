# Dmoney API Automation using Rest Assured
### This is a complete project where a Dmoney API Testing is automated by using Rest Assured.

## Scenerio
1. Call login API
2. Create  a new customer and an agent
3. Search by the customer phone number
4. Deposit 5000 tk to the Agent from system
5. Deposit 2000 tk by agent to customer 
6. Check balance of customer
7. Check statement by trnxId 
8. Withdraw 1000 tk by customer and assert expected balance
9. Send 500 tk to another customer and assert expected balance
10. Check customer statement

Key test cases(total 24) are written for each module and test suites created including the positive and negative test cases.

## Test Case
https://docs.google.com/spreadsheets/d/1N8u_plpyVsc20YjtqO13jjTlYkFozX9n/edit?usp=share_link&ouid=100119845117710925220&rtpof=true&sd=true

## Technology and Tool Used
- Rest Assured
- TestNG
- commons-configuration
- Jackson
- Java Faker
- Lombok
- Java
- Gradle
- intellij idea 
- Allure


## How to run this project
- clone this project
- hit the following command into the terminal:
  - gradle clean test
- For generating Report in Allure hit
  - allure generate allure-results --clean -o allure-report
  - allure serve allure-results  
  
  
  
  ## Allure reports:
  
  
  
  ![Dmoney-Allure1](https://user-images.githubusercontent.com/28690228/224394546-1e141614-9509-4438-9b42-9451bd0d8b30.png)
  ![Dmoney-allure2](https://user-images.githubusercontent.com/28690228/224394599-29c63840-cf99-4e7d-a60c-d6b0972afbe0.png)
  
  
  ## Gradle Generated report:
  
  ![Dmoney-gradle](https://user-images.githubusercontent.com/28690228/224394854-fa96124b-6a1e-4acd-9fb5-5959fa01726e.png)

  
  
  

  
  
