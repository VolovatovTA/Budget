#!/usr/bin/env bash
# Prints the module dependency graph as Mermaid, read from the build scripts.
# Every edge is a real project(...) dependency; nothing is summarised.
# Usage: scripts/module-graph.sh > /tmp/graph.md, then paste into README.md.
set -euo pipefail
cd "$(dirname "$0")/.."

# node id for a module path like features:bottom-navigation:host
id() { echo "$1" | sed -E 's/^features://; s/^core:/core_/; s/^bottom-navigation:/bn_/; s/[-:]/_/g'; }
label() { echo "$1" | sed -E 's/^features://; s/^core://'; }

modules=$(find androidApp core shared features -name build.gradle.kts | sed 's|/build.gradle.kts||; s|/|:|g' | sort)

echo 'flowchart TB'
echo '    androidApp'
echo '    subgraph features'
for m in $modules; do
  case "$m" in features:currency-rates) ;; features:*) echo "        $(id "$m")[$(label "$m")]";; esac
done
echo '    end'
echo '    subgraph data'
echo '        currency_rates[currency-rates]'
echo '    end'
echo '    subgraph core'
for m in $modules; do
  case "$m" in core:*) echo "        $(id "$m")[$(label "$m")]";; esac
done
echo '    end'
echo '    shared[shared · KMP]'
echo
for m in $modules; do
  f="$(echo "$m" | sed 's|:|/|g')/build.gradle.kts"
  { grep -oE 'project\(":[^"]+"\)' "$f" || true; } | sed -E 's/project\(":(.*)"\)/\1/' | while read -r to; do
    echo "    $(id "$m") --> $(id "$to")"
  done
done
