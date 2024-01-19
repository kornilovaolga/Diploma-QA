package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    // Позитивный сценарий

    public static Card getApprovedCard() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), getValidCvc());
    }

    // Негативный сценарий
    // Номер карты

    public static Card getEmptyFieldCardNumber() {
        return new Card("", getValidMonth(), getValidYear(), getValidHolder(), getValidCvc());
    }

    public static Card getInvalidCardNumber() {
        return new Card("4444 4444 4444 444", getValidMonth(), getValidYear(), getValidHolder(), getValidCvc());
    }

    public static Card getAllZeroCardNumber() {
        return new Card("0000 0000 0000 0000", getValidMonth(), getValidYear(), getValidHolder(), getValidCvc());
    }

    public static Card getDeclinedCard() {
        return new Card("4444 4444 4444 4442", getValidMonth(), getValidYear(), getValidHolder(), getValidCvc());
    }

    // Месяц
    public static Card getInvalidMonth() {
        return new Card("4444 4444 4444 4441", "13", getValidYear(), getValidHolder(), getValidCvc());
    }

    public static Card getEmptyMonth() {
        return new Card("4444 4444 4444 4441", "", getValidYear(), getValidHolder(), getValidCvc());
    }

    // Год
    public static Card getLastYear() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getPastYear(), getValidHolder(), getValidCvc());
    }

    public static Card getEmptyYear() {
        return new Card("4444 4444 4444 4441", getValidMonth(), "", getValidHolder(), getValidCvc());
    }

    // Владелец
    public static Card getInvalidHolderCard() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), "Ivan", getValidCvc());
    }

    public static Card getEmptyHolderCard() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), "", getValidCvc());
    }

    public static Card getInvalidHolderCardIfRusSym() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getRusHolder(), getValidCvc());
    }

    // "CVC/CVV"
    public static Card getEmptyCVC() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), "");
    }

    public static Card getOneNumberCVC() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), "1");
    }

    public static Card getTwoNumberCVC() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), "12");
    }


    // Генераторы данных
    public static String getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return validMonth;
    }

    public static String getValidYear() {
        String validYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return validYear;
    }

    public static String getPastYear() {
        String pastYear = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return pastYear;
    }

    public static String getValidHolder() {
        return fakerEn.name().firstName() + " " + fakerEn.name().lastName();
    }

    public static String getRusHolder() {
        return fakerRu.name().fullName();
    }

    public static String getValidCvc() {
        return fakerEn.number().digits(3);
    }
}