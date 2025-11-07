package br.com.fiap.QMove_MVC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CT01Login {

    private static final String BASE_URL = "http://localhost:8080";

    // Cenário de teste: Login válido
    @Test
    public void testeLoginValido() {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try {
            // Dado que o usuário está na página de login
            driver.get(BASE_URL + "/login");

            // Quando ele insere credenciais válidas
            WebElement emailInput = driver.findElement(By.name("username"));
            WebElement passwordInput = driver.findElement(By.name("password"));
            emailInput.sendKeys("admin@mottu.com");
            passwordInput.sendKeys("admin123");
            passwordInput.submit();

            // Então ele deve ser redirecionado para a página home
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            boolean leftLogin = wait.until(ExpectedConditions.not(
                    ExpectedConditions.urlContains("/login")
            ));
            Assertions.assertTrue(leftLogin, "Deveria sair da página de login após autenticar");

            // E o título da página deve ser o esperado (pagina home)
            String pageTitle = driver.getTitle();
            Assertions.assertEquals("QMove - Home", pageTitle);

        } finally {
            driver.quit();
        }
    }

       // Cenário de teste: Usuário válido e Senha Inválida
       // Validando um erro
        @Test
        public void testeLoginSenhaInvalida() {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            try {
                // Dado que o usuário está na página de login
                driver.get(BASE_URL + "/login");

                // Quando ele insere um usuário válido, mas senha inválida
                WebElement emailInput = driver.findElement(By.name("username"));
                WebElement passwordInput = driver.findElement(By.name("password"));
                emailInput.sendKeys("admin@mottu.com");
                passwordInput.sendKeys("Erro123");
                passwordInput.submit();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                // Então ele deve permanecer na página de login
                wait.until(ExpectedConditions.urlContains("/login"));
                Assertions.assertTrue(driver.getCurrentUrl().contains("/login"),
                        "Deveria permanecer na página de login com senha inválida");
                // E deve ser exibida a mensagem de erro correta
                WebElement mensagemErro = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".alert.alert-error")
                ));
                Assertions.assertEquals("Usuário ou senha inválidos.", mensagemErro.getText().trim(),
                        "Mensagem de erro exibida está incorreta");

            } finally {
                driver.quit();
            }
        }

    }

