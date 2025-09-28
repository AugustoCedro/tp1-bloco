package org.example;

import io.javalin.Javalin;
import org.example.controller.ClientController;
import org.example.view.pages.CreateClientPage;
import org.example.view.pages.ListPage;
import org.example.view.util.Config;
import org.example.view.util.DriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // permite @BeforeAll e @AfterAll não estáticos
public class SeleniumTest {

    private Javalin app;
    private WebDriver driver;
    private ListPage listPage;
    private ClientController clientController;

    private static final int PORT = 7000; // porta fixa para todos os testes
    private static final String BASE_URL = "http://localhost:" + PORT + "/clients";

    @BeforeAll
    public void setupAll() {
        // Inicializa Javalin apenas uma vez
        app = Javalin.create(config -> {}).start(PORT);
        clientController = new ClientController(app);

        // Inicializa WebDriver
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
    }

    @AfterAll
    public void teardownAll() {
        if (driver != null) driver.quit();
        if (app != null) app.stop();
    }

    @BeforeEach
    public void beforeEachTest() {
        // Sempre abre a página inicial antes de cada teste
        driver.get(BASE_URL);
        listPage = new ListPage(driver);
    }

    @Test
    public void listPageNavigationTest() {
        listPage.goToCreateClientPage();
        String expectedUrl = BASE_URL + "/new";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void createClientTest() {
        listPage.goToCreateClientPage();

        CreateClientPage createClientPage = new CreateClientPage(driver);
        createClientPage.enterName("Augusto");
        createClientPage.enterEmail("augustocedro@example.com");
        createClientPage.submitCreateClientButton();

        // Verifica se voltou para a lista de clientes
        String expectedUrl = BASE_URL;
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);
    }

    // Você pode adicionar novos testes sem se preocupar com porta ou servidor
}
