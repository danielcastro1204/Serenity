package com.botica.alquimista.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Capa "cargando" que la aplicación muestra mientras simula un proceso
 * (verificar credenciales, consultar cupones, preparar el pedido).
 */
public class LoadingOverlay {

    public static final Target OVERLAY = Target.the("capa de procesamiento")
            .locatedBy("css:#capa-cargando");

    private LoadingOverlay() {
    }
}
