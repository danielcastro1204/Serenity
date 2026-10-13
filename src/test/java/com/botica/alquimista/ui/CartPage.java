package com.botica.alquimista.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Carrito de compras (carrito.html).
 */
public class CartPage {

    public static final Target TITLE = Target.the("título del carrito")
            .locatedBy("css:main.contenido h1");

    public static final Target MESSAGE = Target.the("mensaje del carrito")
            .locatedBy("css:[data-testid='mensaje-carrito']");

    public static final Target EMPTY_CART = Target.the("aviso de carrito vacío")
            .locatedBy("css:[data-testid='carrito-vacio']");

    public static final Target CART_LINES = Target.the("líneas del carrito")
            .locatedBy("css:tr.linea-carrito");

    // Línea concreta. El nombre va precedido de un ícono, por eso se usa contains()
    public static final Target LINE_OF = Target.the("línea del carrito de {0}")
            .locatedBy("//tr[contains(@class,'linea-carrito')][td[contains(normalize-space(.),'{0}')]]");

    public static final Target UNIT_PRICE_OF = Target.the("precio unitario de {0}")
            .locatedBy("//tr[contains(@class,'linea-carrito')][td[contains(normalize-space(.),'{0}')]]/td[contains(@class,'precio-unitario')]");

    public static final Target LINE_SUBTOTAL_OF = Target.the("subtotal de la línea de {0}")
            .locatedBy("//tr[contains(@class,'linea-carrito')][td[contains(normalize-space(.),'{0}')]]/td[contains(@class,'subtotal-linea')]");

    public static final Target QUANTITY_OF = Target.the("cantidad en el carrito de {0}")
            .locatedBy("css:input.cantidad-carrito[aria-label='Cantidad de {0}']");

    public static final Target UPDATE_BUTTON_OF = Target.the("botón actualizar de {0}")
            .locatedBy("//tr[contains(@class,'linea-carrito')][td[contains(normalize-space(.),'{0}')]]//button[contains(@class,'btn-actualizar')]");

    public static final Target REMOVE_BUTTON_OF = Target.the("botón eliminar de {0}")
            .locatedBy("css:button.btn-eliminar[aria-label='Eliminar {0}']");

    // Cupones
    public static final Target COUPON_FIELD = Target.the("campo cupón de descuento")
            .locatedBy("css:input[name='cupon']");

    public static final Target APPLY_COUPON_BUTTON = Target.the("botón aplicar cupón")
            .locatedBy("css:#btn-aplicar-cupon");

    public static final Target COUPON_MESSAGE = Target.the("mensaje del cupón")
            .locatedBy("css:#mensaje-cupon");

    public static final Target ACTIVE_COUPON_BLOCK = Target.the("bloque de cupón activo")
            .locatedBy("css:p.cupon-activo");

    public static final Target APPLIED_COUPON = Target.the("cupón aplicado")
            .locatedBy("css:[data-testid='cupon-aplicado']");

    public static final Target REMOVE_COUPON_BUTTON = Target.the("botón quitar cupón")
            .locatedBy("css:[data-testid='quitar-cupon']");

    // Totales
    public static final Target SUBTOTAL = Target.the("subtotal del carrito")
            .locatedBy("css:#subtotal");

    public static final Target DISCOUNT_ROW = Target.the("fila de descuento del carrito")
            .locatedBy("css:dl.totales .fila-descuento");

    public static final Target DISCOUNT = Target.the("descuento del carrito")
            .locatedBy("css:[data-testid='descuento']");

    public static final Target PARTIAL_TOTAL = Target.the("total parcial del carrito")
            .locatedBy("css:dd.total-parcial");

    public static final Target CHECKOUT_BUTTON = Target.the("botón Ir a pagar")
            .locatedBy("css:button.btn-pagar");

    private CartPage() {
    }
}
