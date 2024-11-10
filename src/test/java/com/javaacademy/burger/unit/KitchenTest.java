package com.javaacademy.burger.unit;

import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.dish.Dish;
import com.javaacademy.burger.dish.DishType;
import com.javaacademy.burger.exception.KitchenHasNoGasException;
import com.javaacademy.burger.exception.UnsupportedDishTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Queue;

public class KitchenTest {

    @Test
    @DisplayName("Ситуация №1: Успешное приготовление бургера")
    public void cookSuccess() {
        Kitchen kitchen = new Kitchen();

        kitchen.cook(DishType.BURGER);
        Queue<Dish> burgers = kitchen.getCompletedDishes().get(DishType.BURGER);
        Assertions.assertNotNull(burgers);
        Assertions.assertFalse(burgers.isEmpty());
    }

    @Test
    @DisplayName("Ситуация №2: Проверка ошибки KitchenHasNoGasException")
    public void kitchenHasNoGasException() {
        Kitchen kitchen = new Kitchen();
        kitchen.setHasGas(false);
        Assertions.assertThrows(KitchenHasNoGasException.class, () -> kitchen.cook(DishType.BURGER));
    }

    @Test
    @DisplayName("Ситуация №3: Проверка ошибки UnsupportedDishTypeException")
    public void unsupportedDishTypeException() {
        Kitchen kitchen = new Kitchen();
        Assertions.assertThrows(UnsupportedDishTypeException.class, () -> kitchen.cook(DishType.FUAGRA));
    }
}
