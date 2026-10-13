package com.botica.alquimista.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

import static com.botica.alquimista.ui.CatalogPage.CATEGORY_FILTER;
import static com.botica.alquimista.ui.CatalogPage.SEARCH_BOX;

/**
 * Búsqueda y filtrado del catálogo.
 */
public final class BrowseCatalog {

    private BrowseCatalog() {
    }

    /** La búsqueda filtra en cada pulsación (evento input), no requiere botón. */
    public static Performable searchingFor(String text) {
        return Task.where("{0} busca '" + text + "' en el catálogo",
                Enter.theValue(text).into(SEARCH_BOX));
    }

    public static Performable filteringByCategory(String category) {
        return Task.where("{0} filtra el catálogo por la categoría " + category,
                SelectFromOptions.byVisibleText(category).from(CATEGORY_FILTER));
    }
}
