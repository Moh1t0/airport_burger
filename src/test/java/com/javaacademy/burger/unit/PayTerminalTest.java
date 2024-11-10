package com.javaacademy.burger.unit;

import com.javaacademy.burger.Currency;
import com.javaacademy.burger.PayTerminal;
import com.javaacademy.burger.Paycheck;
import com.javaacademy.burger.dish.DishType;
import com.javaacademy.burger.exception.NotAcceptedCurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class PayTerminalTest {

    @Test
    @DisplayName("Ситуация №1: Успешная оплата бургера в рублях")
    public void paySuccess() {
        PayTerminal payTerminal = new PayTerminal();
        Paycheck result = payTerminal.pay(DishType.BURGER, Currency.RUB);
        Paycheck paycheck = new Paycheck(new BigDecimal(300), Currency.RUB, DishType.BURGER);
        Assertions.assertEquals(paycheck, result);
    }

    @Test
    @DisplayName("Ситуация №2: Проверка ошибки NotAcceptedCurrencyException")
    public void payFailure() {
        PayTerminal payTerminal = new PayTerminal();
        Assertions.assertThrows(NotAcceptedCurrencyException.class,
                () -> payTerminal.pay(DishType.BURGER, Currency.MOZAMBICAN_DOLLARS));
    }
}
