package com.javaacademy.burger.unit;

import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.Waitress;
import com.javaacademy.burger.dish.DishType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WaitressTest {

    @Test
    @DisplayName("Ситуация №1: Успешное принятие заказа")
    public void giveOrderToKitchenSuccess() {
        Kitchen kitchen = Mockito.mock(Kitchen.class);
        Waitress waitress = new Waitress();

        boolean result = waitress.giveOrderToKitchen(DishType.BURGER, kitchen);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Ситуация №2: Успешное не принятие заказа")
    public void giveOrderToKitchenFailure() {
        Kitchen kitchen = Mockito.mock(Kitchen.class);
        Waitress waitress = new Waitress();

        boolean result = waitress.giveOrderToKitchen(DishType.FUAGRA, kitchen);
        Assertions.assertFalse(result);
    }


}
