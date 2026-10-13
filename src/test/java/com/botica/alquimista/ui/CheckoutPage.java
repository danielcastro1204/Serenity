package com.botica.alquimista.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Datos de envío y pago (checkout.html).
 * Varios ids de esta página parecen generados (inp-x92, t-771, c1), por eso se
 * prefieren los atributos name y data-testid, que describen el dato y son más estables.
 */
public class CheckoutPage {

    public static final Target TITLE = Target.the("título de la página de pago")
            .locatedBy("css:main.contenido h1");

    public static final Target FULL_NAME = Target.the("campo nombre completo")
            .locatedBy("css:#nombreCompleto");

    public static final Target EMAIL = Target.the("campo correo electrónico")
            .locatedBy("css:input[name='correo']");

    public static final Target ADDRESS = Target.the("campo dirección")
            .locatedBy("css:[data-testid='direccion']");

    public static final Target CITY = Target.the("campo ciudad")
            .locatedBy("css:input[name='ciudad']");

    public static final Target PHONE = Target.the("campo teléfono")
            .locatedBy("css:input[name='telefono']");

    // {0} = nombre | correo | direccion | ciudad | telefono
    public static final Target FIELD_ERROR = Target.the("error del campo {0}")
            .locatedBy("css:span.error-campo[data-campo='{0}']");

    // {0} = normal | express
    public static final Target SHIPPING_OPTION = Target.the("opción de envío {0}")
            .locatedBy("css:input[name='envio'][value='{0}']");

    public static final Target FORM_MESSAGE = Target.the("mensaje del formulario de pago")
            .locatedBy("css:[data-testid='mensaje-checkout']");

    public static final Target CONFIRM_BUTTON = Target.the("botón Confirmar pedido")
            .locatedBy("css:#btn-confirmar");

    // Resumen del pedido
    public static final Target SUMMARY_SUBTOTAL = Target.the("subtotal del resumen")
            .locatedBy("css:#resumen-subtotal");

    public static final Target SUMMARY_DISCOUNT = Target.the("descuento del resumen")
            .locatedBy("css:[data-testid='resumen-descuento']");

    public static final Target SUMMARY_VAT = Target.the("IVA del resumen")
            .locatedBy("css:dd.valor-iva");

    public static final Target SUMMARY_SHIPPING = Target.the("envío del resumen")
            .locatedBy("css:[data-testid='resumen-envio']");

    public static final Target SUMMARY_TOTAL = Target.the("total a pagar")
            .locatedBy("css:#total-pagar");

    private CheckoutPage() {
    }
}
