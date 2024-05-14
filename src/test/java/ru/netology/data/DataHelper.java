package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.lang.String.format;

public class DataHelper {
    private DataHelper() {
    }

    public static final Faker faker = new Faker(new Locale("en"));

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String holder;
        String codCvcCvv;
    }

    //Номер карты
    //Валидные значения
    public static String getApprovedCardNumber() {
        return ("1111 2222 3333 4444");
    }

    public static String getDeclinedCardNumber() {
        return ("5555 6666 7777 8888");
    }

    //Невалидные значения
    public static CardInfo getCardNumberEmpty() {
        return new CardInfo("", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberSmall() {
        return new CardInfo("1111 2222 3333 44", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberCyrillic() {
        return new CardInfo("1111 2222 3333 44гг", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberLatin() {
        return new CardInfo("1111 2222 3333 44ff", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberSymbol() {
        return new CardInfo("1111 2222 3333 44@&", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    //Месяц
    //Валидные значения
    public static String getValidMonth() {
        LocalDate localDate = LocalDate.now();
        return format("%02d", localDate.getMonthValue());
    }

    //Невалидные значения
    public static CardInfo getMonthEmpty() {
        return new CardInfo(getApprovedCardNumber(), "", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonth13() {
        return new CardInfo(getApprovedCardNumber(), "13", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthSymbol() {
        return new CardInfo(getApprovedCardNumber(), "1!", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthLatin() {
        return new CardInfo(getApprovedCardNumber(), "f2", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthCyrillic() {
        return new CardInfo(getApprovedCardNumber(), "г2", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthOneDigit() {
        return new CardInfo(getApprovedCardNumber(), "0", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthTwoZeros() {
        return new CardInfo(getApprovedCardNumber(), "00", getValidYear(), getValidHolder(), getValidCVC());
    }

    //Год
    //Валидные значения
    public static String getValidYear() {
        int year = 0;
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }

    //Невалидные значения
    public static String getYearLess() {
        int year = -1;
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getYearMore() {
        int year = 10;
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static CardInfo getYearLessThanCurrent() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getYearLess(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearMoreThanTheCurrentOne() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getYearMore(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearSymbol() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "2%", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearLatin() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "qw", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearCyrillic() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "цй", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearOneDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "2", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "", getValidHolder(), getValidCVC());
    }

    //Владелец
    //Валидные значения
    public static String getValidHolder() {
        return faker.name().firstName();
    }

    //Невалидные значения
    public static CardInfo getHolderCyrillic() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Денис", getValidCVC());
    }

    public static CardInfo getHolderSymbol() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "%denis", getValidCVC());
    }

    public static CardInfo getHolderDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "1denis", getValidCVC());
    }

    public static CardInfo getHolderOneLetter() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "S", getValidCVC());
    }

    public static CardInfo getHolderMoreThan100() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Aaaaaaaaaaaaaaaaaaaaaaaaadddddddddddddddddddddddddlllllllllllllllllllllllllyyyyyyyyyyyyyyyyyyyyyyyyyeeeeeeeeee", getValidCVC());
    }

    public static CardInfo getHolderEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "", getValidCVC());
    }

    //CVC/CVV
    //Валидные значения
    public static String getValidCVC() {
        return ("123");
    }

    //Невалидные значения
    public static CardInfo getCVCSymbol() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "12#");
    }

    public static CardInfo getCVCLatin() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "22r");
    }

    public static CardInfo getCVCCyrillic() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "22r");
    }

    public static CardInfo getCVCtwoDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "22");
    }

    public static CardInfo getCVCempty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "");
    }
}
