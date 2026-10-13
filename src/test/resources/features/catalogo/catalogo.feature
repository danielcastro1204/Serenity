# language: es
@HU-01 @catalogo
Característica: Catálogo de productos
  Como cliente de La Botica del Alquimista
  quiero consultar, filtrar y buscar los productos del catálogo
  para encontrar los artículos de alquimia que necesito

  Antecedentes:
    Dado que Laura inició sesión en La Botica del Alquimista

  @REQ-03 @Issue-4 @TC-04 @positivo @smoketest
  Escenario: El catálogo muestra nombre, categoría, precio y stock de cada producto
    Entonces Laura debería ver los siguientes productos en el catálogo:
      | nombre                   | categoria    | precio  | stock |
      | Poción de Sueño Profundo | Pociones     | $18.500 | 10    |
      | Elixir de Vitalidad      | Pociones     | $42.000 | 3     |
      | Poción de Invisibilidad  | Pociones     | $65.000 | 2     |
      | Raíz de Mandrágora       | Ingredientes | $12.000 | 20    |
      | Polvo de Luna            | Ingredientes | $8.500  | 15    |
      | Pluma de Fénix           | Ingredientes | $95.000 | 1     |
      | Amuleto de Protección    | Amuletos     | $35.000 | 6     |
      | Anillo de la Suerte      | Amuletos     | $27.500 | 4     |
      | Colgante Lunar           | Amuletos     | $51.000 | 0     |

  @REQ-04 @Issue-5 @TC-05 @positivo
  Esquema del escenario: Filtrar el catálogo por categoría
    Cuando Laura filtra el catálogo por la categoría "<categoria>"
    Entonces Laura debería ver solo productos de la categoría "<categoria>"
    Y Laura debería ver <cantidad> productos en el catálogo

    Ejemplos:
      | categoria    | cantidad |
      | Pociones     | 3        |
      | Ingredientes | 3        |
      | Amuletos     | 3        |

  @REQ-05 @Issue-6 @TC-06 @positivo
  Esquema del escenario: Buscar por nombre con coincidencia parcial sin distinguir mayúsculas
    Cuando Laura busca "<texto>" en el catálogo
    Entonces Laura debería ver únicamente los productos "<resultado>"

    Ejemplos:
      | texto   | resultado                                         |
      | poción  | Poción de Sueño Profundo, Poción de Invisibilidad |
      | LUNA    | Polvo de Luna, Colgante Lunar                     |
      | fÉnIx   | Pluma de Fénix                                    |
      | de la s | Anillo de la Suerte                               |

  @REQ-05 @Issue-7 @TC-07 @negativo
  Escenario: Buscar un producto que no existe en el catálogo
    Cuando Laura busca "piedra filosofal" en el catálogo
    Entonces Laura no debería ver productos en el catálogo
    Y Laura debería ver el aviso de búsqueda "No se encontraron productos."

  @REQ-09 @Issue-8 @TC-08 @negativo
  Escenario: Un producto sin stock aparece como agotado y no se puede agregar
    Entonces el producto "Colgante Lunar" debería mostrar 0 unidades disponibles
    Y Laura debería ver el producto "Colgante Lunar" marcado como "Agotado"
    Y Laura no debería poder agregar "Colgante Lunar" al carrito
