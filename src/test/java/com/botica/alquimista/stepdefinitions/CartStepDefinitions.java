package com.botica.alquimista.stepdefinitions;

import com.botica.alquimista.questions.TheCart;
import com.botica.alquimista.questions.TheMessage;
import com.botica.alquimista.tasks.AddToCart;
import com.botica.alquimista.tasks.ManageCart;
import com.botica.alquimista.tasks.Navigate;
import com.botica.alquimista.ui.CartPage;
import com.botica.alquimista.ui.CatalogPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;

import static com.botica.alquimista.ui.CartPage.CHECKOUT_BUTTON;
import static com.botica.alquimista.ui.CartPage.EMPTY_CART;
import static com.botica.alquimista.ui.CartPage.LINE_OF;
import static com.botica.alquimista.ui.CartPage.LINE_SUBTOTAL_OF;
import static com.botica.alquimista.ui.CartPage.SUBTOTAL;
import static com.botica.alquimista.ui.CartPage.TITLE;
import static com.botica.alquimista.ui.CartPage.UNIT_PRICE_OF;

/**
 * Pasos del carrito: agregar, límites de cantidad y stock, subtotales, eliminar y carrito vacío.
 */
public class CartStepDefinitions {

    @Dado("que {actor} agrega {int} unidad(es) de {string} al carrito")
    @Cuando("{actor} agrega {int} unidad(es) de {string} al carrito")
    public void addsToCart(Actor actor, int quantity, String product) {
        actor.attemptsTo(AddToCart.theProduct(product, quantity));
    }

    @Cuando("{actor} abre su carrito")
    public void opensCart(Actor actor) {
        actor.attemptsTo(
                Navigate.toTheCart(),
                Ensure.that(TITLE).text().isEqualTo("Tu carrito")
        );
    }

    @Cuando("{actor} cambia la cantidad de {string} a {int} en el carrito")
    public void changesQuantity(Actor actor, String product, int quantity) {
        actor.attemptsTo(ManageCart.changeQuantity(product, quantity));
    }

    @Cuando("{actor} elimina {string} del carrito")
    public void removesProduct(Actor actor, String product) {
        actor.attemptsTo(ManageCart.remove(product));
    }

    @Entonces("{actor} debería ver el mensaje del catálogo {string}")
    public void shouldSeeCatalogMessage(Actor actor, String message) {
        actor.attemptsTo(Ensure.that(TheMessage.shownIn(CatalogPage.MESSAGE)).isEqualTo(message));
    }

    @Entonces("{actor} debería ver el mensaje del carrito {string}")
    public void shouldSeeCartMessage(Actor actor, String message) {
        actor.attemptsTo(Ensure.that(TheMessage.shownIn(CartPage.MESSAGE)).isEqualTo(message));
    }

    @Entonces("el contador del carrito de {actor} debería indicar {int} unidades")
    public void counterShouldShow(Actor actor, int units) {
        actor.attemptsTo(Ensure.that(TheCart.unitsInTheCounter()).isEqualTo(units));
    }

    @Entonces("{actor} debería ver en su carrito {string} con precio {string}, cantidad {int} y subtotal {string}")
    public void shouldSeeCartLine(Actor actor, String product, String price, int quantity, String subtotal) {
        actor.attemptsTo(
                Ensure.that(UNIT_PRICE_OF.of(product)).text().isEqualTo(price),
                Ensure.that(TheCart.quantityOf(product)).isEqualTo(quantity),
                Ensure.that(LINE_SUBTOTAL_OF.of(product)).text().isEqualTo(subtotal)
        );
    }

    @Entonces("{actor} debería tener {int} unidades de {string} en el carrito")
    public void shouldHaveQuantity(Actor actor, int quantity, String product) {
        actor.attemptsTo(Ensure.that(TheCart.quantityOf(product)).isEqualTo(quantity));
    }

    @Entonces("{actor} debería ver el subtotal del carrito {string}")
    public void shouldSeeCartSubtotal(Actor actor, String subtotal) {
        actor.attemptsTo(Ensure.that(SUBTOTAL).text().isEqualTo(subtotal));
    }

    @Entonces("{actor} no debería ver {string} en su carrito")
    public void shouldNotSeeProduct(Actor actor, String product) {
        actor.attemptsTo(Ensure.that(LINE_OF.of(product)).isNotDisplayed());
    }

    @Entonces("{actor} debería ver {int} línea(s) en su carrito")
    public void shouldSeeLines(Actor actor, int lines) {
        actor.attemptsTo(Ensure.that(TheCart.numberOfLines()).isEqualTo(lines));
    }

    @Entonces("{actor} debería ver el aviso {string}")
    public void shouldSeeEmptyCartNotice(Actor actor, String notice) {
        actor.attemptsTo(Ensure.that(TheMessage.shownIn(EMPTY_CART)).startsWith(notice));
    }

    @Entonces("{actor} no debería poder continuar al pago")
    public void shouldNotBeAbleToCheckout(Actor actor) {
        actor.attemptsTo(
                Ensure.that(CHECKOUT_BUTTON).isNotDisplayed(),
                Ensure.that(CHECKOUT_BUTTON).isDisabled()
        );
    }

    @Entonces("{actor} debería permanecer en el carrito")
    public void shouldRemainInCart(Actor actor) {
        actor.attemptsTo(
                Ensure.that(TITLE).text().isEqualTo("Tu carrito"),
                Ensure.thatTheCurrentPage().currentUrl().endsWith("/carrito.html")
        );
    }
}
