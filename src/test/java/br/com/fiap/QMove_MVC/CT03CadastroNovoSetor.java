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

    // Cenário de teste: Cadastro de novo setor com sucesso
    @Test
    public void cadastroComSucesso() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            LoginAuxiliar.realizarLogin(driver);

            // Agora o usuário já está logado — pode acessar o cadastro
            driver.get(BASE_URL + "/setores/novo");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement nomeInput = driver.findElement(By.id("nome"));
            WebElement codigoInput = driver.findElement(By.id("codigo"));

            nomeInput.sendKeys("Teste");
            codigoInput.sendKeys("Rosa");

            driver.findElement(By.cssSelector("button[type='submit'], .btn.btn-primary")).click();

            wait.until(ExpectedConditions.urlContains("/setores"));
            Assertions.assertTrue(driver.getCurrentUrl().contains("/setores"));

        } finally {
            driver.quit();
        }
    }
    @Test
    public void excluirSetorComMotosVinculadas() {
    WebDriverManager.chromedriver().setup();
    WebDriver driver = new ChromeDriver();

    try {
        LoginAuxiliar.realizarLogin(driver);
        // Dado que o usuário está na página de listagem de setores
            driver.get(BASE_URL + "/setores");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Quando tenta excluir um setor que possui motos vinculadas
        // (supondo que o botão de excluir tenha um ícone de lixeira ou classe "btn-danger")
        WebElement linkExcluir = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(., 'Excluir') and contains(@class, 'btn-danger')]")
        ));
        linkExcluir.click();

        // Se aparecer um alerta (popup JS confirmando exclusão), confirma
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // se não tiver alerta, ignora
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


