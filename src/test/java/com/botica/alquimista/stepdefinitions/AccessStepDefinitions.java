package com.botica.alquimista.stepdefinitions;

import com.botica.alquimista.questions.TheMessage;
import com.botica.alquimista.tasks.Login;
import com.botica.alquimista.tasks.Navigate;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;

import static com.botica.alquimista.ui.CatalogPage.TITLE;
import static com.botica.alquimista.ui.LoginPage.ERROR_MESSAGE;
import static com.botica.alquimista.ui.LoginPage.LOGIN_BUTTON;
import static com.botica.alquimista.ui.StoreHeader.GREETING;

/**
 * Pasos de acceso (inicio de sesión). Solo traducen Gherkin a tareas y preguntas.
 */
public class AccessStepDefinitions {

    @Dado("que {actor} abre la tienda La Botica del Alquimista")
    public void opensTheStore(Actor actor) {
        actor.attemptsTo(Navigate.toTheStore());
    }

    @Dado("que {actor} inició sesión en La Botica del Alquimista")
    public void hasLoggedIn(Actor actor) {
        actor.attemptsTo(
                Navigate.toTheStore(),
                Login.asTheRegisteredCustomer(),
                Ensure.that(TITLE).text().isEqualTo("Catálogo")
        );
    }

    @Cuando("{actor} inicia sesión con sus credenciales de cliente")
    public void logsInAsCustomer(Actor actor) {
        actor.attemptsTo(Login.asTheRegisteredCustomer());
    }

    @Cuando("{actor} intenta iniciar sesión con el usuario {string} y la contraseña {string}")
    public void triesToLogIn(Actor actor, String username, String password) {
        actor.attemptsTo(Login.withCredentials(username, password));
    }

    @Cuando("{actor} intenta abrir directamente la página {string}")
    public void opensPageDirectly(Actor actor, String page) {
        actor.attemptsTo(Navigate.directlyTo(page));
    }

    @Entonces("{actor} debería ver el catálogo de productos")
    public void shouldSeeTheCatalog(Actor actor) {
        actor.attemptsTo(Ensure.that(TITLE).text().isEqualTo("Catálogo"));
    }

    @Entonces("{actor} debería ver el saludo {string}")
    public void shouldSeeTheGreeting(Actor actor, String greeting) {
        actor.attemptsTo(Ensure.that(GREETING).text().isEqualTo(greeting));
    }

    @Entonces("{actor} debería ver el mensaje de error de acceso {string}")
    public void shouldSeeLoginError(Actor actor, String message) {
        actor.attemptsTo(Ensure.that(TheMessage.shownIn(ERROR_MESSAGE)).isEqualTo(message));
    }

    @Entonces("{actor} debería permanecer en la página de inicio de sesión")
    public void shouldRemainOnLoginPage(Actor actor) {
        actor.attemptsTo(
                Ensure.that(LOGIN_BUTTON).isDisplayed(),
                Ensure.thatTheCurrentPage().title().isEqualTo("Ingresar | La Botica del Alquimista")
        );
    }
}
