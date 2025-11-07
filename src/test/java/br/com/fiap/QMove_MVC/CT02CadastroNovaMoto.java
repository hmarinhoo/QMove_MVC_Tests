package br.com.fiap.QMove_MVC;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CT02CadastroNovaMoto {

    private static final String BASE_URL = "http://localhost:8080";

    // Cenário de teste 1: Cadastro de nova moto com sucesso
    @Test
    public void cadastroComSucesso() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Dado que o usuário está logado e acessa a página de cadastro de moto
            LoginAuxiliar.realizarLogin(driver);

            driver.get(BASE_URL + "/motos/novo");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Quando o usuário preenche todos os campos corretamente e envia o formulário
            WebElement placaInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("placa")));
            WebElement modeloInput = driver.findElement(By.id("modelo"));
            WebElement statusSelect = driver.findElement(By.id("status"));
            WebElement setorSelect = driver.findElement(By.id("setor.id"));

            placaInput.sendKeys("HJD990");
            modeloInput.sendKeys("Mottu-E");
            new Select(statusSelect).selectByVisibleText("Disponível");
            new Select(setorSelect).selectByVisibleText("Disponível - Verde");

            driver.findElement(By.cssSelector("button[type='submit'], .btn.btn-primary")).click();

            // Então a página deve ser redirecionada para /motos, indicando sucesso no cadastro
            wait.until(ExpectedConditions.urlContains("/motos"));
            Assertions.assertTrue(driver.getCurrentUrl().contains("/motos"));

        } finally {
            driver.quit();
        }
    }

    // Cenário de teste 2: Cadastro de moto com campos obrigatórios vazios
    @Test
    public void camposVazios() {
        WebDriver driver = new ChromeDriver();
        try {
            // Dado que o usuário está logado e acessa a página de cadastro de moto
            LoginAuxiliar.realizarLogin(driver);
            driver.get(BASE_URL + "/motos/novo");

            // Quando o usuário tenta salvar sem preencher nenhum campo
            WebElement botaoSalvar = driver.findElement(By.cssSelector("button[type='submit'].btn"));
            botaoSalvar.click();

            // Então o sistema verifica que o formulário não foi enviado, permanecendo na mesma página
            assert driver.getCurrentUrl().equals(BASE_URL + "/motos/novo") :
                    "O formulário não deveria ser enviado com campos vazios.";

            // E valida os campos com suas mensagens de erro
            WebElement placaInput = driver.findElement(By.id("placa"));
            WebElement modeloInput = driver.findElement(By.id("modelo"));
            WebElement statusSelect = driver.findElement(By.id("status"));
            WebElement setorSelect = driver.findElement(By.id("setor.id"));

            String placaMsg = placaInput.getDomProperty("validationMessage");
            String modeloMsg = modeloInput.getDomProperty("validationMessage");
            String statusMsg = statusSelect.getDomProperty("validationMessage");
            String setorMsg = setorSelect.getDomProperty("validationMessage");

            assert placaMsg != null && !placaMsg.isEmpty() : "O campo 'placa' deveria exibir uma mensagem de validação.";
            assert modeloMsg != null && !modeloMsg.isEmpty() : "O campo 'modelo' deveria exibir uma mensagem de validação.";
            assert statusMsg != null && !statusMsg.isEmpty() : "O campo 'status' deveria exibir uma mensagem de validação.";
            assert setorMsg != null && !setorMsg.isEmpty() : "O campo 'setor' deveria exibir uma mensagem de validação.";

        } finally {
            driver.quit();
        }
    }

    // Cenário de teste 3: Cadastro de moto com placa já existente
    @Test
    public void cadastroMotoJaExistente() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Dado que o usuário está logado e acessa a página de cadastro de moto
            LoginAuxiliar.realizarLogin(driver);
            driver.get(BASE_URL +"/motos/novo");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement placaInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("placa")));
            WebElement modeloInput = driver.findElement(By.id("modelo"));
            WebElement statusSelectElement = driver.findElement(By.id("status"));
            WebElement setorSelectElement = driver.findElement(By.id("setor.id"));

            // E que a placa da moto já existe no banco
            placaInput.sendKeys("XYZ1234");
            modeloInput.sendKeys("Mottu-E");
            new Select(statusSelectElement).selectByVisibleText("Em Uso");
            new Select(setorSelectElement).selectByVisibleText("Disponível - Verde");

            // Quando ele clica em salvar
            WebElement botaoSalvar = driver.findElement(By.xpath("//button[contains(., 'Salvar')]"));
            botaoSalvar.click();

            // Então deve permanecer na URL /motos/salvar e exibir mensagem de erro 
            wait.until(ExpectedConditions.urlContains("/motos/salvar"));

            String pageSource = driver.getPageSource();
            Assertions.assertTrue(
                    pageSource.contains("Algo deu errado!") ||
                    pageSource.contains("Já existe uma moto com essa placa cadastrada"),
                    "Deveria exibir mensagem de erro informando que a placa já está cadastrada."
            );

        } finally {
            driver.quit();
        }
    }
}

