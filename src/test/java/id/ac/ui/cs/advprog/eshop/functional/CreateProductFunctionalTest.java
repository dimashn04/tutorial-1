package id.ac.ui.cs.advprog.eshop.functional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");
        
        WebElement createProductButton = driver.findElement(
            By.linkText("Create Product"));
        createProductButton.click();

        WebElement productNameInput = driver.findElement(
            By.id("nameInput"));
        productNameInput.clear();
        productNameInput.sendKeys("Sample Product");

        WebElement productQuantityInput = driver.findElement(
            By.id("quantityInput"));
        productQuantityInput.clear();
        productQuantityInput.sendKeys("100");

        WebElement submitButton = driver.findElement(
            By.xpath("//button[text()='Submit']"));
        submitButton.click();

        WebElement productNameInCard = driver.findElement(
            By.xpath("//div[@class='card-body']/h5[@class='card-title']"));
        String actualProductName = productNameInCard.getText();
        assertEquals("Sample Product", actualProductName);

        WebElement productQuantityInCard = driver.findElement(
            By.xpath("//div[@class='card-body']/p[contains(text(),'Quantity:')]/span"));
        String actualProductQuantity = productQuantityInCard.getText();
        assertEquals("100", actualProductQuantity);
    }
}
