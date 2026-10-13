# Automatización Front-End · La Botica del Alquimista (Serenity BDD + Screenplay)

Taller individual **"Automatización de Pruebas Front-End con Serenity BDD"**. Suite de pruebas automatizadas
que recorre la interfaz web real de **La Botica del Alquimista** (https://boticaalquimista.onrender.com) y
verifica la historia de usuario **HU-01: Compra en La Botica del Alquimista**.

> No se modifica la aplicación: el proyecto contiene solo pruebas (`src/test`).

---

## 1. Objetivo

Automatizar con Serenity BDD, Gherkin y el patrón Screenplay los criterios de aceptación de HU-01, con
escenarios positivos y negativos, trazabilidad a GitHub Issues, ejecución en GitHub Actions y reportes
de Serenity como documentación viva.

## 2. Aplicación seleccionada

**La Botica del Alquimista**: tienda de pociones, ingredientes y amuletos. Es una aplicación estática
(HTML + JavaScript) que guarda la sesión, el carrito, el cupón, el stock y los pedidos en el
`localStorage` del navegador. Esto tiene dos consecuencias para las pruebas:

- Cada escenario abre un **navegador nuevo** (`serenity.restart.browser.for.each = scenario`), así que
  siempre arranca con el inventario inicial de la HU, el carrito vacío y sin cupón.
- Las compras de un escenario (TC-32 descuenta stock) **no contaminan** a los demás ni a otras ejecuciones
  de CI. No hace falta ningún mecanismo de restauración de datos.

## 3. HU-01 y cobertura

Los 26 criterios verificables de HU-01 (acceso, catálogo y carrito, cupones, pago y pedido) se
identificaron como `REQ-01` … `REQ-26` y **todos** están cubiertos por al menos un escenario.
Detalle en [docs/trazabilidad.md](docs/trazabilidad.md) y [docs/matriz-pruebas.md](docs/matriz-pruebas.md).

| Funcionalidad | Archivo | Escenarios | Positivos | Negativos |
|---|---|---:|---:|---:|
| Acceso | `features/acceso/inicio_sesion.feature` | 3 | 1 | 2 |
| Catálogo | `features/catalogo/catalogo.feature` | 5 | 3 | 2 |
| Carrito | `features/carrito/carrito.feature` | 8 | 3 | 5 |
| Cupones | `features/cupones/cupones.feature` | 7 | 3 | 4 |
| Pago y pedido | `features/pago/pago.feature` | 9 | 5 | 4 |
| **Total** | | **32** | **15** | **17** |

32 declaraciones independientes (`Escenario` o `Esquema del escenario`); con los ejemplos de los 7 esquemas
son **45 ejecuciones**.

## 4. Tecnologías y versiones (verificadas en `build.gradle`)

| Herramienta | Versión |
|---|---|
| Java | 21 (toolchain de Gradle; probado con OpenJDK 21.0.12) |
| Gradle (wrapper) | 9.6.0 |
| Serenity BDD (core, cucumber, screenplay, screenplay-webdriver, ensure, plugin Gradle) | 5.3.9 |
| Cucumber (junit-platform-engine) | 7.34.2 |
| JUnit Platform (BOM) | 6.0.3 |
| Selenium (traído por Serenity) | 4.41.0 |
| Navegador | Google Chrome (headless), driver gestionado por Selenium Manager |

Se conservó **Gradle** porque el proyecto inicial ya lo usaba. El `build.gradle` original mezclaba
Serenity 4.1.14 / 4.2.32 / 4.3.1 y Selenium 4.15 / 4.38 en `implementation`; se alinearon todas las
dependencias a una sola versión de Serenity, en `testImplementation`, con JUnit 5 (JUnit 4 está obsoleto
desde Serenity 5).

## 5. Requisitos previos

- JDK 21 (`java -version`).
- Google Chrome instalado.
- Conexión a internet (la aplicación y las dependencias son remotas).
- No hace falta instalar Gradle: se usa el wrapper (`gradlew` / `gradlew.bat`).

## 6. Estructura

```text
.github/
├── workflows/ci.yml              → pipeline (basado en el ci.yml del profesor)
└── scripts/actualizar-issues.sh  → comenta resultados y etiqueta los Issues
docs/                             → matriz, trazabilidad y guía de sustentación
src/test/java/com/botica/alquimista/
├── runners/CucumberTestSuite.java   → suite JUnit 5 + Cucumber + reporter de Serenity
├── hooks/ScreenplayHooks.java       → OnlineCast (navegador por actor) y parámetro {actor}
├── stepdefinitions/                 → Access, Catalog, Cart, Coupon, Checkout
├── tasks/                           → Navigate, Login, BrowseCatalog, AddToCart, ManageCart, Coupon, Checkout
├── interactions/                    → WaitForProcessing, EnterSecret
├── questions/                       → TheMessage, TheCatalog, TheCart, TheOrderSummary
├── ui/                              → Targets: LoginPage, StoreHeader, CatalogPage, CartPage, CheckoutPage, ConfirmationPage, LoadingOverlay
├── models/                          → ShippingDetails, CatalogProduct
└── config/CustomerCredentials.java  → credenciales de prueba (variables de entorno o serenity.conf)
src/test/resources/
├── features/{acceso,catalogo,carrito,cupones,pago}/*.feature
└── serenity.conf                    → URL base, navegador, capturas, credenciales de práctica
```

## 7. Arquitectura Screenplay

```text
Feature (Gherkin) → Step Definition → Actor → Task / Interaction → Target
                                         └──→ Question → Ensure / seeThat
```

| Elemento | En este proyecto |
|---|---|
| **Actor** | "Laura", la clienta. El parámetro `{actor}` de `ScreenplayHooks` convierte el nombre del Gherkin en un `Actor` con `OnStage.theActorCalled`. |
| **Ability** | `BrowseTheWeb`, asignada automáticamente por `OnlineCast` en el `@Before`. Ningún step crea el driver. |
| **Tasks** | Metas de negocio con `Task.where`: `Login.asTheRegisteredCustomer()`, `AddToCart.theProduct(product, qty)`, `Coupon.apply(code)`, `Checkout.fillShippingDetails(details)`, `Checkout.confirmOrder()`… |
| **Interactions** | Las de Serenity (`Click`, `Enter`, `SelectFromOptions`, `Open.relativeUrl`) y dos propias: `WaitForProcessing.toFinish()` (espera explícita a que desaparezca la capa `#capa-cargando`) y `EnterSecret.into(target, secret)` (escribe la contraseña sin publicarla en el reporte). |
| **Questions** | `TheMessage.shownIn(target)` (espera a que el mensaje sea visible y devuelve su texto), `TheCatalog.products()`, `TheCart.quantityOf(product)`, `TheOrderSummary.valueOf("IVA")`… |
| **Targets** | Clases en `ui/`. Localizadores tomados del DOM real; prioridad: `id` → `data-testid` / `name` / `aria-label` → CSS → XPath relativo. Los CSS llevan el prefijo `css:` para que Serenity no los confunda con XPath. |

Las step definitions solo traducen: no contienen selectores ni esperas.

## 8. Escenarios

Positivos: inicio de sesión válido, datos del catálogo, filtro, búsqueda, agregar al carrito, subtotales,
eliminar, LUNA10 (y `luna10`), ELIXIR20 con el mínimo exacto de $100.000, quitar cupón, envío normal,
envío gratis, envío express, IVA con descuento y confirmación del pedido.

Negativos: credenciales incorrectas, acceso sin sesión, búsqueda sin resultados, producto agotado,
cantidad fuera de rango, más de 5 unidades, stock insuficiente (catálogo y carrito), carrito vacío,
ELIXIR20 bajo el mínimo, cupón inexistente, segundo cupón, retiro automático de ELIXIR20, campos
obligatorios, correo inválido, teléfono inválido y envío cobrado cuando el descuento baja la base de $150.000.

Lista completa con datos y resultados esperados: [docs/matriz-pruebas.md](docs/matriz-pruebas.md).

## 9. Configuración de credenciales

Las credenciales de prueba de HU-01 están **solo** en `CustomerCredentials.java`, que busca en este orden:

1. Variables de entorno `BOTICA_USERNAME` y `BOTICA_PASSWORD`.
2. `botica.username` / `botica.password` en `serenity.conf` (los datos de práctica que la propia HU y la
   página de login publican).

Para usar otras credenciales:

```bash
export BOTICA_USERNAME=alquimista
export BOTICA_PASSWORD=********
```

En PowerShell: `$env:BOTICA_USERNAME="alquimista"`. En GitHub Actions: *Settings → Secrets and variables →
Actions* → crear los secretos `BOTICA_USERNAME` y `BOTICA_PASSWORD` (opcional; el workflow ya los lee).
La contraseña nunca aparece en el reporte: se escribe con `EnterSecret`.

## 10. Ejecución

```bash
./gradlew clean test
```

En Windows: `gradlew.bat clean test`. El reporte se genera al final aunque haya fallos (`test` está
`finalizedBy 'aggregate'`). La tarea `test` termina en error si falla algún escenario.

Navegador visible (para ver la ejecución):

```bash
./gradlew clean test -Denvironment=visible
```

### Ejecución por tags

| Qué ejecutar | Comando |
|---|---|
| Un Issue | `./gradlew clean test -Dcucumber.filter.tags="@Issue-17"` |
| Un caso | `./gradlew clean test -Dcucumber.filter.tags="@TC-17"` |
| Un requisito | `./gradlew clean test -Dcucumber.filter.tags="@REQ-14"` |
| Una funcionalidad | `./gradlew clean test -Dcucumber.filter.tags="@cupones"` |
| Smoke | `./gradlew clean test -Dcucumber.filter.tags="@smoketest"` |
| Solo negativos | `./gradlew clean test -Dcucumber.filter.tags="@negativo"` |
| Todo menos defectos conocidos | `./gradlew clean test -Dcucumber.filter.tags="@HU-01 and not @defecto"` |

Comprobado: los escenarios no seleccionados aparecen como *skipped* en Gradle y no se ejecutan.

## 11. Reportes y capturas

| Qué | Dónde |
|---|---|
| Reporte principal de Serenity | `target/site/serenity/index.html` |
| Capturas de pantalla (solo en fallos, `FOR_FAILURES`) | `target/site/serenity/*.png`, enlazadas desde cada paso fallido |
| JSON de Cucumber (lo usa el pipeline) | `target/cucumber-reports/cucumber.json` |
| Resultados JUnit de Gradle | `build/test-results/test/*.xml` y `build/reports/tests/test/index.html` |

Cómo leer el reporte: la pestaña **Requirements** organiza los resultados por carpeta de features
(acceso, catálogo…); cada escenario muestra la narrativa de Laura (las descripciones de `Task.where` y
`Target.the(...)`), los valores comparados por cada aserción y, si falló, el error y la captura.
Para depurar con capturas en cada acción: `-Dserenity.take.screenshots=FOR_EACH_ACTION`.

## 12. Pipeline `ci.yml`

Basado en el `ci.yml` del profesor (se conservaron disparadores, caché de Gradle, lógica de tags y
publicación con `if: always()`). Cambios: `-Dkarate.options` → `-Dcucumber.filter.tags`, rutas de
reportes de Serenity, verificación de escenarios ejecutados y actualización de Issues.

1. **Disparadores:** `push` y `pull_request` a `main`/`develop` ejecutan `@smoketest` (7 escenarios, como en
   el pipeline original). `workflow_dispatch` acepta cualquier expresión en `testTag`; vacío = todos.
2. Instala JDK 21 (Temurin), usa el wrapper de Gradle y el Chrome del runner.
3. Ejecuta `./gradlew clean test` con el filtro; el job **falla si falla un escenario**.
4. **Verify that scenarios were executed:** falla si la expresión de tags no seleccionó ningún escenario
   (evita un verde falso).
5. Publica los artefactos `serenity-report`, `cucumber-reports` y `test-results` **aunque fallen las pruebas**.
6. **Update linked GitHub Issues:** comenta en cada Issue `@Issue-N` ejecutado el resultado real, el caso,
   la fecha y el enlace a la ejecución, y pone `automated-passed` o `automated-failed`
   (permisos mínimos: `contents: read`, `issues: write`; usa el `GITHUB_TOKEN`).

Ejecutar un caso desde GitHub: *Actions → CI → Run workflow* → `testTag`: `@Issue-5`.
Descargar reportes: abrir la ejecución → sección *Artifacts* → `serenity-report` → descomprimir y abrir `index.html`.

## 13. Trazabilidad con GitHub Issues

Cada escenario tiene su Issue (#1 a #32) con caso, criterio, precondiciones, datos, resultado esperado,
archivo, tags y criterios de aceptación. Convención de tags:

```gherkin
@REQ-14 @REQ-15 @Issue-18 @TC-18 @positivo
Escenario: El cupón ELIXIR20 descuenta el 20% cuando el subtotal llega a $100.000
```

(`@HU-01` se hereda del tag de la `Característica`). Matriz completa: [docs/trazabilidad.md](docs/trazabilidad.md).

## 14. Project Board

Ver la sección *Pendientes manuales*: el token de `gh` no tiene el permiso `project`, por lo que el tablero
no pudo crearse desde aquí. Pasos exactos para crearlo y vincularlo en
[docs/trazabilidad.md](docs/trazabilidad.md#4-project-board).

## 15. Resultados

Ver la sección **Resultados de ejecución** al final de este archivo.

## 16. Limitaciones y pendientes manuales

Ver la sección final **Pendientes manuales**.

---

## Resultados de ejecución

Ejecución local completa, 2026-10-12, Windows 11, OpenJDK 21.0.12, Chrome 154 en modo headless:

```text
./gradlew clean test
45 tests completed, 2 failed      (32 escenarios; 45 ejecuciones contando los ejemplos)
BUILD FAILED in 20m 15s           (falla a propósito: hay 2 defectos de la aplicación)
Main report: target/site/serenity/index.html
```

| Resultado | Escenarios |
|---|---|
| ✅ Pasan (30 escenarios, 43 ejecuciones) | TC-01 a TC-12, TC-14 a TC-30, TC-32 |
| ❌ Fallan por defecto de la app | **TC-13** (#13): el carrito acepta 4 unidades de Elixir de Vitalidad con stock 3 → *"Cantidad actualizada"* · **TC-31** (#31): IVA $3.515 en vez de $3.164 (se calcula antes del descuento) |

Corridas parciales verificadas: `@TC-01` (filtro por tag: solo 1 ejecutado, el resto *skipped*),
`@acceso or @catalogo` (15/15 pasan), `@carrito or @cupones or @pago` (28/30; fallan TC-13 y TC-31).
El `cucumber.json` generado se validó: los 32 escenarios llevan su `@Issue-N` y el script de Issues
detecta como fallidos solo #13 y #31.

**GitHub Actions:** todavía no se ha ejecutado, porque el código aún no está en GitHub (falta el `push`).

## Pendientes manuales

1. **Subir el código** a https://github.com/danielcastro1204/Serenity (el repositorio local ya tiene un commit
   en `main` con el remoto `origin` configurado): `git push -u origin main`. El push lanza el CI
   (`@smoketest`), que comenta en los Issues #1, #2, #4, #9, #16, #17 y #32.
2. **Ejecución completa en Actions:** *Actions → CI → Run workflow* con `testTag` vacío. Se espera que
   quede en rojo por TC-13 y TC-31 y que los 32 Issues reciban su comentario y su etiqueta.
3. **Project Board:** requiere `gh auth refresh -s project`. Pasos en [docs/trazabilidad.md](docs/trazabilidad.md#4-project-board).
4. Opcional: crear los secretos `BOTICA_USERNAME` y `BOTICA_PASSWORD` (sin ellos se usan los datos de práctica).
