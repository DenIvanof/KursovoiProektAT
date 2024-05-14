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
    private final SelenideElement buy = $(byText("Купить"));
    private final SelenideElement buyAloan = $(byText("Купить в кредит"));
    private final SelenideElement paymentCard = $(byText("Оплата по карте"));
    private final SelenideElement creditCard = $(byText("Кредит по данным карты"));

    //Оплата по карте
    public PurchasePage buyCard() {
        buy.click();
        paymentCard.shouldBe(visible);
        return new PurchasePage();
    }

    //Покупка в кредит
    public PurchasePage buyCreditCard() {
        buyAloan.click();
        creditCard.shouldBe(visible);
        return new PurchasePage();
    }

    //Поля формы
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private SelenideElement holder = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcCvv = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continueButton = $(byText("Продолжить"));

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
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        holder.setValue(cardInfo.getHolder());
        cvcCvv.setValue(cardInfo.getCodCvcCvv());
        continueButton.click();
    }

    //Не заполненная форма
    public void emptyForm() {
        continueButton.click();
        cardNumberError.shouldBe(visible);
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        holderError.shouldBe(visible);
        cvcError.shouldBe(visible);
    }

    //Оплата
    public void paymentSuccessFull() {
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

    public void theCardExpired() {
        termCardError.shouldBe(visible);
    }

    public void invalidHolderFormat() {
        holderError.shouldBe(visible);
    }

    public void invalidCvcFormat() {
        cvcError.shouldBe(visible);
    }
}
