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

    void fillForm(ChromeDriver driver, String name, int quantity) {
        WebElement productNameInput = driver.findElement(
            By.id("nameInput"));
        productNameInput.clear();
        productNameInput.sendKeys(name);

        WebElement productQuantityInput = driver.findElement(
            By.id("quantityInput"));
        productQuantityInput.clear();
        productQuantityInput.sendKeys(String.valueOf(quantity));
    }

    void compareProduct(ChromeDriver driver, String expectedName, int expectedQuantity) {
        WebElement productNameInCard = driver.findElement(
            By.xpath("//div[@class='card-body']/h5[@class='card-title']"));
        String actualProductName = productNameInCard.getText();
        assertEquals(expectedName, actualProductName);

        WebElement productQuantityInCard = driver.findElement(
            By.xpath("//div[@class='card-body']/p[contains(text(),'Quantity:')]/span"));
        String actualProductQuantity = productQuantityInCard.getText();
        assertEquals(String.valueOf(expectedQuantity), actualProductQuantity);
    }

    void clickHyperlink(ChromeDriver driver, String linkText) {
        WebElement createProductButton = driver.findElement(
            By.linkText(linkText));
        createProductButton.click();
    }

    void clickButton(ChromeDriver driver, String buttonText) {
        WebElement button = driver.findElement(
            By.xpath("//button[text()='" + buttonText + "']"));
        button.click();
    }

    void deleteAll(ChromeDriver driver) {
        driver.get(baseUrl + "/product/list");
        for (WebElement deleteButton : 
            driver.findElements(By.xpath("//button[text()='Delete']"))) {
            deleteButton.click();
        }
    }

    @Test
    void createProduct_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");
        clickHyperlink(driver, "Create Product");
        fillForm(driver, "Sample Product", 100);
        clickButton(driver, "Submit");
        compareProduct(driver, "Sample Product", 100);
        deleteAll(driver);
    }

    @Test
    void editProduct_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");
        clickHyperlink(driver, "Create Product");
        fillForm(driver, "Sample Product for Edit", 100);
        clickButton(driver, "Submit");
        clickHyperlink(driver, "Edit");
        fillForm(driver, "Edited Product", 200);
        clickButton(driver, "Edit Product");
        compareProduct(driver, "Edited Product", 200);
        deleteAll(driver);
    }

    @Test
    void deleteProduct_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");
        clickHyperlink(driver, "Create Product");
        fillForm(driver, "Sample Product for Delete", 100);
        clickButton(driver, "Submit");
        clickButton(driver, "Delete");
        assertEquals(0, driver.findElements(
            By.xpath("//div[@class='card-body']"))
            .size());
    }
}
