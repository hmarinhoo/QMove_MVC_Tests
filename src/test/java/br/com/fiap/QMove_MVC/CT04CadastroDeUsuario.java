package br.com.fiap.QMove_MVC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CT04CadastroDeUsuario {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080";

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Cadastro de funcionário com dados válidos e permissão marcada")
    public void cadastroFuncionarioComSucesso() {
        LoginAuxiliar.realizarLogin(driver);
        driver.get(BASE_URL + "/funcionarios/novo");

        WebElement nome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
        WebElement email = driver.findElement(By.id("email"));
        WebElement senha = driver.findElement(By.id("senha"));
        WebElement roleUser = driver.findElement(By.id("roles2")); // ROLE_USER
        WebElement botaoSalvar = driver.findElement(By.xpath("//button[contains(.,'Salvar')]"));

        nome.sendKeys("Funcionário Teste OK");
        email.sendKeys("func.ok" + System.currentTimeMillis() + "@teste.com");
        senha.sendKeys("123456");
        if (!roleUser.isSelected()) roleUser.click();
        botaoSalvar.click();

        // Aguarda redirecionamento ou mensagem de sucesso
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
    }

    @Test
    @DisplayName("Validar erro de senha com menos de 6 caracteres")
    public void validarErroSenhaCurta() {
        LoginAuxiliar.realizarLogin(driver);
        driver.get(BASE_URL + "/funcionarios/novo");

        WebElement nome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
        WebElement email = driver.findElement(By.id("email"));
        WebElement senha = driver.findElement(By.id("senha"));
        WebElement botaoSalvar = driver.findElement(By.xpath("//button[contains(.,'Salvar')]"));

        nome.sendKeys("Teste Senha Curta");
        email.sendKeys("teste@teste.com");
        senha.sendKeys("123"); // senha curta
        botaoSalvar.click();

        WebElement msgErro = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'A senha deve ter pelo menos 6 caracteres')]")
        ));

        Assertions.assertTrue(msgErro.isDisplayed(),
                "Mensagem de erro de senha curta não foi exibida como esperado.");
    }

    @Test
    @DisplayName("Validar erro de e-mail sem '@'")
    public void validarErroEmailInvalido() {
        LoginAuxiliar.realizarLogin(driver);
        driver.get(BASE_URL + "/funcionarios/novo");

        WebElement nome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
        WebElement email = driver.findElement(By.id("email"));
        WebElement senha = driver.findElement(By.id("senha"));
        WebElement botaoSalvar = driver.findElement(By.xpath("//button[contains(.,'Salvar')]"));

        nome.sendKeys("Teste Email Invalido");
        email.sendKeys("testeemail.com"); // sem @
        senha.sendKeys("123456");
        botaoSalvar.click();

        boolean mensagemExibida = false;
        try {
            WebElement msgErro = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Inclua um')]")
            ));
            mensagemExibida = msgErro.isDisplayed();
        } catch (TimeoutException e) {
            String validationMessage = email.getAttribute("validationMessage");
            Assertions.assertTrue(validationMessage.contains("@") || validationMessage.contains("Inclua"),
                    "Mensagem de validação de e-mail inválido não exibida corretamente.");
            mensagemExibida = true;
        }

        Assertions.assertTrue(mensagemExibida,
                "A mensagem de erro para e-mail inválido não foi exibida.");
    }

    @Test
    @DisplayName("Validar erros com todos os campos vazios")
    public void validarErroCamposVazios() {
    LoginAuxiliar.realizarLogin(driver);
    driver.get(BASE_URL + "/funcionarios/novo");

    WebElement botaoSalvar = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(.,'Salvar')]")
    ));

    // Não preenche nenhum campo
    botaoSalvar.click();

    // Valida mensagens de erro
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
}

}
