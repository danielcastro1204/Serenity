package com.botica.alquimista.tasks;

import com.botica.alquimista.interactions.WaitForProcessing;
import com.botica.alquimista.models.ShippingDetails;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.botica.alquimista.ui.CartPage.CHECKOUT_BUTTON;
import static com.botica.alquimista.ui.CheckoutPage.ADDRESS;
import static com.botica.alquimista.ui.CheckoutPage.CITY;
import static com.botica.alquimista.ui.CheckoutPage.CONFIRM_BUTTON;
import static com.botica.alquimista.ui.CheckoutPage.EMAIL;
import static com.botica.alquimista.ui.CheckoutPage.FULL_NAME;
import static com.botica.alquimista.ui.CheckoutPage.PHONE;
import static com.botica.alquimista.ui.CheckoutPage.SHIPPING_OPTION;

/**
 * Pasos del proceso de pago: ir al pago, diligenciar el envío, elegir método y confirmar.
 */
public final class Checkout {

    private Checkout() {
    }

    public static Performable proceedFromTheCart() {
        return Task.where("{0} continúa al pago", Click.on(CHECKOUT_BUTTON));
    }

    public static Performable fillShippingDetails(ShippingDetails details) {
        return Task.where("{0} diligencia los datos de envío (correo " + details.email()
                        + ", teléfono " + details.phone() + ")",
                Enter.theValue(details.fullName()).into(FULL_NAME),
                Enter.theValue(details.email()).into(EMAIL),
                Enter.theValue(details.address()).into(ADDRESS),
                Enter.theValue(details.city()).into(CITY),
                Enter.theValue(details.phone()).into(PHONE)
        );
    }

    /** method = normal | express */
    public static Performable chooseShipping(String method) {
        return Task.where("{0} elige el envío " + method,
                Click.on(SHIPPING_OPTION.of(method)));
    }

    public static Performable confirmOrder() {
        return Task.where("{0} confirma el pedido",
                Click.on(CONFIRM_BUTTON),
                WaitForProcessing.toFinish()
        );
    }
}
