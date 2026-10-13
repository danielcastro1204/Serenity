# Trazabilidad · HU-01 → criterios → casos → escenarios → Issues

Repositorio: https://github.com/danielcastro1204/Serenity · Cada escenario lleva los tags `@HU-01`, `@REQ-xx`, `@Issue-N` y `@TC-xx`.

## 1. Criterios de aceptación de HU-01

| Requisito | Criterio | Casos que lo cubren | Estado |
|---|---|---|---|
| REQ-01 | Acceso 1 - Para comprar hay que iniciar sesión con usuario y contraseña válidos | TC-01 ([#1](https://github.com/danielcastro1204/Serenity/issues/1)), TC-03 ([#3](https://github.com/danielcastro1204/Serenity/issues/3)) | Cubierto |
| REQ-02 | Acceso 2 - Credenciales incorrectas muestran "Usuario o contraseña incorrectos" | TC-02 ([#2](https://github.com/danielcastro1204/Serenity/issues/2)) | Cubierto |
| REQ-03 | F1.1 - El catálogo muestra nombre, categoría, precio y stock | TC-04 ([#4](https://github.com/danielcastro1204/Serenity/issues/4)) | Cubierto |
| REQ-04 | F1.2 - Filtro por categoría (Pociones, Ingredientes, Amuletos) | TC-05 ([#5](https://github.com/danielcastro1204/Serenity/issues/5)) | Cubierto |
| REQ-05 | F1.3 - Búsqueda por nombre parcial sin distinguir mayúsculas | TC-06 ([#6](https://github.com/danielcastro1204/Serenity/issues/6)), TC-07 ([#7](https://github.com/danielcastro1204/Serenity/issues/7)) | Cubierto |
| REQ-06 | F1.4 - Cantidad al agregar entre 1 y 5 | TC-09 ([#9](https://github.com/danielcastro1204/Serenity/issues/9)), TC-10 ([#10](https://github.com/danielcastro1204/Serenity/issues/10)) | Cubierto |
| REQ-07 | F1.5 - Máximo 5 unidades por producto aunque se agregue varias veces | TC-11 ([#11](https://github.com/danielcastro1204/Serenity/issues/11)) | Cubierto |
| REQ-08 | F1.6 - La cantidad no supera el stock (catálogo y carrito): "No hay suficiente stock disponible" | TC-12 ([#12](https://github.com/danielcastro1204/Serenity/issues/12)), TC-13 ([#13](https://github.com/danielcastro1204/Serenity/issues/13)) | Cubierto · con defecto detectado |
| REQ-09 | F1.7 - Producto sin stock aparece "Agotado" y no se puede agregar | TC-08 ([#8](https://github.com/danielcastro1204/Serenity/issues/8)) | Cubierto |
| REQ-10 | F1.8 - Subtotal por línea (precio x cantidad) y suma del carrito | TC-09 ([#9](https://github.com/danielcastro1204/Serenity/issues/9)), TC-14 ([#14](https://github.com/danielcastro1204/Serenity/issues/14)) | Cubierto |
| REQ-11 | F1.9 - Eliminar un producto del carrito | TC-15 ([#15](https://github.com/danielcastro1204/Serenity/issues/15)) | Cubierto |
| REQ-12 | F1.10 - Carrito vacío: "Tu carrito está vacío" y no se puede ir al pago | TC-16 ([#16](https://github.com/danielcastro1204/Serenity/issues/16)) | Cubierto |
| REQ-13 | F2.1 - LUNA10 da 10% de descuento sobre el subtotal | TC-17 ([#17](https://github.com/danielcastro1204/Serenity/issues/17)) | Cubierto |
| REQ-14 | F2.2 - ELIXIR20 da 20% solo con subtotal >= $100.000; si no: "Este cupón requiere una compra mínima de $100.000" | TC-18 ([#18](https://github.com/danielcastro1204/Serenity/issues/18)), TC-19 ([#19](https://github.com/danielcastro1204/Serenity/issues/19)) | Cubierto |
| REQ-15 | F2.3 - Los códigos no distinguen mayúsculas de minúsculas | TC-17 ([#17](https://github.com/danielcastro1204/Serenity/issues/17)), TC-18 ([#18](https://github.com/danielcastro1204/Serenity/issues/18)) | Cubierto |
| REQ-16 | F2.4 - Código inexistente: "Cupón no válido" | TC-20 ([#20](https://github.com/danielcastro1204/Serenity/issues/20)) | Cubierto |
| REQ-17 | F2.5 - Un cupón por compra: "Solo se permite un cupón por compra" | TC-21 ([#21](https://github.com/danielcastro1204/Serenity/issues/21)) | Cubierto |
| REQ-18 | F2.6 - El cupón aplicado se puede quitar | TC-22 ([#22](https://github.com/danielcastro1204/Serenity/issues/22)) | Cubierto |
| REQ-19 | F2.7 - ELIXIR20 se retira automáticamente con aviso si el subtotal baja de $100.000 | TC-23 ([#23](https://github.com/danielcastro1204/Serenity/issues/23)) | Cubierto |
| REQ-20 | F3.1 - Campos obligatorios: nombre, correo, dirección, ciudad, teléfono | TC-24 ([#24](https://github.com/danielcastro1204/Serenity/issues/24)) | Cubierto |
| REQ-21 | F3.2 - El correo debe tener formato válido | TC-25 ([#25](https://github.com/danielcastro1204/Serenity/issues/25)) | Cubierto |
| REQ-22 | F3.3 - El teléfono debe tener exactamente 10 dígitos | TC-26 ([#26](https://github.com/danielcastro1204/Serenity/issues/26)) | Cubierto |
| REQ-23 | F3.4 - Envío normal $8.000 (gratis desde $150.000 con descuento) / express $15.000 | TC-27 ([#27](https://github.com/danielcastro1204/Serenity/issues/27)), TC-28 ([#28](https://github.com/danielcastro1204/Serenity/issues/28)), TC-29 ([#29](https://github.com/danielcastro1204/Serenity/issues/29)), TC-30 ([#30](https://github.com/danielcastro1204/Serenity/issues/30)) | Cubierto |
| REQ-24 | F3.5 - IVA 19% sobre el subtotal después del descuento; el envío no lleva IVA | TC-27 ([#27](https://github.com/danielcastro1204/Serenity/issues/27)), TC-31 ([#31](https://github.com/danielcastro1204/Serenity/issues/31)) | Cubierto · con defecto detectado |
| REQ-25 | F3.6 - Total = subtotal - descuento + IVA + envío, redondeado y con separador de miles | TC-27 ([#27](https://github.com/danielcastro1204/Serenity/issues/27)), TC-31 ([#31](https://github.com/danielcastro1204/Serenity/issues/31)) | Cubierto · con defecto detectado |
| REQ-26 | F3.7 - Confirmación: número BOT-XXXX, resumen, carrito vacío y stock descontado | TC-32 ([#32](https://github.com/danielcastro1204/Serenity/issues/32)) | Cubierto |

Los 26 criterios verificables de HU-01 están cubiertos por al menos un escenario. No quedan criterios sin automatizar.

## 2. Escenario → tags → Issue

| Issue | Caso | Escenario | Archivo `.feature` | Tags |
|---|---|---|---|---|
| [#1](https://github.com/danielcastro1204/Serenity/issues/1) | TC-01 | Iniciar sesión con credenciales válidas | `acceso/inicio_sesion.feature` | `@HU-01 @REQ-01 @Issue-1 @TC-01 @positivo @smoketest` |
| [#2](https://github.com/danielcastro1204/Serenity/issues/2) | TC-02 | Rechazar el acceso con credenciales incorrectas | `acceso/inicio_sesion.feature` | `@HU-01 @REQ-02 @Issue-2 @TC-02 @negativo @smoketest` |
| [#3](https://github.com/danielcastro1204/Serenity/issues/3) | TC-03 | Impedir el acceso al catálogo sin haber iniciado sesión | `acceso/inicio_sesion.feature` | `@HU-01 @REQ-01 @Issue-3 @TC-03 @negativo` |
| [#4](https://github.com/danielcastro1204/Serenity/issues/4) | TC-04 | El catálogo muestra nombre, categoría, precio y stock de cada producto | `catalogo/catalogo.feature` | `@HU-01 @REQ-03 @Issue-4 @TC-04 @positivo @smoketest` |
| [#5](https://github.com/danielcastro1204/Serenity/issues/5) | TC-05 | Filtrar el catálogo por categoría | `catalogo/catalogo.feature` | `@HU-01 @REQ-04 @Issue-5 @TC-05 @positivo` |
| [#6](https://github.com/danielcastro1204/Serenity/issues/6) | TC-06 | Buscar por nombre con coincidencia parcial sin distinguir mayúsculas | `catalogo/catalogo.feature` | `@HU-01 @REQ-05 @Issue-6 @TC-06 @positivo` |
| [#7](https://github.com/danielcastro1204/Serenity/issues/7) | TC-07 | Buscar un producto que no existe en el catálogo | `catalogo/catalogo.feature` | `@HU-01 @REQ-05 @Issue-7 @TC-07 @negativo` |
| [#8](https://github.com/danielcastro1204/Serenity/issues/8) | TC-08 | Un producto sin stock aparece como agotado y no se puede agregar | `catalogo/catalogo.feature` | `@HU-01 @REQ-09 @Issue-8 @TC-08 @negativo` |
| [#9](https://github.com/danielcastro1204/Serenity/issues/9) | TC-09 | Agregar un producto al carrito con una cantidad válida | `carrito/carrito.feature` | `@HU-01 @REQ-06 @REQ-10 @Issue-9 @TC-09 @positivo @smoketest` |
| [#10](https://github.com/danielcastro1204/Serenity/issues/10) | TC-10 | Rechazar cantidades fuera del rango de 1 a 5 al agregar | `carrito/carrito.feature` | `@HU-01 @REQ-06 @Issue-10 @TC-10 @negativo` |
| [#11](https://github.com/danielcastro1204/Serenity/issues/11) | TC-11 | Un producto no puede superar 5 unidades aunque se agregue varias veces | `carrito/carrito.feature` | `@HU-01 @REQ-07 @Issue-11 @TC-11 @negativo` |
| [#12](https://github.com/danielcastro1204/Serenity/issues/12) | TC-12 | No se puede agregar desde el catálogo más unidades que el stock disponible | `carrito/carrito.feature` | `@HU-01 @REQ-08 @Issue-12 @TC-12 @negativo` |
| [#13](https://github.com/danielcastro1204/Serenity/issues/13) | TC-13 | No se puede aumentar en el carrito la cantidad por encima del stock disponible | `carrito/carrito.feature` | `@HU-01 @REQ-08 @Issue-13 @TC-13 @negativo @defecto` |
| [#14](https://github.com/danielcastro1204/Serenity/issues/14) | TC-14 | Cada línea muestra su subtotal y el carrito suma todas las líneas | `carrito/carrito.feature` | `@HU-01 @REQ-10 @Issue-14 @TC-14 @positivo` |
| [#15](https://github.com/danielcastro1204/Serenity/issues/15) | TC-15 | Eliminar un producto del carrito recalcula el total | `carrito/carrito.feature` | `@HU-01 @REQ-11 @Issue-15 @TC-15 @positivo` |
| [#16](https://github.com/danielcastro1204/Serenity/issues/16) | TC-16 | Con el carrito vacío no se puede continuar al pago | `carrito/carrito.feature` | `@HU-01 @REQ-12 @Issue-16 @TC-16 @negativo @smoketest` |
| [#17](https://github.com/danielcastro1204/Serenity/issues/17) | TC-17 | El cupón LUNA10 descuenta el 10% del subtotal sin importar mayúsculas | `cupones/cupones.feature` | `@HU-01 @REQ-13 @REQ-15 @Issue-17 @TC-17 @positivo @smoketest` |
| [#18](https://github.com/danielcastro1204/Serenity/issues/18) | TC-18 | El cupón ELIXIR20 descuenta el 20% cuando el subtotal llega a $100.000 | `cupones/cupones.feature` | `@HU-01 @REQ-14 @REQ-15 @Issue-18 @TC-18 @positivo` |
| [#19](https://github.com/danielcastro1204/Serenity/issues/19) | TC-19 | Rechazar ELIXIR20 cuando el subtotal no llega a $100.000 | `cupones/cupones.feature` | `@HU-01 @REQ-14 @Issue-19 @TC-19 @negativo` |
| [#20](https://github.com/danielcastro1204/Serenity/issues/20) | TC-20 | Rechazar un código de cupón inexistente | `cupones/cupones.feature` | `@HU-01 @REQ-16 @Issue-20 @TC-20 @negativo` |
| [#21](https://github.com/danielcastro1204/Serenity/issues/21) | TC-21 | Solo se permite un cupón por compra | `cupones/cupones.feature` | `@HU-01 @REQ-17 @Issue-21 @TC-21 @negativo` |
| [#22](https://github.com/danielcastro1204/Serenity/issues/22) | TC-22 | Quitar el cupón aplicado elimina el descuento | `cupones/cupones.feature` | `@HU-01 @REQ-18 @Issue-22 @TC-22 @positivo` |
| [#23](https://github.com/danielcastro1204/Serenity/issues/23) | TC-23 | ELIXIR20 se retira automáticamente si el subtotal baja de $100.000 | `cupones/cupones.feature` | `@HU-01 @REQ-19 @Issue-23 @TC-23 @negativo` |
| [#24](https://github.com/danielcastro1204/Serenity/issues/24) | TC-24 | Todos los datos de envío son obligatorios | `pago/pago.feature` | `@HU-01 @REQ-20 @Issue-24 @TC-24 @negativo` |
| [#25](https://github.com/danielcastro1204/Serenity/issues/25) | TC-25 | Rechazar un correo con formato inválido | `pago/pago.feature` | `@HU-01 @REQ-21 @Issue-25 @TC-25 @negativo` |
| [#26](https://github.com/danielcastro1204/Serenity/issues/26) | TC-26 | Rechazar un teléfono que no tiene exactamente 10 dígitos | `pago/pago.feature` | `@HU-01 @REQ-22 @Issue-26 @TC-26 @negativo` |
| [#27](https://github.com/danielcastro1204/Serenity/issues/27) | TC-27 | Envío normal de $8.000 cuando la compra es menor a $150.000 | `pago/pago.feature` | `@HU-01 @REQ-23 @REQ-24 @REQ-25 @Issue-27 @TC-27 @positivo` |
| [#28](https://github.com/danielcastro1204/Serenity/issues/28) | TC-28 | Envío normal gratis cuando la compra con descuento llega a $150.000 | `pago/pago.feature` | `@HU-01 @REQ-23 @Issue-28 @TC-28 @positivo` |
| [#29](https://github.com/danielcastro1204/Serenity/issues/29) | TC-29 | El envío normal se cobra si el descuento deja la compra por debajo de $150.000 | `pago/pago.feature` | `@HU-01 @REQ-23 @Issue-29 @TC-29 @negativo` |
| [#30](https://github.com/danielcastro1204/Serenity/issues/30) | TC-30 | El envío express cuesta $15.000 sin importar el valor de la compra | `pago/pago.feature` | `@HU-01 @REQ-23 @Issue-30 @TC-30 @positivo` |
| [#31](https://github.com/danielcastro1204/Serenity/issues/31) | TC-31 | El IVA se calcula sobre el subtotal después del descuento y se redondea al peso | `pago/pago.feature` | `@HU-01 @REQ-24 @REQ-25 @Issue-31 @TC-31 @positivo @defecto` |
| [#32](https://github.com/danielcastro1204/Serenity/issues/32) | TC-32 | Confirmar un pedido válido vacía el carrito y descuenta el stock | `pago/pago.feature` | `@HU-01 @REQ-26 @Issue-32 @TC-32 @positivo @smoketest` |

## 3. Cómo se mantiene el vínculo

1. El tag `@Issue-N` del escenario es el número real del Issue en GitHub (creados #1 a #32 en orden, verificado al crearlos).
2. El pipeline (`.github/workflows/ci.yml`) genera `target/cucumber-reports/cucumber.json`.
3. El paso **Update linked GitHub Issues** ejecuta `.github/scripts/actualizar-issues.sh`, que lee los tags `@Issue-N` y `@TC-xx` de cada escenario ejecutado, comenta el resultado (caso, escenario, fecha y enlace a la ejecución) y pone la etiqueta `automated-passed` o `automated-failed`.
4. Para ejecutar un caso desde su Issue: *Actions → CI → Run workflow* con `testTag = @Issue-N`, o localmente `./gradlew clean test -Dcucumber.filter.tags="@Issue-N"`.
## 4. Project Board

Tablero: [TestCases-Serenity - HU-01 Botica del Alquimista](https://github.com/users/danielcastro1204/projects/3)
(proyecto #3 del usuario `danielcastro1204`, vinculado al repositorio `Serenity`).

- Contiene los 32 Issues de casos de prueba (#1 a #32).
- El campo **Status** tiene las columnas *Pendiente*, *En progreso*, *Automatizado* y *Verificado*.
- Regla: con `automated-passed` el Issue va a *Verificado*; con `automated-failed` o sin ejecutar, a *Automatizado*.
- Estado tras la ejecución completa [#38335396035](https://github.com/danielcastro1204/Serenity/actions/runs/38335396035):
  30 en *Verificado* y 2 en *Automatizado* (#13 y #31, defectos de la app).
- Para sincronizarlo de nuevo: `bash .github/scripts/sincronizar-project.sh 3`. Requiere `gh` con el permiso
  `project`. Se corre en local porque el `GITHUB_TOKEN` de Actions no puede escribir en proyectos de usuario.
