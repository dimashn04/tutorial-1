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

import java.util.List;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class OrderFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    private CreateProductFunctionalTest createProductFunctionalTest;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
        createProductFunctionalTest = new CreateProductFunctionalTest();
    }

    void createProduct(ChromeDriver driver, String name, int quantity){
        driver.get(String.format(baseUrl + "/product/create"));
        createProductFunctionalTest.fillForm(driver, name, quantity);
        createProductFunctionalTest.clickButton(driver, "Submit");
    }

    void tearDown(ChromeDriver driver, int numOfProducts){
        driver.get(baseUrl + "/product/list");
        for (int i = 0; i < numOfProducts; i++){
            List<WebElement> links = driver.findElements(By.tagName("button"));
            WebElement linkDelete = links.get(0);
            linkDelete.click();
        }
    }

    @Test
    void testCreateOrderGetTitle(ChromeDriver driver) throws Exception{
        driver.get(baseUrl + "/order/create");
        String pageTitle = driver.getTitle();

        assertEquals("Create Order", pageTitle);
    }

    @Test
    void testOrderHistory(ChromeDriver driver) throws Exception{
        driver.get(baseUrl + "/order/history");
        String pageTitle = driver.getTitle();
        assertEquals("Get Order History", pageTitle);
    }

    @Test
    void testCreateOrderCreateOrderAndHistory(ChromeDriver driver) throws Exception{
        createProduct(driver, "test", 0);

        driver.get(baseUrl + "/order/create");
        String pageTitle = driver.getTitle();
        assertEquals("Create Order", pageTitle);

        driver.findElement(By.id("quantityInput")).clear();
        driver.findElement(By.id("quantityInput")).sendKeys(String.valueOf(10));

        driver.findElement(By.id("author")).clear();
        driver.findElement(By.id("author")).sendKeys("a");
        driver.findElement(By.className("btn")).click();

        pageTitle = driver.getTitle();
        assertEquals("Get Order History", pageTitle);

        driver.findElement(By.id("nameInput")).clear();
        driver.findElement(By.id("nameInput")).sendKeys("a");
        driver.findElement(By.xpath("//button[text()='Submit']")).click();

        pageTitle = driver.getTitle();
        assertEquals("Order History", pageTitle);
        List<WebElement> tabelrow = driver.findElements(By.tagName("tr"));
        assertEquals(4,tabelrow.size());

        tearDown(driver,1);
    }
}
