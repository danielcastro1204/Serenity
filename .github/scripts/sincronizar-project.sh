#!/usr/bin/env bash
# Mueve cada Issue de caso de prueba a su columna del Project Board según su etiqueta:
#   automated-passed -> Verificado · automated-failed o sin ejecutar -> Automatizado
# Se ejecuta localmente (el GITHUB_TOKEN de Actions no puede escribir en proyectos de usuario).
# Requiere: gh autenticado con el permiso "project" (gh auth refresh -s project).
# Uso: bash .github/scripts/sincronizar-project.sh [numero-del-proyecto]
set -euo pipefail

owner="danielcastro1204"
repo="$owner/Serenity"
number="${1:-3}"

project_id=$(gh api graphql -f query='query($o:String!,$n:Int!){user(login:$o){projectV2(number:$n){id}}}' \
  -f o="$owner" -F n="$number" --jq .data.user.projectV2.id)
field=$(gh api graphql -f query='query($p:ID!){node(id:$p){... on ProjectV2{field(name:"Status"){... on ProjectV2SingleSelectField{id options{id name}}}}}}' \
  -f p="$project_id" --jq .data.node.field)
field_id=$(jq -r .id <<<"$field" 2>/dev/null || python -c "import json,sys;print(json.loads(sys.argv[1])['id'])" "$field")
option_of() {
  python -c "import json,sys;print(next(o['id'] for o in json.loads(sys.argv[1])['options'] if o['name']==sys.argv[2]))" "$field" "$1"
}
verified=$(option_of "Verificado")
automated=$(option_of "Automatizado")

# Elementos del tablero: id del item, número del Issue y etiquetas
gh api graphql -f query='query($p:ID!){node(id:$p){... on ProjectV2{items(first:100){nodes{id content{... on Issue{number labels(first:20){nodes{name}}}}}}}}}' \
  -f p="$project_id" --jq '.data.node.items.nodes[] | "\(.id)\t\(.content.number)\t\([.content.labels.nodes[].name] | join(","))"' |
while IFS=$'\t' read -r item issue labels; do
  if [[ ",$labels," == *",automated-passed,"* ]]; then
    option="$verified"; column="Verificado"
  else
    option="$automated"; column="Automatizado"
  fi
  gh api graphql -f query='mutation($p:ID!,$i:ID!,$f:ID!,$o:String!){updateProjectV2ItemFieldValue(input:{projectId:$p,itemId:$i,fieldId:$f,value:{singleSelectOptionId:$o}}){projectV2Item{id}}}' \
    -f p="$project_id" -f i="$item" -f f="$field_id" -f o="$option" >/dev/null
  echo "#$issue -> $column"
done
