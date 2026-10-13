# language: es
@HU-01 @cupones
Característica: Cupones de descuento
  Como cliente de La Botica del Alquimista
  quiero aplicar un cupón de descuento a mi carrito
  para pagar menos cuando cumplo las condiciones del cupón

  Antecedentes:
    Dado que Laura inició sesión en La Botica del Alquimista

  @REQ-13 @REQ-15 @Issue-17 @TC-17 @positivo @smoketest
  Esquema del escenario: El cupón LUNA10 descuenta el 10% del subtotal sin importar mayúsculas
    Dado que Laura agrega 2 unidades de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Cuando Laura aplica el cupón "<codigo>"
    Entonces Laura debería ver el mensaje del cupón "Cupón LUNA10 aplicado correctamente"
    Y Laura debería ver el cupón aplicado "LUNA10"
    Y Laura debería ver el subtotal del carrito "$70.000"
    Y Laura debería ver el descuento "-$7.000"
    Y Laura debería ver el total parcial "$63.000"

    Ejemplos:
      | codigo |
      | LUNA10 |
      | luna10 |

  @REQ-14 @REQ-15 @Issue-18 @TC-18 @positivo
  Escenario: El cupón ELIXIR20 descuenta el 20% cuando el subtotal llega a $100.000
    Dado que Laura agrega 1 unidad de "Poción de Invisibilidad" al carrito
    Y Laura agrega 1 unidad de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Cuando Laura aplica el cupón "Elixir20"
    Entonces Laura debería ver el mensaje del cupón "Cupón ELIXIR20 aplicado correctamente"
    Y Laura debería ver el subtotal del carrito "$100.000"
    Y Laura debería ver el descuento "-$20.000"
    Y Laura debería ver el total parcial "$80.000"

  @REQ-14 @Issue-19 @TC-19 @negativo
  Escenario: Rechazar ELIXIR20 cuando el subtotal no llega a $100.000
    Dado que Laura agrega 1 unidad de "Poción de Invisibilidad" al carrito
    Y Laura agrega 1 unidad de "Anillo de la Suerte" al carrito
    Y Laura abre su carrito
    Cuando Laura aplica el cupón "ELIXIR20"
    Entonces Laura debería ver el mensaje del cupón "Este cupón requiere una compra mínima de $100.000"
    Y Laura no debería tener ningún cupón aplicado
    Y Laura debería ver el total parcial "$92.500"

  @REQ-16 @Issue-20 @TC-20 @negativo
  Escenario: Rechazar un código de cupón inexistente
    Dado que Laura agrega 1 unidad de "Polvo de Luna" al carrito
    Y Laura abre su carrito
    Cuando Laura aplica el cupón "SOL50"
    Entonces Laura debería ver el mensaje del cupón "Cupón no válido"
    Y Laura no debería tener ningún cupón aplicado
    Y Laura debería ver el total parcial "$8.500"

  @REQ-17 @Issue-21 @TC-21 @negativo
  Escenario: Solo se permite un cupón por compra
    Dado que Laura agrega 3 unidades de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Y Laura aplica el cupón "LUNA10"
    Cuando Laura aplica el cupón "ELIXIR20"
    Entonces Laura debería ver el mensaje del cupón "Solo se permite un cupón por compra"
    Y Laura debería ver el cupón aplicado "LUNA10"
    Y Laura debería ver el descuento "-$10.500"

  @REQ-18 @Issue-22 @TC-22 @positivo
  Escenario: Quitar el cupón aplicado elimina el descuento
    Dado que Laura agrega 2 unidades de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Y Laura aplica el cupón "LUNA10"
    Cuando Laura quita el cupón aplicado
    Entonces Laura debería ver el mensaje del cupón "Se quitó el cupón"
    Y Laura no debería tener ningún cupón aplicado
    Y Laura debería ver el total parcial "$70.000"

  @REQ-19 @Issue-23 @TC-23 @negativo
  Escenario: ELIXIR20 se retira automáticamente si el subtotal baja de $100.000
    Dado que Laura agrega 3 unidades de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Y Laura aplica el cupón "ELIXIR20"
    Y Laura debería ver el descuento "-$21.000"
    Cuando Laura cambia la cantidad de "Amuleto de Protección" a 2 en el carrito
    Entonces Laura debería ver el mensaje del carrito "El cupón ELIXIR20 fue retirado porque tu compra ya no alcanza el mínimo de $100.000."
    Y Laura no debería tener ningún cupón aplicado
    Y Laura debería ver el total parcial "$70.000"
