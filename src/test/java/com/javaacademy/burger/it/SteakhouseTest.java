package com.javaacademy.burger.it;


import com.javaacademy.burger.Currency;
import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.PayTerminal;
import com.javaacademy.burger.Paycheck;
import com.javaacademy.burger.Steakhouse;
import com.javaacademy.burger.Waitress;
import com.javaacademy.burger.dish.Dish;
import com.javaacademy.burger.dish.DishType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;

@Slf4j
public class SteakhouseTest {

    private Waitress waitress;
    private Kitchen kitchen;
    private PayTerminal payTerminal;
    private Steakhouse steakhouse;

    @Test
    @DisplayName("Ситуация №1: Клиент покупает бургер за рубли")
    public void makeOrder() {
        Kitchen kitchen = new Kitchen();
        Waitress waitress = new Waitress();
        PayTerminal payTerminal = new PayTerminal();
        Steakhouse steakhouse = new Steakhouse(waitress, kitchen, payTerminal);

        Paycheck paycheck = steakhouse.makeOrder(DishType.BURGER, Currency.RUB);
        Assertions.assertNotNull(paycheck);
        Assertions.assertEquals(DishType.BURGER, paycheck.getDishType());

        Dish dish = steakhouse.takeOrder(paycheck);

        Assertions.assertNotNull(dish);
        Assertions.assertEquals(DishType.BURGER, dish.getDishType());
    }

    @Test
    @DisplayName("Проверка от санэпидемстанции")
    public void makeOrderBySes() {

        Kitchen kitchen = new Kitchen();
        Waitress waitress = new Waitress();
        PayTerminal payTerminal = Mockito.spy(PayTerminal.class);
        Steakhouse steakhouse = new Steakhouse(waitress, kitchen, payTerminal);

        Mockito.when(payTerminal.pay(DishType.RIBS, Currency.RUB))
                .thenReturn(new Paycheck(new BigDecimal("700"), Currency.RUB, DishType.RIBS));

        Paycheck paycheck = steakhouse.makeOrder(DishType.RIBS, Currency.RUB);
        Dish dish = steakhouse.takeOrder(paycheck);

        Assertions.assertNotNull(payTerminal);
        Assertions.assertEquals(DishType.RIBS, dish.getDishType());
        Assertions.assertEquals(new BigDecimal("700"), paycheck.getTotalAmount());
        Assertions.assertEquals(Currency.RUB, paycheck.getCurrency());
    }

    @BeforeEach
    void setUp() {
        waitress = Mockito.mock(Waitress.class);
        kitchen = Mockito.mock(Kitchen.class);
        payTerminal = Mockito.spy(PayTerminal.class);
        steakhouse = new Steakhouse(waitress, kitchen, payTerminal);

        Mockito.when(waitress.giveOrderToKitchen(Mockito.any(), Mockito.any())).thenReturn(true);
    }

    @Test
    @DisplayName("Ситуация 1: клиент заказал ребра за рубли")
    public void makeOrderByTaxRibs() {

        Mockito.when(waitress.giveOrderToKitchen(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.doReturn(new Paycheck(new BigDecimal("700"), Currency.RUB, DishType.RIBS))
                .when(payTerminal).pay(DishType.RIBS, Currency.RUB);

        Paycheck paycheck = steakhouse.makeOrder(DishType.RIBS, Currency.RUB);
        Assertions.assertNotNull(paycheck);
        Assertions.assertEquals(new BigDecimal("700"), paycheck.getTotalAmount());
        Assertions.assertEquals(Currency.RUB, paycheck.getCurrency());
        Assertions.assertEquals(DishType.RIBS, paycheck.getDishType());
    }

    @Test
    @DisplayName("Ситуация 2: клиент захотел купить картошку за доллары")
    public void makeOrderByTaxPotato() {

        Mockito.when(waitress.giveOrderToKitchen(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.doReturn(new Paycheck(new BigDecimal("1"), Currency.USD, DishType.FRIED_POTATO))
               .when(payTerminal).pay(DishType.FRIED_POTATO, Currency.USD);

        Paycheck paycheck = steakhouse.makeOrder(DishType.FRIED_POTATO, Currency.USD);
        Assertions.assertNotNull(paycheck);
        Assertions.assertEquals(new BigDecimal("1"), paycheck.getTotalAmount());
        Assertions.assertEquals(DishType.FRIED_POTATO, paycheck.getDishType());
        Assertions.assertEquals(Currency.USD, paycheck.getCurrency());
    }

    @Test
    @DisplayName("Ситуация 3: клиент захотел купить бургер за мозамбикский доллар")
    public void makeOrderByTaxMozambicansDollar() {

        Mockito.when(waitress.giveOrderToKitchen(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.doReturn(new Paycheck(new BigDecimal("1"), Currency.MOZAMBICAN_DOLLARS, DishType.BURGER))
                .when(payTerminal).pay(DishType.BURGER, Currency.MOZAMBICAN_DOLLARS);

        Paycheck paycheck = steakhouse.makeOrder(DishType.BURGER, Currency.MOZAMBICAN_DOLLARS);
        Assertions.assertNotNull(paycheck);
        Assertions.assertEquals(new BigDecimal("1"), paycheck.getTotalAmount());
        Assertions.assertEquals(DishType.BURGER, paycheck.getDishType());
        Assertions.assertEquals(Currency.MOZAMBICAN_DOLLARS, paycheck.getCurrency());
    }
}
