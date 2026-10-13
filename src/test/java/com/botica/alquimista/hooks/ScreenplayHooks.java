package com.botica.alquimista.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Prepara la escena antes de cada escenario.
 * OnlineCast le da a cada actor la habilidad BrowseTheWeb con su propio navegador,
 * gestionado por Serenity (se abre y se cierra solo, uno nuevo por escenario).
 */
public class ScreenplayHooks {

    @Before(order = 0)
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    /** Permite escribir en Gherkin "Laura agrega..." o "ella agrega..." y recibir un Actor. */
    @ParameterType("[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+|él|ella")
    public Actor actor(String actorName) {
        return OnStage.theActorCalled(actorName);
    }
}
