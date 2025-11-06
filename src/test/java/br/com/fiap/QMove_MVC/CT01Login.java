package br.com.fiap.QMove_MVC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Teste de login usando Selenium WebDriver.
 * Nota: a aplicação deve estar rodando em http://localhost:8080 antes de executar este teste.
 */
public class CT01Login {

    // Cenário 1: Login com Senha e Usuário Válidos
    @Test
    public void testeLoginValido() {
        // Configura chromedriver automaticamente
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        try {
            // Dado que o usuário está na página de login
            driver.get("http://localhost:8080/login");

            // Seletores do template login.html (name="username" e name="password")
            WebElement emailInput = driver.findElement(By.name("username"));
            WebElement passwordInput = driver.findElement(By.name("password"));

            // Credenciais das migrations
            emailInput.sendKeys("admin@mottu.com");
            passwordInput.sendKeys("admin123");

            // Submete o formulário
            passwordInput.submit();

            // Então o usuário deve ser redirecionado para a página home
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            boolean leftLogin = wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
            Assertions.assertTrue(leftLogin, "Deveria sair da página de login após autenticar");

            // E a página home deve exibir o título correto
            String pageTitle = driver.getTitle();
            Assertions.assertEquals("QMove - Home", pageTitle);
        } finally {
            driver.quit();
        }
    }
}
