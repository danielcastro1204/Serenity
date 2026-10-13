package com.botica.alquimista.stepdefinitions;

import com.botica.alquimista.models.CatalogProduct;
import com.botica.alquimista.questions.TheCatalog;
import com.botica.alquimista.questions.TheMessage;
import com.botica.alquimista.tasks.BrowseCatalog;
import io.cucumber.java.DataTableType;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.botica.alquimista.ui.CatalogPage.ADD_BUTTON_OF;
import static com.botica.alquimista.ui.CatalogPage.NO_RESULTS;
import static com.botica.alquimista.ui.CatalogPage.QUANTITY_OF;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Pasos del catálogo: datos de productos, filtro, búsqueda y productos agotados.
 */
public class CatalogStepDefinitions {

    @DataTableType
    public CatalogProduct catalogProduct(Map<String, String> row) {
        return new CatalogProduct(row.get("nombre"), row.get("categoria"), row.get("precio"), row.get("stock"));
    }

    @Cuando("{actor} filtra el catálogo por la categoría {string}")
    public void filtersByCategory(Actor actor, String category) {
        actor.attemptsTo(BrowseCatalog.filteringByCategory(category));
    }

    @Cuando("{actor} busca {string} en el catálogo")
    public void searches(Actor actor, String text) {
        actor.attemptsTo(BrowseCatalog.searchingFor(text));
    }

    @Entonces("{actor} debería ver los siguientes productos en el catálogo:")
    public void shouldSeeProducts(Actor actor, List<CatalogProduct> expected) {
        actor.should(seeThat(TheCatalog.products(), equalTo(expected)));
    }

    @Entonces("{actor} debería ver solo productos de la categoría {string}")
    public void shouldSeeOnlyCategory(Actor actor, String category) {
        actor.should(seeThat(TheCatalog.productCategories(), everyItem(equalTo(category))));
    }

    @Entonces("{actor} debería ver {int} productos en el catálogo")
    public void shouldSeeNumberOfProducts(Actor actor, int count) {
        actor.should(seeThat(TheCatalog.productNames(), hasSize(count)));
    }

    /** resultado = nombres separados por coma, en el orden del catálogo */
    @Entonces("{actor} debería ver únicamente los productos {string}")
    public void shouldSeeOnlyProducts(Actor actor, String names) {
        String[] expected = Arrays.stream(names.split(",")).map(String::trim).toArray(String[]::new);
        actor.should(seeThat(TheCatalog.productNames(), contains(expected)));
    }

    @Entonces("{actor} no debería ver productos en el catálogo")
    public void shouldSeeNoProducts(Actor actor) {
        actor.should(seeThat(TheCatalog.productNames(), empty()));
    }

    @Entonces("{actor} debería ver el aviso de búsqueda {string}")
    public void shouldSeeNoResultsNotice(Actor actor, String notice) {
        actor.attemptsTo(Ensure.that(TheMessage.shownIn(NO_RESULTS)).isEqualTo(notice));
    }

    @Entonces("el producto {string} debería mostrar {int} unidades disponibles")
    public void productShouldShowStock(String product, int stock) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheCatalog.stockOf(product)).isEqualTo(stock));
    }

    @Entonces("{actor} debería ver el producto {string} marcado como {string}")
    public void shouldSeeProductLabel(Actor actor, String product, String label) {
        actor.attemptsTo(Ensure.that(ADD_BUTTON_OF.of(product)).text().isEqualTo(label));
    }

    @Entonces("{actor} no debería poder agregar {string} al carrito")
    public void shouldNotBeAbleToAdd(Actor actor, String product) {
        actor.attemptsTo(
                Ensure.that(ADD_BUTTON_OF.of(product)).isDisabled(),
                Ensure.that(QUANTITY_OF.of(product)).isDisabled()
        );
    }
}
