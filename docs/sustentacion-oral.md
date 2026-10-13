# Guía de sustentación oral · Serenity BDD + Screenplay · La Botica del Alquimista

Todo lo que aparece aquí corresponde a archivos reales del proyecto. Rutas de Java relativas a
`src/test/java/com/botica/alquimista/`; features en `src/test/resources/features/`.

---

## 1. Guion de exposición (3 a 5 minutos)

**1. Objetivo (20 s).** "El taller consiste en automatizar pruebas Front-End con Serenity BDD. Elegí
La Botica del Alquimista y automaticé la historia HU-01: buscar productos, armar el carrito, aplicar un
cupón y confirmar el pedido pagando el valor correcto."

**2. La aplicación (30 s).** "Es una tienda web estática. Lo importante para las pruebas es que guarda
sesión, carrito, cupón, stock y pedidos en el localStorage del navegador. Por eso configuré
`serenity.restart.browser.for.each = scenario`: cada escenario abre un Chrome nuevo y arranca con el
inventario inicial. Así los escenarios son independientes, y comprar en un escenario no afecta a otro."

**3. HU-01 y casos (45 s).** "Separé la historia en 26 criterios, REQ-01 a REQ-26, y diseñé 32
escenarios: 15 positivos y 17 negativos, en cinco features: acceso, catálogo, carrito, cupones y pago.
Por ejemplo, el negativo TC-19 verifica que ELIXIR20 con $92.500 muestra exactamente 'Este cupón requiere
una compra mínima de $100.000', y el positivo TC-18 usa justo el límite: Poción de Invisibilidad más
Amuleto de Protección suman $100.000."

**4. Screenplay (60 s).** "El flujo es Feature → Step Definition → Actor → Task → Target. El actor es
Laura. En `ScreenplayHooks` el `@Before` monta un `OnlineCast`, que le da a Laura la habilidad
BrowseTheWeb. Los steps solo traducen: por ejemplo, 'Laura aplica el cupón LUNA10' llama a
`Coupon.apply("LUNA10")`, que es un Task que escribe el código, hace clic en Aplicar y espera con mi
interacción `WaitForProcessing` a que desaparezca la capa de carga. Los localizadores están en `ui/`,
como `CartPage.COUPON_FIELD`, y las consultas en `questions/`, como `TheMessage.shownIn(...)`, que espera a
que el mensaje sea visible antes de leerlo."

**5. Validaciones (30 s).** "Las aserciones comparan lo que ve el usuario: textos exactos de los mensajes,
subtotales, descuento, IVA, envío y total con separador de miles. No doy por bueno un escenario solo
porque se hizo clic."

**6. Defectos encontrados (30 s).** "Dos escenarios fallan y es correcto que fallen: TC-13, porque en el
carrito la app deja subir Elixir de Vitalidad a 4 unidades aunque el stock es 3, y TC-31, porque la app
calcula el IVA antes del descuento: con LUNA10 sobre $18.500 cobra $3.515 en vez de $3.164. No cambié la
expectativa; los marqué con `@defecto`."

**7. Trazabilidad (30 s).** "Cada escenario tiene un Issue, del #1 al #32, y los tags `@HU-01`, `@REQ-xx`,
`@Issue-N` y `@TC-xx`. El pipeline lee esos tags del cucumber.json y comenta el resultado en el Issue con la
etiqueta automated-passed o automated-failed."

**8. Ejecución y reportes (30 s).** "Localmente: `./gradlew clean test`. El reporte queda en
`target/site/serenity/index.html`. En GitHub Actions, el ci.yml del profesor se adaptó de Karate a
Cucumber: en push corre `@smoketest`, y manualmente puedo pasar cualquier tag, por ejemplo `@Issue-17`.
Los reportes se suben como artefactos aunque fallen las pruebas."

**9. Resultados (15 s).** "En la última ejecución completa: 32 escenarios, 45 ejecuciones con ejemplos;
pasan todas menos los 2 defectos de la aplicación."

---

## 2. Mapa rápido del código

| Pieza | Archivo | Para qué |
|---|---|---|
| Runner | `runners/CucumberTestSuite.java` | `@Suite` + `@IncludeEngines("cucumber")`, glue `com.botica.alquimista`, plugins Serenity + JSON |
| Escena | `hooks/ScreenplayHooks.java` | `OnStage.setTheStage(new OnlineCast())` y `@ParameterType` `{actor}` |
| Credenciales | `config/CustomerCredentials.java` | `BOTICA_USERNAME`/`BOTICA_PASSWORD` o `serenity.conf` |
| Tasks | `tasks/*.java` | `Login`, `Navigate`, `BrowseCatalog`, `AddToCart`, `ManageCart`, `Coupon`, `Checkout` |
| Interactions | `interactions/WaitForProcessing.java`, `interactions/EnterSecret.java` | espera explícita; contraseña oculta en el reporte |
| Questions | `questions/TheMessage.java`, `TheCatalog.java`, `TheCart.java`, `TheOrderSummary.java` | leer lo que ve el usuario |
| Targets | `ui/*.java` | localizadores con nombre de negocio |
| Configuración | `src/test/resources/serenity.conf` | URL base, Chrome headless, capturas en fallos, navegador por escenario |

---

## 3. Preguntas y respuestas técnicas

**1. ¿Qué es una prueba Front-End?**
Una prueba que usa la aplicación como el usuario: a través del navegador, con clics, formularios y
lectura de lo que aparece en pantalla. Aquí Selenium maneja Chrome contra https://boticaalquimista.onrender.com.

**2. ¿Diferencia entre probar la interfaz y probar una API?**
La API se prueba enviando peticiones HTTP y validando respuestas (JSON, códigos), sin pantalla; es más
rápida y estable. La interfaz valida además lo que ve el usuario: mensajes, botones deshabilitados,
formatos ("$27.814"). En esta app no hay API: toda la lógica está en el JavaScript del navegador, por eso
la prueba de interfaz es la adecuada.

**3. ¿Qué es Serenity BDD?**
Un framework que integra Selenium, Cucumber y JUnit, organiza la automatización (Screenplay) y genera
reportes de documentación viva con la narrativa de cada escenario, aserciones y capturas. Versión usada:
5.3.9 (`build.gradle`).

**4. ¿Qué es Gherkin?**
El lenguaje de los `.feature`: `Característica`, `Antecedentes`, `Escenario`, `Dado/Cuando/Entonces/Y`.
Uso `# language: es` en la primera línea para escribirlo en español.

**5. ¿Diferencia entre `Feature`, `Scenario` y `Scenario Outline`?**
`Característica` agrupa escenarios de una funcionalidad (p. ej. `cupones.feature`). `Escenario` es un caso
concreto. `Esquema del escenario` es una plantilla que se ejecuta una vez por fila de `Ejemplos`: TC-17 se
ejecuta con `LUNA10` y con `luna10`. Cuenta como un escenario y dos ejecuciones.

**6. ¿Qué es el patrón Screenplay?**
Un patrón centrado en el usuario: un **actor** con **habilidades** realiza **tareas** formadas por
**interacciones** sobre **targets** y responde **preguntas** sobre el estado del sistema. Frente a Page
Object, separa mejor "qué quiere lograr el usuario" de "cómo se manipula la página".

**7. ¿Qué representa un Actor?**
El usuario que interactúa. En Gherkin es "Laura"; `ScreenplayHooks.actor(String)` lo convierte en
`OnStage.theActorCalled("Laura")`. Recuerda datos con `remember`/`recall` (TC-32 guarda el stock inicial).

**8. ¿Qué es una Ability?**
Una capacidad técnica del actor. Laura tiene `BrowseTheWeb` (un WebDriver), que asigna `OnlineCast` en el
`@Before`. Por eso ningún step crea ni cierra el navegador.

**9. ¿Cuándo se usa un Task?**
Para una meta de negocio que agrupa varias acciones. `Checkout.fillShippingDetails(details)` escribe los 5
campos del formulario; `Coupon.apply(code)` escribe, aplica y espera.

**10. ¿Diferencia entre Task e Interaction?**
La Interaction es atómica y técnica (un clic, escribir, esperar); el Task expresa intención de negocio.
`WaitForProcessing.toFinish()` es una Interaction (espera a que desaparezca `#capa-cargando`); `Login.withCredentials`
es un Task que usa `Enter`, `EnterSecret`, `Click` y `WaitForProcessing`.

**11. ¿Qué es una Question?**
Una consulta del estado de la pantalla que devuelve un valor. `TheCart.quantityOf("Elixir de Vitalidad")`
lee el campo numérico de la línea; `TheOrderSummary.valueOf("IVA")` lee `dd.valor-iva`. La pregunta
consulta; la expectativa (`Ensure...isEqualTo`) está en el step.

**12. ¿Para qué sirve un Target?**
Centraliza un localizador con un nombre legible para el reporte. `CartPage.APPLY_COUPON_BUTTON =
Target.the("botón aplicar cupón").locatedBy("css:#btn-aplicar-cupon")`. Si cambia la página, se corrige en
un solo lugar. Los dinámicos usan `{0}`: `CartPage.REMOVE_BUTTON_OF.of("Amuleto de Protección")`.

**13. ¿Cómo se conectan las step definitions con Screenplay?**
Cucumber relaciona el texto del paso con la expresión de la anotación. "Laura aplica el cupón \"LUNA10\""
coincide con `@Cuando("{actor} aplica el cupón {string}")` en `CouponStepDefinitions.appliesCoupon`, que
recibe el `Actor` y ejecuta `actor.attemptsTo(Coupon.apply(code))`.

**14. ¿Cómo se construye un localizador estable?**
Inspeccionando el DOM real y prefiriendo `id`, `data-testid`, `name` o `aria-label` sobre posiciones o
clases visuales. En `checkout.html` los ids `inp-x92`, `t-771` y `c1` parecen generados, por eso usé
`input[name='correo']`, `input[name='telefono']` e `input[name='ciudad']`. Nunca XPath absoluto.

**15. ¿Por qué son necesarias las esperas explícitas?**
La app simula demoras aleatorias (300 ms a 1,6 s) al validar el login, consultar cupones y confirmar.
Leer el mensaje antes de tiempo daría un texto vacío. `WaitForProcessing` usa
`WaitUntil.the(OVERLAY, isNotVisible()).forNoMoreThan(15).seconds()`, que espera lo justo. `Thread.sleep`
espera siempre lo máximo y aun así puede fallar.

**16. ¿Cómo se verifican los errores de un formulario?**
TC-24 confirma con el formulario vacío y compara, campo por campo, el texto de
`span.error-campo[data-campo='{0}']` con "Este campo es obligatorio" (tabla en el Gherkin), además del
mensaje general "Revisa los campos marcados" y que la URL sigue en `/checkout.html`.

**17. ¿Cómo se comprueba el subtotal?**
TC-14: 2 × $18.500 = $37.000 y 1 × $35.000 = $35.000; se lee `td.subtotal-linea` de cada línea
(`CartPage.LINE_SUBTOTAL_OF`) y `#subtotal` del carrito, que debe ser $72.000.

**18. ¿Cómo se verifica la aplicación de un cupón?**
TC-17: carrito de $70.000, se aplica `luna10`; se verifica el mensaje "Cupón LUNA10 aplicado correctamente",
el cupón aplicado `LUNA10`, el descuento `-$7.000` (10 %) y el total parcial `$63.000`.

**19. ¿Cómo se calcula el IVA según HU-01?**
19 % del subtotal **después** del descuento; el envío no lleva IVA. TC-31: $18.500 − $1.850 = $16.650;
IVA = 3.163,5 → $3.164; total = 16.650 + 3.164 + 8.000 = $27.814. La app muestra $3.515 (19 % de $18.500):
es un defecto y por eso el escenario falla.

**20. ¿Para qué sirven los tags?**
Para clasificar y filtrar: `@HU-01` (historia), `@REQ-xx` (criterio), `@Issue-N` (Issue), `@TC-xx` (caso),
`@positivo/@negativo`, `@smoketest`, `@defecto`. Se usan con `-Dcucumber.filter.tags="@cupones and @negativo"`.

**21. ¿Cómo se vincula un escenario con un Issue?**
Con el tag `@Issue-N`, igual al número real del Issue. El script `.github/scripts/actualizar-issues.sh` lee
los tags del `cucumber.json`, comenta el resultado en ese Issue y pone `automated-passed`/`automated-failed`.

**22. ¿Qué ocurre cuando un test falla en GitHub Actions?**
Gradle termina con error, el job queda en rojo, pero los pasos con `if: always()` igual suben los
artefactos `serenity-report`, `cucumber-reports` y `test-results` y actualizan el Issue con "FALLÓ".

**23. ¿Dónde se consultan los reportes de Serenity?**
Local: `target/site/serenity/index.html`. En Actions: la ejecución → *Artifacts* → `serenity-report`.

**24. ¿Cómo diagnosticar un error de Selenium?**
Leer el tipo de excepción y el target: `NoSuchElementException` o `ElementShouldBeEnabledException` →
localizador o espera; `AssertionError: Expected ... Actual ...` → la app hace otra cosa. Abrir el escenario
en el reporte, ver el paso rojo y su captura, y probar el selector en DevTools
(`document.querySelectorAll("...")` o `$x("...")`). Caso real del proyecto: `input[name='clave']` fue
interpretado como XPath por Serenity; se corrigió con el prefijo `css:`.

**25. ¿Por qué cada escenario abre un navegador nuevo?**
Porque el estado vive en localStorage: con un navegador nuevo el stock vuelve al inicial y el carrito está
vacío. Es la forma de garantizar independencia sin un endpoint de restauración.

---

## 4. Ejercicios de modificación en vivo

> Los pasos y targets que reutilizan estos ejercicios se ejecutaron en la suite. Los fragmentos nuevos (ejercicios 3, 4 y 6) **no se han ejecutado todavía**: ensáyelos antes de la sustentación.

### Ejercicio 1 · Añadir una aserción a un escenario

Escenario TC-15 (`carrito/carrito.feature`). Agregar al final:

```gherkin
    Y el contador del carrito de Laura debería indicar 2 unidades
```

No requiere código: el paso ya existe en `CartStepDefinitions.counterShouldShow`. Verificar:

```bash
./gradlew clean test -Dcucumber.filter.tags="@Issue-15"
```

### Ejercicio 2 · Corregir o mejorar un localizador

`ui/StoreHeader.java`, `CATALOG_LINK` usa XPath por texto. Alternativa CSS, igual de estable, basada en el
atributo `href` real (`<a href="catalogo.html" ...>`):

```java
public static final Target CATALOG_LINK = Target.the("enlace al catálogo")
        .locatedBy("css:nav.menu a[href='catalogo.html']");
```

Verificar con TC-32, que lo usa al volver al catálogo: `-Dcucumber.filter.tags="@Issue-32"`.

### Ejercicio 3 · Crear una Task de Screenplay (cerrar sesión)

1. Target real del encabezado (`<button ... name="cerrar-sesion">Cerrar sesión</button>`), en `ui/StoreHeader.java`:

```java
public static final Target LOGOUT_BUTTON = Target.the("botón cerrar sesión")
        .locatedBy("css:button[name='cerrar-sesion']");
```

2. Nueva clase `tasks/Logout.java`:

```java
package com.botica.alquimista.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static com.botica.alquimista.ui.StoreHeader.LOGOUT_BUTTON;

public final class Logout {
    private Logout() {
    }

    public static Performable fromTheStore() {
        return Task.where("{0} cierra sesión", Click.on(LOGOUT_BUTTON));
    }
}
```

3. Step en `AccessStepDefinitions`:

```java
@Cuando("{actor} cierra sesión")
public void logsOut(Actor actor) {
    actor.attemptsTo(Logout.fromTheStore());
}
```

4. Escenario en `acceso/inicio_sesion.feature` (reutiliza el paso existente de "permanecer en inicio de sesión"):

```gherkin
  @REQ-01 @positivo
  Escenario: Cerrar sesión devuelve al inicio de sesión
    Cuando Laura inicia sesión con sus credenciales de cliente
    Y Laura cierra sesión
    Entonces Laura debería permanecer en la página de inicio de sesión
```

### Ejercicio 4 · Añadir un escenario negativo nuevo

Cupón vacío. La app muestra "Ingresa un código de cupón" (`carrito.html`). Todo el código ya existe:

```gherkin
  @REQ-16 @negativo
  Escenario: Aplicar un cupón sin escribir el código
    Dado que Laura agrega 1 unidad de "Polvo de Luna" al carrito
    Y Laura abre su carrito
    Cuando Laura aplica el cupón ""
    Entonces Laura debería ver el mensaje del cupón "Ingresa un código de cupón"
    Y Laura no debería tener ningún cupón aplicado
```

Nota: con código vacío la app no muestra la capa de carga; `WaitForProcessing` igual pasa porque espera
"no visible" y la capa no existe. Para trazabilidad completa habría que crear su Issue y añadir `@Issue-N`.

### Ejercicio 5 · Ejecutar un escenario concreto por su tag

```bash
./gradlew clean test -Dcucumber.filter.tags="@Issue-19"
./gradlew clean test -Dcucumber.filter.tags="@cupones and @negativo"
```

En GitHub: *Actions → CI → Run workflow* → `testTag` = `@Issue-19`. El resumen de la ejecución muestra
la expresión usada y cuántos escenarios corrieron; el Issue #19 recibe el comentario con el resultado.

### Ejercicio 6 · Comprobar el cálculo de un cupón

ELIXIR20 con 2 × Poción de Invisibilidad ($130.000): descuento 20 % = $26.000, total parcial $104.000.

```gherkin
  @REQ-14 @positivo
  Escenario: ELIXIR20 sobre $130.000
    Dado que Laura agrega 2 unidades de "Poción de Invisibilidad" al carrito
    Y Laura abre su carrito
    Cuando Laura aplica el cupón "ELIXIR20"
    Entonces Laura debería ver el descuento "-$26.000"
    Y Laura debería ver el total parcial "$104.000"
```

(Stock de la poción = 2: es válido agregar 2.)

### Ejercicio 7 · Interpretar un fallo en el reporte de Serenity

1. `./gradlew clean test -Dcucumber.filter.tags="@Issue-31"`.
2. Abrir `target/site/serenity/index.html` → escenario "El IVA se calcula sobre el subtotal después del
   descuento…" (rojo).
3. El paso fallido muestra: `Expected: el valor de IVA en el resumen de pago that is equal to: <"$3.164">`,
   `Actual: <"$3.515">`. La captura muestra el resumen de pago.
4. Diagnóstico: 3.515 = 18.500 × 0,19 → la app calcula el IVA antes del descuento. Es un defecto de la
   aplicación (criterio F3.5), no de la prueba: el localizador encontró el valor y la espera funcionó.

### Ejercicio 8 · Construir manualmente un localizador

Elemento real: el botón **Reiniciar datos** del encabezado (pintado por `js/comun.js`):

```html
<button type="button" class="btn-texto" data-testid="reiniciar-datos">Reiniciar datos</button>
```

Opciones, de más a menos recomendable:

| Tipo | Localizador | Comentario |
|---|---|---|
| data-testid (CSS) | `[data-testid='reiniciar-datos']` | Atributo pensado para pruebas: el más estable |
| CSS por clase | `header.barra button.btn-texto` | Ambiguo: también coincide con "Cerrar sesión" |
| XPath por texto | `//button[normalize-space()='Reiniciar datos']` | Válido si no hay data-testid |
| XPath absoluto | `/html/body/header/div/button[1]` | **No usar**: se rompe con cualquier cambio |

Cómo probarlo en el navegador: F12 → Console → `document.querySelectorAll("[data-testid='reiniciar-datos']").length`
debe dar `1`; para XPath: `$x("//button[normalize-space()='Reiniciar datos']")`.

Target en `ui/StoreHeader.java`:

```java
public static final Target RESET_DATA_BUTTON = Target.the("botón reiniciar datos")
        .locatedBy("css:[data-testid='reiniciar-datos']");
```

Otro ejemplo frecuente: el precio de un producto concreto en el catálogo:
`//article[.//h3[normalize-space()='Polvo de Luna']]//p[@class='precio']` → "$8.500".

---

## 5. Cinco conceptos que hay que dominar

1. **Actor / Ability / Task / Interaction / Question / Target** y dónde está cada uno en el proyecto.
2. **Cómo un paso Gherkin llega al navegador**: texto → expresión `{actor} … {string}` → step → `attemptsTo(Task)` → `Target` → Selenium.
3. **Localizadores estables** y por qué los CSS llevan `css:`.
4. **Esperas explícitas** (`WaitForProcessing`, `TheMessage.shownIn`) frente a `Thread.sleep`.
5. **Trazabilidad y CI**: tags `@REQ`/`@Issue`, filtro `cucumber.filter.tags`, artefactos y comentarios en Issues; y que un escenario rojo puede ser un defecto real (TC-13, TC-31), no un error de la prueba.
