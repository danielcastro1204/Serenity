# language: es
@HU-01 @pago
Característica: Pago y confirmación del pedido
  Como cliente de La Botica del Alquimista
  quiero diligenciar mis datos de envío, elegir el método de envío y confirmar el pedido
  para recibir mis artículos pagando el valor correcto

  Antecedentes:
    Dado que Laura inició sesión en La Botica del Alquimista

  @REQ-20 @Issue-24 @TC-24 @negativo
  Escenario: Todos los datos de envío son obligatorios
    Dado que Laura agrega 1 unidad de "Polvo de Luna" al carrito
    Y Laura abre su carrito
    Y Laura continúa al pago
    Cuando Laura confirma el pedido
    Entonces Laura debería ver los siguientes errores en el formulario de pago:
      | campo     | mensaje                   |
      | nombre    | Este campo es obligatorio |
      | correo    | Este campo es obligatorio |
      | direccion | Este campo es obligatorio |
      | ciudad    | Este campo es obligatorio |
      | telefono  | Este campo es obligatorio |
    Y Laura debería ver el mensaje del formulario de pago "Revisa los campos marcados"
    Y Laura debería permanecer en la página de pago

  @REQ-21 @Issue-25 @TC-25 @negativo
  Esquema del escenario: Rechazar un correo con formato inválido
    Dado que Laura agrega 1 unidad de "Polvo de Luna" al carrito
    Y Laura abre su carrito
    Y Laura continúa al pago
    Cuando Laura diligencia los datos de envío con el correo "<correo>" y el teléfono "3001234567"
    Y Laura confirma el pedido
    Entonces Laura debería ver el error "Ingresa un correo válido" en el campo "correo"
    Y Laura debería permanecer en la página de pago

    Ejemplos:
      | correo                 |
      | nicolas.flamel         |
      | nicolas@botica         |
      | nicolas flamel@test.co |

  @REQ-22 @Issue-26 @TC-26 @negativo
  Esquema del escenario: Rechazar un teléfono que no tiene exactamente 10 dígitos
    Dado que Laura agrega 1 unidad de "Polvo de Luna" al carrito
    Y Laura abre su carrito
    Y Laura continúa al pago
    Cuando Laura diligencia los datos de envío con el correo "nicolas.flamel@botica.test" y el teléfono "<telefono>"
    Y Laura confirma el pedido
    Entonces Laura debería ver el error "El teléfono debe tener 10 dígitos" en el campo "telefono"
    Y Laura debería permanecer en la página de pago

    Ejemplos:
      | telefono    |
      | 300123456   |
      | 30012345678 |
      | 300ABC4567  |

  @REQ-23 @REQ-24 @REQ-25 @Issue-27 @TC-27 @positivo
  Escenario: Envío normal de $8.000 cuando la compra es menor a $150.000
    Dado que Laura agrega 2 unidades de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Cuando Laura continúa al pago
    Y Laura elige el envío "normal"
    Entonces Laura debería ver en el resumen de pago:
      | concepto | valor   |
      | Subtotal | $70.000 |
      | IVA      | $13.300 |
      | Envío    | $8.000  |
      | Total    | $91.300 |

  @REQ-23 @Issue-28 @TC-28 @positivo
  Escenario: Envío normal gratis cuando la compra con descuento llega a $150.000
    Dado que Laura agrega 5 unidades de "Poción de Sueño Profundo" al carrito
    Y Laura agrega 2 unidades de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Cuando Laura continúa al pago
    Y Laura elige el envío "normal"
    Entonces Laura debería ver en el resumen de pago:
      | concepto | valor    |
      | Subtotal | $162.500 |
      | IVA      | $30.875  |
      | Envío    | Gratis   |
      | Total    | $193.375 |

  @REQ-23 @Issue-29 @TC-29 @negativo
  Escenario: El envío normal se cobra si el descuento deja la compra por debajo de $150.000
    Dado que Laura agrega 5 unidades de "Poción de Sueño Profundo" al carrito
    Y Laura agrega 2 unidades de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Y Laura aplica el cupón "LUNA10"
    Cuando Laura continúa al pago
    Y Laura elige el envío "normal"
    Entonces Laura debería ver en el resumen de pago:
      | concepto  | valor     |
      | Subtotal  | $162.500  |
      | Descuento | -$16.250  |
      | Envío     | $8.000    |

  @REQ-23 @Issue-30 @TC-30 @positivo
  Escenario: El envío express cuesta $15.000 sin importar el valor de la compra
    Dado que Laura agrega 5 unidades de "Poción de Sueño Profundo" al carrito
    Y Laura agrega 2 unidades de "Amuleto de Protección" al carrito
    Y Laura abre su carrito
    Cuando Laura continúa al pago
    Y Laura elige el envío "express"
    Entonces Laura debería ver en el resumen de pago:
      | concepto | valor    |
      | Subtotal | $162.500 |
      | Envío    | $15.000  |
      | Total    | $208.375 |

  @REQ-24 @REQ-25 @Issue-31 @TC-31 @positivo @defecto
  Escenario: El IVA se calcula sobre el subtotal después del descuento y se redondea al peso
    Dado que Laura agrega 1 unidad de "Poción de Sueño Profundo" al carrito
    Y Laura abre su carrito
    Y Laura aplica el cupón "LUNA10"
    Cuando Laura continúa al pago
    Y Laura elige el envío "normal"
    Entonces Laura debería ver en el resumen de pago:
      | concepto  | valor   |
      | Subtotal  | $18.500 |
      | Descuento | -$1.850 |
      | IVA       | $3.164  |
      | Envío     | $8.000  |
      | Total     | $27.814 |

  @REQ-26 @Issue-32 @TC-32 @positivo @smoketest
  Escenario: Confirmar un pedido válido vacía el carrito y descuenta el stock
    Dado que Laura consulta el stock disponible de "Raíz de Mandrágora"
    Y Laura agrega 2 unidades de "Raíz de Mandrágora" al carrito
    Y Laura abre su carrito
    Y Laura continúa al pago
    Cuando Laura diligencia datos de envío válidos
    Y Laura elige el envío "normal"
    Y Laura confirma el pedido
    Entonces Laura debería ver un número de pedido con formato BOT-XXXX
    Y Laura debería ver en el resumen de la compra la línea "2 × Raíz de Mandrágora $24.000"
    Y Laura debería ver el total pagado "$36.560"
    Y el contador del carrito de Laura debería indicar 0 unidades
    Cuando Laura vuelve al catálogo
    Entonces el stock de "Raíz de Mandrágora" debería haber bajado 2 unidades
