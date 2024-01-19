package test;

import data.DataHelper;
import data.SQLHelper;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentPageTest {
    public static String url = System.getProperty("sut.url");

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");

    }

    @BeforeEach
    public void openPage() {
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    // Позитивный сценарий

    @Test
    void shouldBuyAllFieldValidApprovedCard() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getApprovedCard());
        payment.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getDebitPaymentStatus());
    }

    // Негативный сценарий
    // Номер карты

    @Test
    void shouldBuyWithEmptyFieldDebitCardNumber() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyFieldCardNumber());
        payment.waitForValidationFieldMessage();
    }

    @Test
    void shouldBuyWithInvalidDebitCard() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidCardNumber());
        payment.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyAllZeroNumberDebitCard() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getAllZeroCardNumber());
        payment.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyAllFieldValidDeclinedCard() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getDeclinedCard());
        payment.notificationErrorIsVisible();
    }

    // Месяц
    @Test
    void shouldBuyWithFieldMonthOver12() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidMonth());
        payment.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldBuyWithFieldMonthIsEmpty() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyMonth());
        payment.waitForValidationFieldMessage();
    }

    // Год
    @Test
    void shouldBuyWithFieldYearIsLastYear() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getLastYear());
        payment.waitForCardExpiredMessage();
    }

    @Test
    void shouldBuyWithFieldYearIsEmpty() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyYear());
        payment.waitForValidationFieldMessage();
    }

    // Владелец
    @Test
    void shouldBuyWithFieldHolderOnlyName() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderCard());
        payment.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyWithEmptyFieldHolder() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyHolderCard());
        payment.waitForValidationFieldMessage();
    }

    @Test
    void shouldBuyWithFieldHolderCardIfRusSym() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderCardIfRusSym());
        payment.waitForWrongFormatMessage();
    }

    // "CVC/CVV"
    @Test
    void shouldBuyWithEmptyCvcField() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyCVC());
        payment.waitForValidationFieldMessage();
    }

    @Test
    void shouldBuyWithCVCFieldOneNumber() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getOneNumberCVC());
        payment.waitForWrongFormatMessage();
    }

    @Test
    void shouldBuyWithCVCFieldTwoNumber() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getTwoNumberCVC());
        payment.waitForWrongFormatMessage();
    }
}