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

public class PurchaseTest {
    //java -jar artifacts/aqa-shop.jar
    //docker compose up
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
    //Покупка
@Test
@DisplayName("Покупка APPROVED картой")
void сardPaymentMustBeApproved() {
    var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    var purchasepage = new PurchasePage();
    purchasepage.BuyCard();
    var form = new PurchasePage();
    form.fillingOutTheForm(cardinfo);
    form.paymentSuccessfull();
    assertEquals("APPROVED", SQLHelper.getPaymentStatus());
}
    @Test
    @DisplayName("Покупка DECLINED картой")
    void сardPaymentMustBeDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(cardinfo);
        form.declinedPayment();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }
    @Test
    @DisplayName("Покупка APPROVED картой в кредит")
    void creditPaymentMustBeApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
        var purchasepage = new PurchasePage();
        purchasepage.BuyCreditCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(cardinfo);
        form.paymentSuccessfull();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }
    @Test
    @DisplayName("Покупка DECLINED картой в кредит")
    void creditPaymentMustBeDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
        var purchasepage = new PurchasePage();
        purchasepage.BuyCreditCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(cardinfo);
        form.declinedPayment();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }
    @Test
    @DisplayName("Незаполненная форма")
    void sendAnEmptyPurchaseForm() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.emptyform();
    }
    //Поле карты
    @Test
    @DisplayName("Незаполненное поле номер карты")
    public void emptyCard() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCardNumberEmpty());
        form.invalidCardFormat();
    }
    @Test
    @DisplayName("Ввод в поле номер карты менее 16 цифр")
    public void cardNumberSmall() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCardNumberSmall());
        form.invalidCardFormat();
    }
    @Test
    @DisplayName("Ввод в поле номер карты кириллицы")
    public void cyrillicCard() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCardNumberCyrillic());
        form.invalidCardFormat();
    }
    @Test
    @DisplayName("Ввод в поле номер карты латиницы")
    public void latinCard() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCardNumberLatin());
        form.invalidCardFormat();
    }
    @Test
    @DisplayName("Ввод в поле номер карты спецсимвол")
    public void symbolCard() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCardNumberSymbol());
        form.invalidCardFormat();
    }
    //Поле месяц
    @Test
    @DisplayName("Поле месяц не заполнено")
    public void emptyMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getMonthEmpty());
        form.invalidMonthFormat();
    }
    @Test
    @DisplayName("Нулевой месяц")
    public void twoZerosMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getMonthTwoZeros());
        form.invalidMonthFormat();
    }
    @Test
    @DisplayName("Поле месяц более 12 ")
    public void moreThanMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getMonth13());
        form.invalidCardExpirationDate();
    }
    @Test
    @DisplayName("Ввод в поле месяц латиницы")
    public void latinMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getMonthLatin());
        form.invalidMonthFormat();
    }
    @Test
    @DisplayName("Ввод в поле месяц кириллицы")
    public void cyrillicMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getMonthCyrillic());
        form.invalidMonthFormat();
    }
    @Test
    @DisplayName("Ввод в поле месяц спецсимвол")
    public void simbolMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getMonthSymbol());
        form.invalidMonthFormat();
    }
    @Test
    @DisplayName("Одна цифра в поле месяц")
    public void oneDigitMonth() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getMonthOneDigit());
        form.invalidMonthFormat();
    }
    //Поле год
    @Test
    @DisplayName("Поле год не заполнено")
    public void notValidEmptyYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getYearEmpty());
        form.invalidYearFormat();
    }
    @Test
    @DisplayName("Поле год одна цифра")
    public void oneDigitYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getYearOneDigit());
        form.invalidYearFormat();
    }
    @Test
    @DisplayName("Год меньше текущего на 1")
    public void lessThanCurrentOneYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getYearLessThanCurrent());
        form.theСardExpired();
    }
    @Test
    @DisplayName("Год больше текущего на 10")
    public void yearLongerThanTheCurrent() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getYearMoreThanTheCurrentOne());
        form.invalidCardExpirationDate();
    }
    @Test
    @DisplayName("Ввод в поле год спецсимвола")
    public void symbolYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getYearSymbol());
        form.invalidYearFormat();
    }
    @Test
    @DisplayName("Ввод в поле год латиницы")
    public void latinYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getYearLatin());
        form.invalidYearFormat();
    }
    @Test
    @DisplayName("Ввод в поле год кириллицы")
    public void cyrillicYear() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getYearCyrillic());
        form.invalidYearFormat();
    }
    //Поле владелец
    @Test
    @DisplayName("Незаполненное поле владелец")
    public void emptyInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getHolderEmpty());
        form.invalidHolderFormat();
    }
    @Test
    @DisplayName("Поле владелец на кириллице")
    public void cyrillicInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getHolderCyrillic());
        form.invalidHolderFormat();
    }
    @Test
    @DisplayName("Спецсимвол в поле владелец")
    public void symbolInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getHolderSymbol());
        form.invalidHolderFormat();
    }
    @Test
    @DisplayName("Цифра в поле владелец")
    public void digitInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getHolderDigit());
        form.invalidHolderFormat();
    }
    @Test
    @DisplayName("Одна буква в поле владелец")
    public void oneLetterInHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getHolderOneLetter());
        form.invalidHolderFormat();
    }
    @Test
    @DisplayName("Более 100 букв в поле владелец")
    public void moreThan100lettersPerHolder() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getHolderMoreThan100());
        form.invalidHolderFormat();
    }
    //Поле CVC
    @Test
    @DisplayName("Ввод в поле CVC/CVV спецсимвола")
    public void symbolInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCVCSymbol());
        form.invalidCvcFormat();
    }
    @Test
    @DisplayName("Ввод в поле CVC/CVV латиницы")
    public void lettersInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCVCLatin());
        form.invalidCvcFormat();
    }
    @Test
    @DisplayName("Ввод в поле CVC/CVV кириллицы")
    public void cyrillicCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCVCCyrillic());
        form.invalidCvcFormat();
    }
    @Test
    @DisplayName("Ввод в поле CVC/CVV двух цифр")
    public void twoDigitInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCVCtwoDigit());
        form.invalidCvcFormat();
    }
    @Test
    @DisplayName("Незаполненное поле CVC/CVV")
    public void emptyInCVC() {
        var purchasepage = new PurchasePage();
        purchasepage.BuyCard();
        var form = new PurchasePage();
        form.fillingOutTheForm(DataHelper.getCVCempty());
        form.invalidCvcFormat();
    }

}
