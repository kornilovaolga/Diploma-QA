package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.RestApiHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static data.DataHelper.getApprovedCard;
import static data.RestApiHelper.paymentRequest;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void connectDB() {
        SQLHelper.getConn();
    }

    @AfterEach
    void cleanDB() {
        SQLHelper.cleanDatabase();
    }

    @Test
    void shouldGiveResponseValidApprovedDebitCard() {
        var validApprovedCardForApi = getApprovedCard();
        var response = paymentRequest(validApprovedCardForApi, "/api/v1/pay");
        assertTrue(response.contains(SQLHelper.getDebitPaymentStatus()));
    }

    @Test
    void shouldGiveResponseValidApprovedCreditCard() {
        var validApprovedCardForApi = getApprovedCard();
        var response = paymentRequest(validApprovedCardForApi, "/api/v1/credit");
        assertTrue(response.contains(SQLHelper.getCreditPaymentStatus()));
    }
}