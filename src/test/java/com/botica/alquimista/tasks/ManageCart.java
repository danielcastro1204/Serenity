package com.botica.alquimista.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.botica.alquimista.ui.CartPage.QUANTITY_OF;
import static com.botica.alquimista.ui.CartPage.REMOVE_BUTTON_OF;
import static com.botica.alquimista.ui.CartPage.UPDATE_BUTTON_OF;

/**
 * Operaciones sobre las líneas del carrito.
 */
public final class ManageCart {

    private ManageCart() {
    }

    public static Performable changeQuantity(String product, int quantity) {
        return Task.where("{0} cambia la cantidad de " + product + " a " + quantity,
                Enter.theValue(String.valueOf(quantity)).into(QUANTITY_OF.of(product)),
                Click.on(UPDATE_BUTTON_OF.of(product))
        );
    }

    public static Performable remove(String product) {
        return Task.where("{0} elimina " + product + " del carrito",
                Click.on(REMOVE_BUTTON_OF.of(product)));
    }
}
