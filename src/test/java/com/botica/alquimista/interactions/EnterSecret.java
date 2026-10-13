package com.botica.alquimista.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Escribe un valor sensible (contraseña) sin que aparezca en el reporte de Serenity.
 * Enter.theValue(...) publica el texto en la narrativa del escenario; esta interacción
 * solo informa en qué campo se escribió.
 */
public class EnterSecret implements Interaction {

    private String secret;
    private Target target;

    public EnterSecret() {
    }

    public EnterSecret(String secret, Target target) {
        this.secret = secret;
        this.target = target;
    }

    public static EnterSecret into(Target target, String secret) {
        return instrumented(EnterSecret.class, secret, target);
    }

    @Override
    @Step("{0} escribe un valor protegido en el #target")
    public <T extends Actor> void performAs(T actor) {
        WebElementFacade field = target.resolveFor(actor);
        field.waitUntilEnabled();
        field.clear();
        field.sendKeys(secret);
    }
}
