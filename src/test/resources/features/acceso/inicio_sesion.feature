# language: es
@HU-01 @acceso
Característica: Acceso a La Botica del Alquimista
  Como cliente de La Botica del Alquimista
  quiero iniciar sesión con mi usuario y contraseña
  para poder comprar mis artículos de alquimia

  Antecedentes:
    Dado que Laura abre la tienda La Botica del Alquimista

  @REQ-01 @Issue-1 @TC-01 @positivo @smoketest
  Escenario: Iniciar sesión con credenciales válidas
    Cuando Laura inicia sesión con sus credenciales de cliente
    Entonces Laura debería ver el catálogo de productos
    Y Laura debería ver el saludo "Hola, alquimista"

  @REQ-02 @Issue-2 @TC-02 @negativo @smoketest
  Esquema del escenario: Rechazar el acceso con credenciales incorrectas
    Cuando Laura intenta iniciar sesión con el usuario "<usuario>" y la contraseña "<contrasena>"
    Entonces Laura debería ver el mensaje de error de acceso "Usuario o contraseña incorrectos"
    Y Laura debería permanecer en la página de inicio de sesión

    Ejemplos:
      | usuario     | contrasena |
      | alquimista  | pocion999  |
      | aprendiz    | pocion123  |
      | ALQUIMISTA  | pocion123  |

  @REQ-01 @Issue-3 @TC-03 @negativo
  Escenario: Impedir el acceso al catálogo sin haber iniciado sesión
    Cuando Laura intenta abrir directamente la página "catalogo.html"
    Entonces Laura debería permanecer en la página de inicio de sesión
