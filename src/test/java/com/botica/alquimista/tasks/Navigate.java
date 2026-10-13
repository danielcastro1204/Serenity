package com.botica.alquimista.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;

import static com.botica.alquimista.ui.StoreHeader.CART_LINK;
import static com.botica.alquimista.ui.StoreHeader.CATALOG_LINK;

/**
 * Navegación entre las páginas de la botica.
 * Las URL son relativas a webdriver.base.url (serenity.conf).
 */
public final class Navigate {

    private Navigate() {
    }

    public static Performable toTheStore() {
        return Task.where("{0} abre La Botica del Alquimista",
                Open.relativeUrl("/index.html"));
    }

    /** Escribe la dirección de una página en el navegador, sin usar los menús. */
    public static Performable directlyTo(String page) {
        return Task.where("{0} escribe en el navegador la dirección de la página " + page,
                Open.relativeUrl("/" + page));
    }

    public static Performable toTheCart() {
        return Task.where("{0} abre su carrito", Click.on(CART_LINK));
    }

    public static Performable toTheCatalog() {
        return Task.where("{0} vuelve al catálogo", Click.on(CATALOG_LINK));
    }
}
