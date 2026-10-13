package com.botica.alquimista.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

import java.util.List;
import java.util.Map;

import static com.botica.alquimista.ui.CheckoutPage.SUMMARY_DISCOUNT;
import static com.botica.alquimista.ui.CheckoutPage.SUMMARY_SHIPPING;
import static com.botica.alquimista.ui.CheckoutPage.SUMMARY_SUBTOTAL;
import static com.botica.alquimista.ui.CheckoutPage.SUMMARY_TOTAL;
import static com.botica.alquimista.ui.CheckoutPage.SUMMARY_VAT;
import static com.botica.alquimista.ui.ConfirmationPage.SUMMARY_LINES;

/**
 * Valores del resumen del pedido en la página de pago y de la confirmación.
 */
public final class TheOrderSummary {

    private static final Map<String, Target> CONCEPTS = Map.of(
            "Subtotal", SUMMARY_SUBTOTAL,
            "Descuento", SUMMARY_DISCOUNT,
            "IVA", SUMMARY_VAT,
            "Envío", SUMMARY_SHIPPING,
            "Total", SUMMARY_TOTAL);

    private TheOrderSummary() {
    }

    /** concept = Subtotal | Descuento | IVA | Envío | Total */
    public static Question<String> valueOf(String concept) {
        Target target = CONCEPTS.get(concept);
        if (target == null) {
            throw new IllegalArgumentException("Concepto de resumen desconocido: " + concept
                    + ". Use uno de " + CONCEPTS.keySet());
        }
        return Question.about("el valor de " + concept + " en el resumen de pago")
                .answeredBy(actor -> Text.of(target).answeredBy(actor).trim());
    }

    /** Líneas "cantidad × producto  valor" de la página de confirmación. */
    public static Question<List<String>> confirmedLines() {
        return Question.about("las líneas del resumen de la compra confirmada")
                .answeredBy(actor -> SUMMARY_LINES.resolveAllFor(actor).stream()
                        .map(line -> line.getText().replaceAll("\\s+", " ").trim())
                        .toList());
    }
}
