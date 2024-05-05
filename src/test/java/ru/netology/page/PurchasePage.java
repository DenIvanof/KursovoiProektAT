package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PurchasePage {
    //Начальная страница
    private final SelenideElement Buy = $(byText("Купить"));
    private final SelenideElement Buyaloan = $(byText("Купить в кредит"));
    private final SelenideElement Paymentcard = $(byText("Оплата по карте"));
    private final SelenideElement Creditcard = $(byText("Кредит по данным карты"));

    //Оплата по карте
    public DashboardPage BuyCard() {
        Buy.click();
        Paymentcard.shouldBe(visible);
        return new DashboardPage();
    }
    //Покупка в кредит
    public DashboardPage BuyCreditCard() {
        Buyaloan.click();
        Creditcard.shouldBe(visible);
        return new DashboardPage();
    }

    //Поля формы
    private SelenideElement cardnumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private SelenideElement holder = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvccvv = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continuebutton = $(byText("Продолжить"));

    //Ошибки
    private SelenideElement formatCardError = $(byText("Неверный формат")).parent().$(".input__sub");
    private SelenideElement cardNumberError = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement expiredCardError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement termCardError = $(byText("Истёк срок действия карты"));
    private SelenideElement holderError = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");

    //Заполненная форма
    public void fillingOutTheForm(DataHelper.CardInfo cardInfo) {
        cardnumber.setValue(cardInfo.getCardnumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        holder.setValue(cardInfo.getHolder());
        cvccvv.setValue(cardInfo.getCodcvccvv());
        continuebutton.click();
    }
    //Не заполненная форма
    public void emptyform() {
        continuebutton.click();
        cardNumberError.shouldBe(visible);
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        holderError.shouldBe(visible);
        cvcError.shouldBe(visible);
    }
    //Оплата
    public void paymentSuccessfull() {
        $(".notification_status_ok").shouldBe(Condition.visible, Duration.ofSeconds(30));
    }

    public void declinedPayment() {
        $(byCssSelector("div.notification.notification_status_error.notification_has-closer.notification_stick-to_right.notification_theme_alfa-on-white")).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }
    public void invalidCardFormat() {
        formatCardError.shouldBe(visible);
    }
    public void invalidMonthFormat() {
        monthError.shouldBe(visible);
    }
    public void invalidCardExpirationDate() {
        expiredCardError.shouldBe(visible);
    }
    public void invalidYearFormat() {
        yearError.shouldBe(visible);
    }
    public void theСardExpired() {
        termCardError.shouldBe(visible);
    }
    public void invalidHolderFormat() {
        holderError.shouldBe(visible);
    }
    public void invalidCvcFormat() {
        cvcError.shouldBe(visible);
    }
}
