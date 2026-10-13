package com.botica.alquimista.tasks;

import com.botica.alquimista.interactions.WaitForProcessing;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.botica.alquimista.ui.CartPage.APPLY_COUPON_BUTTON;
import static com.botica.alquimista.ui.CartPage.COUPON_FIELD;
import static com.botica.alquimista.ui.CartPage.REMOVE_COUPON_BUTTON;

/**
 * Aplicar y quitar cupones de descuento en el carrito.
 */
public final class Coupon {

    private Coupon() {
    }

    public static Performable apply(String code) {
        return Task.where("{0} aplica el cupón '" + code + "'",
                Enter.theValue(code).into(COUPON_FIELD),
                Click.on(APPLY_COUPON_BUTTON),
                // La botica "consulta el grimorio de cupones" durante 0,4 a 1 s
                WaitForProcessing.toFinish()
        );
    }

    public static Performable remove() {
        return Task.where("{0} quita el cupón aplicado", Click.on(REMOVE_COUPON_BUTTON));
    }
}
