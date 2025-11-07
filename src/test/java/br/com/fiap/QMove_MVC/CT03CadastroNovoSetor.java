package br.com.fiap.QMove_MVC;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CT03CadastroNovoSetor {
    private static final String BASE_URL = "http://localhost:8080";

    // Cenário de teste 1: Cadastro de novo setor com sucesso
    @Test
    public void cadastroComSucesso() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Dado que o usuário está logado no perfil de administrador
            LoginAuxiliar.realizarLogin(driver);

            // E que ele está na página de cadastro de setor
            driver.get(BASE_URL + "/setores/novo");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nomeInput = driver.findElement(By.id("nome"));
            WebElement codigoInput = driver.findElement(By.id("codigo"));

            // Quando o usuário preenche os campos corretamente
            nomeInput.sendKeys("Teste");
            codigoInput.sendKeys("Rosa");

            // E clica em salvar
            driver.findElement(By.cssSelector("button[type='submit'], .btn.btn-primary")).click();

            // Então a página deve ser redirecionada para /setores, indicando sucesso no cadastro
            wait.until(ExpectedConditions.urlContains("/setores"));
            Assertions.assertTrue(driver.getCurrentUrl().contains("/setores"));

        } finally {
            driver.quit();
        }
    }

    // Cenário de teste 2: Tentativa de exclusão de setor com motos vinculadas
    @Test
    public void excluirSetorComMotosVinculadas() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Dado que o usuário está logado e na página de listagem de setores
            LoginAuxiliar.realizarLogin(driver);
            driver.get(BASE_URL + "/setores");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Quando tenta excluir um setor que possui motos vinculadas
            WebElement linkExcluir = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(., 'Excluir') and contains(@class, 'btn-danger')]")
            ));
            linkExcluir.click();

            // E confirma o alerta de exclusão, se houver
            try {
                driver.switchTo().alert().accept();
            } catch (Exception e) {
                // E se não tiver alerta, ignora
            }

            // Então deve aparecer a mensagem de erro na página
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.tagName("body"),
                    "Não é possível excluir este setor"
            ));

            String pageSource = driver.getPageSource();
            Assertions.assertTrue(
                    pageSource.contains("Não é possível excluir este setor porque existem motos vinculadas a ele."),
                    "Deveria exibir mensagem informando que o setor não pode ser excluído devido a motos vinculadas."
            );

        } finally {
            driver.quit();}
        }
    }


