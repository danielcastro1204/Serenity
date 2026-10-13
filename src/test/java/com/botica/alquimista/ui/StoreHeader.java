package com.botica.alquimista.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Encabezado común a las páginas autenticadas (lo pinta js/comun.js).
 */
public class StoreHeader {

    public static final Target GREETING = Target.the("saludo al usuario")
            .locatedBy("css:header.barra .saludo");

    public static final Target CART_LINK = Target.the("enlace al carrito")
            .locatedBy("css:#enlace-carrito");

    public static final Target CART_COUNTER = Target.the("contador de unidades del carrito")
            .locatedBy("css:[data-testid='contador-carrito']");

    public static final Target CATALOG_LINK = Target.the("enlace al catálogo")
            .locatedBy("//nav[@aria-label='Menú principal']/a[normalize-space()='Catálogo']");

    private StoreHeader() {
    }
}
