package com.botica.alquimista.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Texto de un mensaje que la aplicación muestra al usuario (error, aviso o éxito).
 * Los mensajes de la botica existen ocultos en el DOM (atributo hidden) y se hacen
 * visibles con el texto final, por eso primero se espera la visibilidad.
 */
public final class TheMessage {

    private TheMessage() {
    }

    public static Question<String> shownIn(Target message) {
        return Question.about("el texto de " + message.getName())
                .answeredBy(actor -> {
                    var element = message.resolveFor(actor);
                    element.waitUntilVisible();
                    return element.getText().trim();
                });
    }
}
