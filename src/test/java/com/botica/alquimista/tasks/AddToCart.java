package com.botica.alquimista.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.botica.alquimista.ui.CatalogPage.ADD_BUTTON_OF;
import static com.botica.alquimista.ui.CatalogPage.QUANTITY_OF;

/**
 * Agrega un producto al carrito desde su tarjeta del catálogo.
 */
public final class AddToCart {

    private AddToCart() {
    }

    public static Performable theProduct(String product, int quantity) {
        return Task.where("{0} agrega " + quantity + " unidad(es) de " + product + " al carrito",
                Enter.theValue(String.valueOf(quantity)).into(QUANTITY_OF.of(product)),
                Click.on(ADD_BUTTON_OF.of(product))
        );
    }
}
