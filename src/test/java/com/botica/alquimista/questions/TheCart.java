package com.botica.alquimista.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.Value;

import static com.botica.alquimista.ui.CartPage.CART_LINES;
import static com.botica.alquimista.ui.CartPage.QUANTITY_OF;
import static com.botica.alquimista.ui.StoreHeader.CART_COUNTER;

/**
 * Consultas sobre el contenido del carrito.
 */
public final class TheCart {

    private TheCart() {
    }

    /** Cantidad que muestra el campo numérico de la línea del producto. */
    public static Question<Integer> quantityOf(String product) {
        return Question.about("la cantidad de " + product + " en el carrito")
                .answeredBy(actor -> Integer.parseInt(Value.of(QUANTITY_OF.of(product)).answeredBy(actor).trim()));
    }

    public static Question<Integer> numberOfLines() {
        return Question.about("el número de líneas del carrito")
                .answeredBy(actor -> CART_LINES.resolveAllFor(actor).size());
    }

    /** Unidades totales que indica el contador del encabezado. */
    public static Question<Integer> unitsInTheCounter() {
        return Question.about("las unidades del contador del carrito")
                .answeredBy(actor -> Integer.parseInt(Text.of(CART_COUNTER).answeredBy(actor).trim()));
    }
}
