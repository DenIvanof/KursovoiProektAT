package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class BuyCardTest {
    //java -jar artifacts/aqa-shop.jar
    //docker compose up
    //./gradlew clean test
    //./gradlew allureServe
    //http://localhost:8080/

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openSource() {
        open("http://localhost:8080");
        SQLHelper.deleteTable();
    }

    //Покупка обычная
    @Test
    @DisplayName("Покупка APPROVED картой")
    void cardPaymentMustBeApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(0), getValidHolder(), getValidCVC());
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(cardinfo);
        purchasepage.paymentSuccessFull();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Покупка DECLINED картой")
    void cardPaymentMustBeDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(0), getValidHolder(), getValidCVC());
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(cardinfo);
        purchasepage.declinedPayment();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Незаполненная форма")
    void sendAnEmptyPurchaseForm() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.emptyForm();
    }

    //Поле карты
    @Test
    @DisplayName("Незаполненное поле номер карты")
    public void emptyCard() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCardNumberEmpty());
        purchasepage.invalidCardFormat();
    }

    @Test
    @DisplayName("Ввод в поле номер карты менее 16 цифр")
    public void cardNumberSmall() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCardNumberSmall());
        purchasepage.invalidCardFormat();
    }

    @Test
    @DisplayName("Ввод в поле номер карты кириллицы")
    public void cyrillicCard() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCardNumberCyrillic());
        purchasepage.invalidCardFormat();
    }

    @Test
    @DisplayName("Ввод в поле номер карты латиницы")
    public void latinCard() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCardNumberLatin());
        purchasepage.invalidCardFormat();
    }

    @Test
    @DisplayName("Ввод в поле номер карты спецсимвол")
    public void symbolCard() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCardNumberSymbol());
        purchasepage.invalidCardFormat();
    }

    //Поле месяц
    @Test
    @DisplayName("Поле месяц не заполнено")
    public void emptyMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getMonthEmpty());
        purchasepage.invalidMonthFormat();
    }

    @Test
    @DisplayName("Нулевой месяц")
    public void twoZerosMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getMonthTwoZeros());
        purchasepage.invalidMonthFormat();
    }

    @Test
    @DisplayName("Поле месяц более 12 ")
    public void moreThanMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getMonth13());
        purchasepage.invalidCardExpirationDate();
    }

    @Test
    @DisplayName("Ввод в поле месяц латиницы")
    public void latinMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getMonthLatin());
        purchasepage.invalidMonthFormat();
    }

    @Test
    @DisplayName("Ввод в поле месяц кириллицы")
    public void cyrillicMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getMonthCyrillic());
        purchasepage.invalidMonthFormat();
    }

    @Test
    @DisplayName("Ввод в поле месяц спецсимвол")
    public void simbolMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getMonthSymbol());
        purchasepage.invalidMonthFormat();
    }

    @Test
    @DisplayName("Одна цифра в поле месяц")
    public void oneDigitMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getMonthOneDigit());
        purchasepage.invalidMonthFormat();
    }

    //Поле год
    @Test
    @DisplayName("Поле год не заполнено")
    public void notValidEmptyYear() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getYearEmpty());
        purchasepage.invalidYearFormat();
    }

    @Test
    @DisplayName("Поле год одна цифра")
    public void oneDigitYear() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getYearOneDigit());
        purchasepage.invalidYearFormat();
    }

    @Test
    @DisplayName("Год меньше текущего на 1")
    public void lessThanCurrentOneYear() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getYearLessThanCurrent());
        purchasepage.theCardExpired();
    }

    @Test
    @DisplayName("Год больше текущего на 10")
    public void yearLongerThanTheCurrent() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getYearMoreThanTheCurrentOne());
        purchasepage.invalidCardExpirationDate();
    }

    @Test
    @DisplayName("Ввод в поле год спецсимвола")
    public void symbolYear() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getYearSymbol());
        purchasepage.invalidYearFormat();
    }

    @Test
    @DisplayName("Ввод в поле год латиницы")
    public void latinYear() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getYearLatin());
        purchasepage.invalidYearFormat();
    }

    @Test
    @DisplayName("Ввод в поле год кириллицы")
    public void cyrillicYear() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getYearCyrillic());
        purchasepage.invalidYearFormat();
    }

    //Поле владелец
    @Test
    @DisplayName("Незаполненное поле владелец")
    public void emptyInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getHolderEmpty());
        purchasepage.invalidHolderFormat();
    }

    @Test
    @DisplayName("Поле владелец на кириллице")
    public void cyrillicInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getHolderCyrillic());
        purchasepage.invalidHolderFormat();
    }

    @Test
    @DisplayName("Спецсимвол в поле владелец")
    public void symbolInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getHolderSymbol());
        purchasepage.invalidHolderFormat();
    }

    @Test
    @DisplayName("Цифра в поле владелец")
    public void digitInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getHolderDigit());
        purchasepage.invalidHolderFormat();
    }

    @Test
    @DisplayName("Одна буква в поле владелец")
    public void oneLetterInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getHolderOneLetter());
        purchasepage.invalidHolderFormat();
    }

    @Test
    @DisplayName("Более 100 букв в поле владелец")
    public void moreThan100lettersPerHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getHolderMoreThan100());
        purchasepage.invalidHolderFormat();
    }

    //Поле CVC
    @Test
    @DisplayName("Ввод в поле CVC/CVV спецсимвола")
    public void symbolInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCVCSymbol());
        purchasepage.invalidCvcFormat();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV латиницы")
    public void lettersInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCVCLatin());
        purchasepage.invalidCvcFormat();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV кириллицы")
    public void cyrillicCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCVCCyrillic());
        purchasepage.invalidCvcFormat();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV двух цифр")
    public void twoDigitInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCVCtwoDigit());
        purchasepage.invalidCvcFormat();
    }

    @Test
    @DisplayName("Незаполненное поле CVC/CVV")
    public void emptyInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.buyCard();
        purchasepage.fillingOutTheForm(DataHelper.getCVCempty());
        purchasepage.invalidCvcFormat();
    }

}
