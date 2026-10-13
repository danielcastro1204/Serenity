package com.botica.alquimista.stepdefinitions;

import com.botica.alquimista.models.ShippingDetails;
import com.botica.alquimista.questions.TheCatalog;
import com.botica.alquimista.questions.TheMessage;
import com.botica.alquimista.questions.TheOrderSummary;
import com.botica.alquimista.tasks.Checkout;
import com.botica.alquimista.tasks.Navigate;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.util.List;
import java.util.Map;

import static com.botica.alquimista.ui.CheckoutPage.FIELD_ERROR;
import static com.botica.alquimista.ui.CheckoutPage.FORM_MESSAGE;
import static com.botica.alquimista.ui.CheckoutPage.TITLE;
import static com.botica.alquimista.ui.ConfirmationPage.ORDER_NUMBER;
import static com.botica.alquimista.ui.ConfirmationPage.TOTAL_PAID;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.hasItem;

/**
 * Pasos de pago: formulario de envío, métodos de envío, resumen y confirmación del pedido.
 */
public class CheckoutStepDefinitions {

    private static final String INITIAL_STOCK = "stock inicial de ";

    @Dado("que {actor} consulta el stock disponible de {string}")
    public void notesStock(Actor actor, String product) {
        actor.remember(INITIAL_STOCK + product, actor.asksFor(TheCatalog.stockOf(product)));
    }

    @Cuando("{actor} continúa al pago")
    public void proceedsToCheckout(Actor actor) {
        actor.attemptsTo(
                Checkout.proceedFromTheCart(),
                Ensure.that(TITLE).text().isEqualTo("Datos de envío y pago")
        );
    }

    @Cuando("{actor} diligencia los datos de envío con el correo {string} y el teléfono {string}")
    public void fillsShippingWith(Actor actor, String email, String phone) {
        actor.attemptsTo(Checkout.fillShippingDetails(ShippingDetails.valid().withEmail(email).withPhone(phone)));
    }

    @Cuando("{actor} diligencia datos de envío válidos")
    public void fillsValidShipping(Actor actor) {
        actor.attemptsTo(Checkout.fillShippingDetails(ShippingDetails.valid()));
    }

    @Cuando("{actor} elige el envío {string}")
    public void choosesShipping(Actor actor, String method) {
        actor.attemptsTo(Checkout.chooseShipping(method));
    }

    @Cuando("{actor} confirma el pedido")
    public void confirmsOrder(Actor actor) {
        actor.attemptsTo(Checkout.confirmOrder());
    }

    @Cuando("{actor} vuelve al catálogo")
    public void returnsToCatalog(Actor actor) {
        actor.attemptsTo(Navigate.toTheCatalog());
    }

    @Entonces("{actor} debería ver los siguientes errores en el formulario de pago:")
    public void shouldSeeFieldErrors(Actor actor, List<Map<String, String>> errors) {
        errors.forEach(error -> actor.attemptsTo(
                Ensure.that(FIELD_ERROR.of(error.get("campo"))).text().isEqualTo(error.get("mensaje"))));
    }

    @Entonces("{actor} debería ver el error {string} en el campo {string}")
    public void shouldSeeFieldError(Actor actor, String message, String field) {
        actor.attemptsTo(Ensure.that(FIELD_ERROR.of(field)).text().isEqualTo(message));
    }

    @Entonces("{actor} debería ver el mensaje del formulario de pago {string}")
    public void shouldSeeFormMessage(Actor actor, String message) {
        actor.attemptsTo(Ensure.that(TheMessage.shownIn(FORM_MESSAGE)).isEqualTo(message));
    }

    @Entonces("{actor} debería permanecer en la página de pago")
    public void shouldRemainOnCheckout(Actor actor) {
        actor.attemptsTo(
                Ensure.that(TITLE).text().isEqualTo("Datos de envío y pago"),
                Ensure.thatTheCurrentPage().currentUrl().endsWith("/checkout.html")
        );
    }

    /** concepto = Subtotal | Descuento | IVA | Envío | Total */
    @Entonces("{actor} debería ver en el resumen de pago:")
    public void shouldSeeSummary(Actor actor, List<Map<String, String>> rows) {
        rows.forEach(row -> actor.attemptsTo(
                Ensure.that(TheOrderSummary.valueOf(row.get("concepto"))).isEqualTo(row.get("valor"))));
    }

    @Entonces("{actor} debería ver un número de pedido con formato BOT-XXXX")
    public void shouldSeeOrderNumber(Actor actor) {
        actor.attemptsTo(Ensure.that(TheMessage.shownIn(ORDER_NUMBER)).matches("^BOT-\\d{4}$"));
    }

    @Entonces("{actor} debería ver en el resumen de la compra la línea {string}")
    public void shouldSeeConfirmedLine(Actor actor, String line) {
        actor.should(seeThat(TheOrderSummary.confirmedLines(), hasItem(line)));
    }

    @Entonces("{actor} debería ver el total pagado {string}")
    public void shouldSeeTotalPaid(Actor actor, String total) {
        actor.attemptsTo(Ensure.that(TOTAL_PAID).text().isEqualTo(total));
    }

    @Entonces("el stock de {string} debería haber bajado {int} unidades")
    public void stockShouldDecrease(String product, int units) {
        Actor actor = OnStage.theActorInTheSpotlight();
        int initialStock = actor.recall(INITIAL_STOCK + product);
        actor.attemptsTo(Ensure.that(TheCatalog.stockOf(product)).isEqualTo(initialStock - units));
    }
}
