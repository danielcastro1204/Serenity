package com.botica.alquimista.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Página de inicio de sesión (index.html).
 */
public class LoginPage {

    public static final Target USERNAME = Target.the("campo usuario")
            .locatedBy("css:#usuario");

    // El campo contraseña no tiene id: se ubica por su atributo name
    public static final Target PASSWORD = Target.the("campo contraseña")
            .locatedBy("css:input[name='clave']");

    public static final Target LOGIN_BUTTON = Target.the("botón Ingresar")
            .locatedBy("css:#form-login button[type='submit']");

    public static final Target ERROR_MESSAGE = Target.the("mensaje de error de acceso")
            .locatedBy("css:[data-testid='error-login']");

    private LoginPage() {
    }
}
