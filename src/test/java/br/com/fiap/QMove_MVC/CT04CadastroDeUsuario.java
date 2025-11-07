package br.com.fiap.QMove_MVC;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;



public class CT04CadastroDeUsuario {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080";

    // Cenário de teste: Cadastro de funcionário com sucesso
    @Test
    public void cadastroFuncionarioComSucesso() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Dado que o usuário está logado
            LoginAuxiliar.realizarLogin(driver);
            // E que ele está na página de cadastro de funcionário
            driver.get(BASE_URL + "/funcionarios/novo");

            WebElement nome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
            WebElement email = driver.findElement(By.id("email"));
            WebElement senha = driver.findElement(By.id("senha"));
            WebElement roleUser = driver.findElement(By.id("roles2")); // ROLE_USER
            WebElement botaoSalvar = driver.findElement(By.xpath("//button[contains(.,'Salvar')]"));

            // Quando o usuário preenche os campos corretamente
            nome.sendKeys("Funcionário Teste OK");
            email.sendKeys("func.ok" + System.currentTimeMillis() + "@teste.com");
            senha.sendKeys("123456");
            if (!roleUser.isSelected()) roleUser.click();
            // E clica em salvar
            botaoSalvar.click();

            // Então aguarda redirecionamento ou mensagem de sucesso
            boolean sucesso = false;
            try {
                wait.until(ExpectedConditions.urlContains("/funcionarios"));
                sucesso = true;
            } catch (TimeoutException e) {
                WebElement msgSucesso = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Funcionário cadastrado com sucesso')]")
                ));
                sucesso = msgSucesso.isDisplayed();
            }
            Assertions.assertTrue(sucesso, "Funcionário válido não foi cadastrado com sucesso.");
        } finally {
            driver.quit();
        }
    }

    // Cenário de teste: Validar erro de senha com menos de 6 caracteres
    @Test
    public void validarErroSenhaCurta() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Dado que o usuário está logado
            LoginAuxiliar.realizarLogin(driver);
            // E que está na página de cadastro de funcionário
            driver.get(BASE_URL + "/funcionarios/novo");

            WebElement nome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
            WebElement email = driver.findElement(By.id("email"));
            WebElement senha = driver.findElement(By.id("senha"));
            WebElement botaoSalvar = driver.findElement(By.xpath("//button[contains(.,'Salvar')]"));

            // Quando o usuário preenche a senha com menos de 6 caracteres
            nome.sendKeys("Teste Senha Curta");
            email.sendKeys("teste@teste.com");
            senha.sendKeys("123"); 
            botaoSalvar.click();

            // Então deve ser exibida a mensagem de erro
            WebElement msgErro = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'A senha deve ter pelo menos 6 caracteres')]")
            ));
            Assertions.assertTrue(msgErro.isDisplayed(),
                    "Mensagem de erro de senha curta não foi exibida como esperado.");
        } finally {
            driver.quit();
        }
    }


    // Cenário de teste: Validar erro ao tentar salvar com campos obrigatórios vazios
    @Test
    public void validarErroCamposVazios() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Dado que o usuário está logado
            LoginAuxiliar.realizarLogin(driver);
            // E que ele está na página de cadastro de funcionário
            driver.get(BASE_URL + "/funcionarios/novo");

            // Quando o usuário tenta salvar sem preencher nenhum campo
            WebElement botaoSalvar = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Salvar')]")
            ));
            botaoSalvar.click();

            // Então devem ser exibidas mensagens de erro para todos os campos obrigatórios
            WebElement msgNome = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'O nome é obrigatório')]")
            ));
            WebElement msgEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'O e-mail é obrigatório')]")
            ));
            WebElement msgSenha = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'A senha é obrigatória')]")
            ));
            Assertions.assertTrue(msgNome.isDisplayed(), "Mensagem de erro para nome vazio não exibida.");
            Assertions.assertTrue(msgEmail.isDisplayed(), "Mensagem de erro para e-mail vazio não exibida.");
            Assertions.assertTrue(msgSenha.isDisplayed(), "Mensagem de erro para senha vazia não exibida.");
        } finally {
            driver.quit();
        }
    }

}
