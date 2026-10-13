package com.botica.alquimista.questions;

import com.botica.alquimista.models.CatalogProduct;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import org.openqa.selenium.By;

import java.util.List;

import static com.botica.alquimista.ui.CatalogPage.PRODUCT_CARDS;
import static com.botica.alquimista.ui.CatalogPage.PRODUCT_CATEGORIES;
import static com.botica.alquimista.ui.CatalogPage.PRODUCT_NAMES;
import static com.botica.alquimista.ui.CatalogPage.STOCK_OF;

/**
 * Consultas sobre lo que el catálogo muestra en pantalla.
 */
public final class TheCatalog {

    private TheCatalog() {
    }

    /** Nombre, categoría, precio y stock de cada tarjeta visible, en orden de aparición. */
    public static Question<List<CatalogProduct>> products() {
        return Question.about("los productos que muestra el catálogo")
                .answeredBy(actor -> PRODUCT_CARDS.resolveAllFor(actor).stream()
                        .map(TheCatalog::toProduct)
                        .toList());
    }

    public static Question<List<String>> productNames() {
        return Question.about("los nombres de los productos visibles")
                .answeredBy(actor -> List.copyOf(Text.ofEach(PRODUCT_NAMES).answeredBy(actor)));
    }

    public static Question<List<String>> productCategories() {
        return Question.about("las categorías de los productos visibles")
                .answeredBy(actor -> List.copyOf(Text.ofEach(PRODUCT_CATEGORIES).answeredBy(actor)));
    }

    public static Question<Integer> stockOf(String product) {
        return Question.about("el stock disponible de " + product)
                .answeredBy(actor -> Integer.parseInt(Text.of(STOCK_OF.of(product)).answeredBy(actor).trim()));
    }

    private static CatalogProduct toProduct(WebElementFacade card) {
        return new CatalogProduct(
                card.find(By.cssSelector(".nombre-producto")).getText().trim(),
                card.find(By.cssSelector(".categoria-producto")).getText().trim(),
                card.find(By.cssSelector(".precio")).getText().trim(),
                card.find(By.cssSelector(".stock span")).getText().trim());
    }
}
