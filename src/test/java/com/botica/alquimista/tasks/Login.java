package com.botica.alquimista.tasks;

import com.botica.alquimista.config.CustomerCredentials;
import com.botica.alquimista.interactions.EnterSecret;
import com.botica.alquimista.interactions.WaitForProcessing;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static com.botica.alquimista.ui.LoginPage.LOGIN_BUTTON;
import static com.botica.alquimista.ui.LoginPage.PASSWORD;
import static com.botica.alquimista.ui.LoginPage.USERNAME;

/**
 * Inicio de sesión en la botica.
 */
public final class Login {

    private Login() {
    }

    /** Usa las credenciales de prueba centralizadas en CustomerCredentials. */
    public static Performable asTheRegisteredCustomer() {
        return Task.where("{0} inicia sesión como cliente registrado",
                actor -> actor.attemptsTo(
                        withCredentials(CustomerCredentials.username(), CustomerCredentials.password())));
    }

    public static Performable withCredentials(String username, String password) {
        return Task.where("{0} inicia sesión con el usuario '" + username + "'",
                Enter.theValue(username).into(USERNAME),
                EnterSecret.into(PASSWORD, password),
                Click.on(LOGIN_BUTTON),
                WaitForProcessing.toFinish()
        );
    }
}
