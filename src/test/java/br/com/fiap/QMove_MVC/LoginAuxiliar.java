package br.com.fiap.QMove_MVC;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginAuxiliar {

    private static final String BASE_URL = "http://localhost:8080";

    public static WebDriver realizarLogin(WebDriver driver) {
        driver.get(BASE_URL + "/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));

        emailInput.sendKeys("admin@mottu.com");
        passwordInput.sendKeys("admin123");
        passwordInput.submit();

        wait.until(ExpectedConditions.urlContains("/home"));
        return driver;
    }
}