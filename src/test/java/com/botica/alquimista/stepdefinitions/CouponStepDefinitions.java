package com.botica.alquimista.stepdefinitions;

import com.botica.alquimista.questions.TheMessage;
import com.botica.alquimista.tasks.Coupon;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;

import static com.botica.alquimista.ui.CartPage.ACTIVE_COUPON_BLOCK;
import static com.botica.alquimista.ui.CartPage.APPLIED_COUPON;
import static com.botica.alquimista.ui.CartPage.COUPON_MESSAGE;
import static com.botica.alquimista.ui.CartPage.DISCOUNT;
import static com.botica.alquimista.ui.CartPage.DISCOUNT_ROW;
import static com.botica.alquimista.ui.CartPage.PARTIAL_TOTAL;

/**
 * Pasos de cupones de descuento.
 */
public class CouponStepDefinitions {

    @Cuando("{actor} aplica el cupón {string}")
    public void appliesCoupon(Actor actor, String code) {
        actor.attemptsTo(Coupon.apply(code));
    }

    @Cuando("{actor} quita el cupón aplicado")
    public void removesCoupon(Actor actor) {
        actor.attemptsTo(Coupon.remove());
    }

    @Entonces("{actor} debería ver el mensaje del cupón {string}")
    public void shouldSeeCouponMessage(Actor actor, String message) {
        actor.attemptsTo(Ensure.that(TheMessage.shownIn(COUPON_MESSAGE)).isEqualTo(message));
    }

    @Entonces("{actor} debería ver el cupón aplicado {string}")
    public void shouldSeeAppliedCoupon(Actor actor, String code) {
        actor.attemptsTo(Ensure.that(APPLIED_COUPON).text().isEqualTo(code));
    }

    @Entonces("{actor} debería ver el descuento {string}")
    public void shouldSeeDiscount(Actor actor, String discount) {
        actor.attemptsTo(Ensure.that(DISCOUNT).text().isEqualTo(discount));
    }

    @Entonces("{actor} debería ver el total parcial {string}")
    public void shouldSeePartialTotal(Actor actor, String total) {
        actor.attemptsTo(Ensure.that(PARTIAL_TOTAL).text().isEqualTo(total));
    }

    @Entonces("{actor} no debería tener ningún cupón aplicado")
    public void shouldHaveNoCoupon(Actor actor) {
        actor.attemptsTo(
                Ensure.that(ACTIVE_COUPON_BLOCK).isNotDisplayed(),
                Ensure.that(DISCOUNT_ROW).isNotDisplayed()
        );
    }
}
