package com.botica.alquimista.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Catálogo de productos (catalogo.html).
 * Los targets con {0} reciben el nombre del producto, por ejemplo ADD_BUTTON_OF.of("Polvo de Luna").
 */
public class CatalogPage {

    public static final Target TITLE = Target.the("título del catálogo")
            .locatedBy("css:main.contenido h1");

    public static final Target SEARCH_BOX = Target.the("campo buscar por nombre")
            .locatedBy("css:#buscar");

    public static final Target CATEGORY_FILTER = Target.the("filtro de categoría")
            .locatedBy("css:select[name='categoria']");

    public static final Target MESSAGE = Target.the("mensaje del catálogo")
            .locatedBy("css:[data-testid='mensaje-catalogo']");

    public static final Target NO_RESULTS = Target.the("mensaje sin resultados")
            .locatedBy("css:p.sin-resultados");

    // Colecciones: una entrada por tarjeta visible
    public static final Target PRODUCT_CARDS = Target.the("tarjetas de producto")
            .locatedBy("css:article.tarjeta-producto");

    public static final Target PRODUCT_NAMES = Target.the("nombres de los productos")
            .locatedBy("css:article.tarjeta-producto .nombre-producto");

    public static final Target PRODUCT_CATEGORIES = Target.the("categorías de los productos")
            .locatedBy("css:article.tarjeta-producto .categoria-producto");

    // Elementos de la tarjeta de un producto concreto
    public static final Target QUANTITY_OF = Target.the("cantidad a agregar de {0}")
            .locatedBy("css:input.cantidad[aria-label='Cantidad de {0}']");

    public static final Target ADD_BUTTON_OF = Target.the("botón agregar de {0}")
            .locatedBy("//article[.//h3[normalize-space()='{0}']]//button[contains(@class,'btn-agregar')]");

    public static final Target STOCK_OF = Target.the("stock disponible de {0}")
            .locatedBy("//article[.//h3[normalize-space()='{0}']]//p[@class='stock']/span");

    private CatalogPage() {
    }
}
