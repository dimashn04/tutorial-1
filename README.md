# Tutorial 1  
  
Dimas Herjunodarpito Notoprayitno  
2206081282  
Pemrograman Lanjut B  
  
**Reflection 1**  
You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code. **Please write your reflection inside the repository's README.md file.**  
I've implemented some clean codes principles (not all of them), such as:  
- Names for variables & functions are concise, clear, & to the point so my code are self explanatory (no comments needed as of now)  
- Small function sizes and only does one specific job  

Secure coding practices that I've implemented:  
- Post method for creating products

Mistakes I've made while making this assignment:  
- Making test functions inside of main instead of test that caused JUnit to not be detected by Code Editor:  
  Solution: I moved all test functions to test folder  
- Selenium not working:  
  Solution: I forgot to put '$' in the @Value  
- Edit product request seems to keep doing GET instead of POST:  
  Solution: Instead of specifying the Product ID in the URL, I put the Product ID inside a hidden input form field (Not secured but I have not find a better way yet)  
  Update: Fixed delete and edit to use correct request methods  

Things I found on the internet that might help in the future:
- https://www.baeldung.com/spring-boot-crud-thymeleaf  
  Turns out you can use Spring Data JPA to make auto CRUD methods in the repository  

**Reflection 2**  
1. After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?  
   My code feels a lot more reliable after doing unit tests. There's no exact number for how many unit tests should be made for a class, but some experts suggests that it should cover 80% of the code. In order to make that our unit tests are enough to verify our program, we must test all features in our program so there are no unhandled cases. There a lot of false-positives & false-negatives even in 100% code coverage so it doesn't mean that our code is bug free or free of errors.  

2. Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables.  
What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!  
   In my opinion, the code would be less clean. The reason is that the functional tests to check the product details & how many products are in the list is not that much different. Therefore, there will be to many repeated codes. The solution is to combine the two classes into one and make a method for the same lines of code.  

# Tutorial 2  
**Reflection**  
You have implemented a CI/CD process that automatically runs the test suites, analyzes code quality, and deploys to a PaaS. Try to answer the following questions in order to reflect on your attempt completing the tutorial and exercise.  
1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.  
   1. "Avoid the use of value in annotations when its the only element" issue by PMD  
      Solution: Instead of ```@PutMapping(value = "/edit/{id}")```, use ```@PutMapping("/edit/{id}")```  
   2. "The instance method name 'HomePage' doesn't match '[a-z][a-zA-Z0-9]*'" issue by PMD  
      Solution: Use camel case for method names  
   3. "Unnecessary modifier 'public' on method '...': the method is declared in an interface type" issue by PMD  
      Solution: Remove public modifier on interface methods  
   4. "This utility class has a non-private constructor" issue by PMD  
      Solution: This should be dismissed since if we put a private constructor in the EshopApplication class, the program won't start (false-positive)  
2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!  
   Continuous Integration comprises the Code and Test phases. Continuous Delivery/Deployment comprises the Review and Operational phases. My current project has implemented CI/CD. The code in the ci.yml workflow will automate test processes when I pull, push, merge to the my repo. For the deployment, Koyeb also implemented some CI/CD to automate deployment process everytime there's pull, push, merge from the repo.  

100% Code Coverage Proof:  
![](Screenshot%202024-02-14%20152921.png)