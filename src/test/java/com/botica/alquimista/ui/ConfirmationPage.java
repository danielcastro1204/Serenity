package com.botica.alquimista.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Confirmación del pedido (confirmacion.html).
 */
public class ConfirmationPage {

    public static final Target TITLE = Target.the("título de la confirmación")
            .locatedBy("css:section.tarjeta-confirmacion h1");

    public static final Target ORDER_NUMBER = Target.the("número de pedido")
            .locatedBy("css:[data-testid='numero-pedido']");

    public static final Target SUMMARY_LINES = Target.the("líneas del resumen de la compra")
            .locatedBy("css:section.tarjeta-confirmacion ul.lista-resumen li");

    public static final Target TOTAL_PAID = Target.the("total pagado")
            .locatedBy("css:#conf-total");

    private ConfirmationPage() {
    }
}
