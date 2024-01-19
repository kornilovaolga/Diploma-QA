package test;

import data.DataHelper;
import data.SQLHelper;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPageTest {
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
    void shouldPurchaseCreditAllFieldValidApprovedCard() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getApprovedCard());
        credit.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditPaymentStatus());
    }

    // Негативный сценарий
    // Номер карты

    @Test
    void shouldPurchaseCreditWithEmptyFieldCardNumber() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyFieldCardNumber());
        credit.waitForValidationFieldMessage();
    }

    @Test
    void shouldPurchaseCreditWithInvalidCardNumber() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidCardNumber());
        credit.waitForWrongFormatMessage();
    }

    @Test
    void shouldPurchaseCreditWithInvalidCardNumberOnlyZero() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getAllZeroCardNumber());
        credit.waitForWrongFormatMessage();
    }

    @Test
    void shouldPurchaseCreditAllFieldValidDeclinedCard() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getDeclinedCard());
        credit.notificationErrorIsVisible();
    }

    // Месяц
    @Test
    void shouldPurchaseCreditWithFieldMonthOver12() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidMonth());
        credit.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldPurchaseCreditWithEmptyFieldMonth() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyMonth());
        credit.waitForValidationFieldMessage();
    }

    // Год
    @Test
    void shouldPurchaseCreditWithFieldYearIsLastYear() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getLastYear());
        credit.waitForCardExpiredMessage();
    }

    @Test
    void shouldPurchaseCreditWithEmptyFieldYear() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyYear());
        credit.waitForValidationFieldMessage();
    }

    // Владелец
    @Test
    void shouldPurchaseCreditWithFieldHolderIsOnlyName() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderCard());
        credit.waitForWrongFormatMessage();
    }

    @Test
    void shouldPurchaseCreditWithFieldHolderIsEmpty() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyHolderCard());
        credit.waitForValidationFieldMessage();
    }

    @Test
    void shouldPurchaseCreditWithFieldHolderIfRusSym() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderCardIfRusSym());
        credit.waitForWrongFormatMessage();
    }

    // "CVC/CVV"
    @Test
    void shouldPurchaseCreditWithCvcFieldIsEmpty() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyCVC());
        credit.waitForValidationFieldMessage();
    }

    @Test
    void shouldPurchaseCreditWithCvcFieldOneNumber() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getOneNumberCVC());
        credit.waitForWrongFormatMessage();
    }

    @Test
    void shouldPurchaseCreditWithCvcFieldTwoNumbers() {
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getTwoNumberCVC());
        credit.waitForWrongFormatMessage();
    }
}