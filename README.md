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

# Tutorial 3  
Apply the SOLID principles you have learned. You are allowed to modify the source code according to the principles you want to implement. Please answer the following questions:  
1) Explain what principles you apply to your project!  
   All principles have been adhered after I've done some refactoring, these include:  
   1. Splitting CarController and ProductController into their each Java file (class) to adhere to SRP  
   2. Changed ```private CarServiceImpl carService;``` to ```private CarService carService;``` to adhere to OCP  
   3. Removed the ```extend ProductController``` in CarController class since CarController has only one job and that is for controlling cars mot products to adhere to SRP  
   4. Removed the UUID setter in the Product model constructor and moved the UUID setting system to the create method in the ProductRepository to adhere to SRP  
   5. Since I removed the ```extend ProductController``` in CarController class, that means there are no more inheritence from superclass to subclass, so the program adheres to LSP. The only inheritence/implementation left is for the service interfaces and their implementations, and these also adhere to LSP because if I want to switch form using```private CarService carService;``` to ```private CarServiceImpl carService;``` , I can since they're interchangable.
   6. The program adheres to ISP as well, because both interfaces are related to one object each only (ProductService relates to Product and CarService relates to Car), both interfaces are cohesive, and both interface lets users implement only the methods they need  
   7. Both controllers (after I modified an attribute on CarController, see point 2) now adhere to DIP. Both of them now rely on abstractions  

2) Explain the advantages of applying SOLID principles to your project with examples.  
   By adhering to OCP and DIP on both my controllers (see points 2 and 7 at number 1), if in the future I decide to make a new implementation like BetterCarServiceImpl or BetterProductServiceImpl, then I won't need to change or modify my controllers. Also, I can hide sensitive implementation details, reduce code complexity, ease code testing, and reduce coupling so I can swap one implementation for another.  

   As for SRP, since I split the two controllers and modified my models and repositories (see points 1, 3 and 4 at number 1), I now have better code organization. In return, understanding and even maintaning won't be an issue. This will also help me communicate to other developers, lecturers, and TAs. SRP also ease testing because it eases creating unit test since each class only have single responsiblities. SRP will also reduce coupling so when I make changes on one class, it won't have ripple effects on other classes. This in turn, will make the codebase less buggy. Scalability is also better when adhering to SRP as I can add new features or modifying existing ones without having a giant makeover to the codebase.  

   For LSP and ISP, since now I have minimal extension and implementation (only for ProductServiceImpl and CarServiceImpl, see points 5 and 6 at number 1 too), I now can implement enchanced polymorphism where subclasses and their base class can be used interchangebly, for example: I can actually swap out CarSevice with CarServiceImpl in CarController. My program now has reduced dependencies too, making my codes in my program more decoupled. Interfaces in my program also have more focused design, where each interface has a specific responsiblity (Ex: ProductService only for products and CarService only for cars).  

3) Explain the disadvantages of not applying SOLID principles to your project with examples.  
   If the program don't adhere to OCP and DIP, it will have a risk of tight coupling between the controller and the implementations. This could lead to horrible testing environment and not being to easily swap between implementations for testing. It will also hinder changing or adding new implementations in the future, since I will have to modify my controller, in which it could potentially introduce more bugs and maintenance overhead.  

   If I don't adhere to SRP and have the controller, repository, or model handle multiple responsibilities, I would risk having a hard time trying to understand and maintain my own code. This could lead to increased complexity, bugs, and harder communications with other developers, lecturers, and TAs. Testing will also be hindered because I need to test multiple responsiblities in a single test, and changes to one responsibility would have a ripple effect on other responsibilities.  

   If I don't adhere to LSP and ISP, I would risk having a codebase that is tightly coupled and less flexible because of interfaces, abstractions or any base classes with unnecessary methods or dependencies. If I need to add new features or modify existing ones, I may need to modify multiple classes.  

# Tutorial 4  
**Reflection**  
1. Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.  
   - Correctness:  
      1. Do I have enough functional tests to reassure myself that my application really works, from point of view of the user? Yes  
      2. Am I testing all edge cases thoroughly? Yes  
      3. Do I have tests that check whether all my components fit together properly? Could some integrated tests do this, or are functional tests enough? Yes. Functional tests are enough but with integrated tests it could be more thoroughly checked.  
   - Maintainability:  
      1. Are my tests giving me the confidence to refactor my code, fearlessly and frequently? Yes  
      2. Are my tests helping me to drive out a good design? If I have a lot of integration tests but less unit tests, do I need to make more unit tests to get better feedback on my code design? Yes. Depends, if I feel like that unit tests count are not enough, then I would add more. Same goes for integration tests.  
   - Production workflow:  
      1. Are my feedback cycles as fast as I would like them? When do I get warned about bugs, and is there any practical way to make that happen sooner? Yes. As of now, it already satifies me, so I won't try to change things yet.  
      2. Is there some way that I could write faster integration tests that would give me feedback quicker? Regularly review and refactor tests.  
      3. Can I run a subset of the full test suite when I need to? Yes  
      4. Am I spending to much time waiting for tests to run, and thus less time in a productive flow state? No  

2. You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.  
My tests have successfully followed F.I.R.S.T because my tests run ASAP so they don't interrupt my workflow. I separated my tests into unit tests and functional tests. I also avoid waiting for another subsystem or function when using unit tests. My tests don't interfere, change fucntion states, or dependent on other test cases. I also implemented dummy, mocks, setUp, and tearDown to avoid duplication and clean up objects. My tests are consistent on repeated runs. If my function invloves calling other functions, then I used Test Double techniques. My tests are self-validating because they have strict assertions. My tests are thorough and timely because they cover all happy and unhappy paths and also cover all possible errors and results.  