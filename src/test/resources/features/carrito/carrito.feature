# language: es
@HU-01 @carrito
Característica: Carrito de compras
  Como cliente de La Botica del Alquimista
  quiero armar mi carrito respetando los límites de cantidad y de stock
  para comprar exactamente lo que necesito

  Antecedentes:
    Dado que Laura inició sesión en La Botica del Alquimista

  @REQ-06 @REQ-10 @Issue-9 @TC-09 @positivo @smoketest
  Escenario: Agregar un producto al carrito con una cantidad válida
    Cuando Laura agrega 3 unidades de "Polvo de Luna" al carrito
    Entonces Laura debería ver el mensaje del catálogo "Se agregó Polvo de Luna al carrito"
    Y el contador del carrito de Laura debería indicar 3 unidades
    Cuando Laura abre su carrito
    Entonces Laura debería ver en su carrito "Polvo de Luna" con precio "$8.500", cantidad 3 y subtotal "$25.500"

  @REQ-06 @Issue-10 @TC-10 @negativo
  Esquema del escenario: Rechazar cantidades fuera del rango de 1 a 5 al agregar
    Cuando Laura agrega <cantidad> unidades de "Raíz de Mandrágora" al carrito
    Entonces Laura debería ver el mensaje del catálogo "La cantidad debe estar entre 1 y 5"
    Y el contador del carrito de Laura debería indicar 0 unidades

    Ejemplos:
      | cantidad |
      | 0        |
      | 6        |

  @REQ-07 @Issue-11 @TC-11 @negativo
  Escenario: Un producto no puede superar 5 unidades aunque se agregue varias veces
    Cuando Laura agrega 3 unidades de "Raíz de Mandrágora" al carrito
    Y Laura agrega 2 unidades de "Raíz de Mandrágora" al carrito
    Y Laura agrega 1 unidad de "Raíz de Mandrágora" al carrito
    Entonces Laura debería ver el mensaje del catálogo "Solo puedes llevar hasta 5 unidades de este producto"
    Y el contador del carrito de Laura debería indicar 5 unidades
    Cuando Laura abre su carrito
    Entonces Laura debería tener 5 unidades de "Raíz de Mandrágora" en el carrito

  @REQ-08 @Issue-12 @TC-12 @negativo
  Escenario: No se puede agregar desde el catálogo más unidades que el stock disponible
    Cuando Laura agrega 4 unidades de "Elixir de Vitalidad" al carrito
    Entonces Laura debería ver el mensaje del catálogo "No hay suficiente stock disponible"
    Y el contador del carrito de Laura debería indicar 0 unidades

  @REQ-08 @Issue-13 @TC-13 @negativo @defecto
  Escenario: No se puede aumentar en el carrito la cantidad por encima del stock disponible
    Dado que Laura agrega 2 unidades de "Elixir de Vitalidad" al carrito
    Y Laura abre su carrito
    Cuando Laura cambia la cantidad de "Elixir de Vitalidad" a 4 en el carrito
    Entonces Laura debería ver el mensaje del carrito "No hay suficiente stock disponible"
    Y Laura debería tener 2 unidades de "Elixir de Vitalidad" en el carrito

  @REQ-10 @Issue-14 @TC-14 @positivo
  Escenario: Cada línea muestra su subtotal y el carrito suma todas las líneas
    Cuando Laura agrega 2 unidades de "Poción de Sueño Profundo" al carrito
    Y Laura agrega 1 unidad de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Entonces Laura debería ver en su carrito "Poción de Sueño Profundo" con precio "$18.500", cantidad 2 y subtotal "$37.000"
    Y Laura debería ver en su carrito "Amuleto de Protección" con precio "$35.000", cantidad 1 y subtotal "$35.000"
    Y Laura debería ver el subtotal del carrito "$72.000"

  @REQ-11 @Issue-15 @TC-15 @positivo
  Escenario: Eliminar un producto del carrito recalcula el total
    Dado que Laura agrega 2 unidades de "Poción de Sueño Profundo" al carrito
    Y Laura agrega 1 unidad de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Cuando Laura elimina "Amuleto de Protección" del carrito
    Entonces Laura debería ver el mensaje del carrito "Se eliminó Amuleto de Protección del carrito"
    Y Laura no debería ver "Amuleto de Protección" en su carrito
    Y Laura debería ver 1 línea en su carrito
    Y Laura debería ver el subtotal del carrito "$37.000"

  @REQ-12 @Issue-16 @TC-16 @negativo @smoketest
  Escenario: Con el carrito vacío no se puede continuar al pago
    Cuando Laura abre su carrito
    Entonces Laura debería ver el aviso "Tu carrito está vacío"
    Y Laura no debería poder continuar al pago
    Cuando Laura intenta abrir directamente la página "checkout.html"
    Entonces Laura debería permanecer en el carrito
