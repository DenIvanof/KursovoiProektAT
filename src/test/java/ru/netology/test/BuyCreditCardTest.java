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

public class BuyCreditCardTest {
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

    //Покупка в кредит

    @Test
    @DisplayName("Покупка APPROVED картой в кредит")
    void creditPaymentMustBeApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(0), getValidHolder(), getValidCVC());
        var purchasepage = new PurchasePage();
        purchasepage.buyCreditCard();
        purchasepage.fillingOutTheForm(cardinfo);
        purchasepage.paymentSuccessFull();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Покупка DECLINED картой в кредит")
    void creditPaymentMustBeDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(0), getValidHolder(), getValidCVC());
        var purchasepage = new PurchasePage();
        purchasepage.buyCreditCard();
        purchasepage.fillingOutTheForm(cardinfo);
        purchasepage.declinedPayment();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }
}
