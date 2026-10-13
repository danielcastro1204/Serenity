package com.botica.alquimista.interactions;

import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.botica.alquimista.ui.LoadingOverlay.OVERLAY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;

/**
 * Espera explícita a que desaparezca la capa "cargando" de la aplicación.
 * La botica simula demoras aleatorias (300 ms a 1,6 s) al validar el acceso,
 * consultar cupones y confirmar el pedido; esta interacción reemplaza cualquier Thread.sleep.
 */
public final class WaitForProcessing {

    private static final int MAX_SECONDS = 15;

    private WaitForProcessing() {
    }

    public static Interaction toFinish() {
        return Interaction.where("{0} espera a que la botica termine de procesar",
                WaitUntil.the(OVERLAY, isNotVisible()).forNoMoreThan(MAX_SECONDS).seconds());
    }
}
