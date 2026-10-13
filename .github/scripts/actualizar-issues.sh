#!/usr/bin/env bash
# Publica en cada Issue vinculado (tag @Issue-N) el resultado real de su escenario
# y actualiza las etiquetas automated-passed / automated-failed.
# Uso: actualizar-issues.sh <cucumber.json>
# Requiere: gh (autenticado con GH_TOKEN), jq, y las variables RUN_URL y GITHUB_REPOSITORY.
set -euo pipefail

report="${1:?Indique la ruta del cucumber.json}"
repo="${GITHUB_REPOSITORY:?}"
run_url="${RUN_URL:?}"
fecha="$(TZ=America/Bogota date '+%Y-%m-%d %H:%M (hora Colombia)')"

gh label create automated-passed --repo "$repo" --color 0e8a16 \
  --description "El escenario automatizado pasó en la última ejecución" --force >/dev/null
gh label create automated-failed --repo "$repo" --color d73a4a \
  --description "El escenario automatizado falló en la última ejecución" --force >/dev/null

# Un registro por Issue. Un Esquema del escenario genera varios ejemplos: el Issue
# queda en "failed" si falla cualquiera de ellos. Los escenarios no seleccionados por
# el filtro de tags (todos sus pasos "skipped") se ignoran.
jq -r '
  [ .[] | .elements[]? | select(.type == "scenario")
    | select(any(.steps[]?; .result.status != "skipped"))
    | {
        issue: ([.tags[]?.name | select(test("^@Issue-[0-9]+$")) | ltrimstr("@Issue-")] | first),
        tc:    ([.tags[]?.name | select(test("^@TC-[0-9]+$")) | ltrimstr("@")] | first // "sin TC"),
        name:  .name,
        failed: any(.steps[]?, .before[]?, .after[]?; .result.status as $s
                    | ["failed", "undefined", "pending", "ambiguous"] | index($s) != null)
      }
    | select(.issue != null) ]
  | group_by(.issue)[]
  | {
      issue: .[0].issue, tc: .[0].tc, name: .[0].name,
      total: length, failed: ([.[] | select(.failed)] | length)
    }
  | [.issue, .tc, .name, (.total | tostring), (.failed | tostring)] | @tsv
' "$report" > /tmp/resultados-issues.tsv

if [ ! -s /tmp/resultados-issues.tsv ]; then
  echo "No hay escenarios ejecutados con tag @Issue-N; no se actualiza ningún Issue."
  exit 0
fi

{
  echo "### Resultado por Issue"
  echo "| Issue | Caso | Escenario | Ejemplos | Resultado |"
  echo "|---|---|---|---|---|"
} >> "${GITHUB_STEP_SUMMARY:-/dev/null}"

while IFS=$'\t' read -r issue tc name total failed; do
  if [ "$failed" -eq 0 ]; then
    estado="✅ PASÓ"; agregar="automated-passed"; quitar="automated-failed"
  else
    estado="❌ FALLÓ"; agregar="automated-failed"; quitar="automated-passed"
  fi

  cuerpo="**Resultado de la ejecución automatizada: ${estado}**

| Campo | Valor |
|---|---|
| Caso de prueba | ${tc} |
| Escenario | ${name} |
| Ejemplos ejecutados | ${total} (fallidos: ${failed}) |
| Fecha | ${fecha} |
| Ejecución | ${run_url} |

El detalle de pasos y capturas está en el artefacto \`serenity-report\` de la ejecución."

  echo "Issue #${issue} (${tc}): ${estado}"
  gh issue comment "$issue" --repo "$repo" --body "$cuerpo" >/dev/null
  gh issue edit "$issue" --repo "$repo" --add-label "$agregar" --remove-label "$quitar" >/dev/null 2>&1 \
    || gh issue edit "$issue" --repo "$repo" --add-label "$agregar" >/dev/null

  echo "| #${issue} | ${tc} | ${name} | ${total} | ${estado} |" >> "${GITHUB_STEP_SUMMARY:-/dev/null}"
done < /tmp/resultados-issues.tsv
