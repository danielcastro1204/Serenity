package com.botica.alquimista.config;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;

/**
 * Único punto donde se obtienen las credenciales de prueba del cliente.
 * Orden de búsqueda:
 *   1. Variables de entorno BOTICA_USERNAME / BOTICA_PASSWORD (local o secretos de GitHub Actions).
 *   2. Propiedades botica.username / botica.password de serenity.conf (datos de práctica de HU-01).
 * La contraseña nunca se escribe en el reporte ni en los logs.
 */
public final class CustomerCredentials {

    private CustomerCredentials() {
    }

    public static String username() {
        return valueOf("BOTICA_USERNAME", "botica.username");
    }

    public static String password() {
        return valueOf("BOTICA_PASSWORD", "botica.password");
    }

    private static String valueOf(String environmentVariable, String serenityProperty) {
        String fromEnvironment = System.getenv(environmentVariable);
        if (fromEnvironment != null && !fromEnvironment.isBlank()) {
            return fromEnvironment;
        }
        return EnvironmentSpecificConfiguration
                .from(SystemEnvironmentVariables.currentEnvironmentVariables())
                .getOptionalProperty(serenityProperty)
                .orElseThrow(() -> new IllegalStateException(
                        "No hay credenciales de prueba: defina " + environmentVariable
                                + " o la propiedad " + serenityProperty + " en serenity.conf"));
    }
}
