# Decisions

A lightweight decision log. One entry per decision that shapes the code: what, why, what it costs,
when to revisit. Agents add an entry with status **Proposed** when a rule in
[architecture.md](architecture.md) or a non-goal in [goals.md](goals.md) is in the way, and stop;
the maintainer accepts or rejects. An implicit decision found in code (something the code now assumes
and later work would have to undo) is reported by the architect review and recorded here too.

Format:

```
## NNNN · Title            Status: Proposed | Accepted | Rejected | Superseded · Date
Context — Decision — Consequences — Revisit when
```

## 0001 · Dependencies point down, graph is asserted · Accepted · 2026-10-09

Context: features had started importing each other and the core.
Decision: `androidApp → features → data → core` with the allowed edges listed in the root build and
checked by `assertModuleGraph`; `bottom-navigation:host` is the single feature allowed to depend on
features. Consequences: a shared need between two features goes to `core` or a data module, never to a
feature-to-feature edge. Revisit when: `host` moves into `androidApp` (then no exception is needed).

## 0002 · Material lives inside the design system · Accepted · 2026-11-09

Context: screens mixed Material 2 defaults with kit components and three fonts.
Decision: `core:designsystem` owns tokens (light/dark), the theme (Material 3 scheme mapped from the
tokens, legacy Material 2 colours kept for the migration) and every component; features use kit
components only. Consequences: a component swap is one PR across all call sites; no second typeface,
no literal colours in features. Revisit when: all Material 2 widgets are gone from features (then the
M2 bridge in `BudgetTheme` is deleted).

## 0003 · Entity colours derive from the id, not from stored data · Accepted · 2026-11-09

Context: categories and wallets have no colour field; the refresh colours them.
Decision: slot = `stablePaletteIndex(id, palette.size)` over `String.hashCode`; palettes per theme with
the same hue order; seven category hues, four wallet gradients. Consequences: the same entity has the
same colour on every screen and device without storage; the eighth category repeats a hue; an id change
(local → server id after sync) changes the colour. Revisit when: two-way sync assigns server ids to
local entities, or users ask to pick colours — then a colour field on the entity replaces the hash.

## 0004 · Tests pin behaviour, not coverage · Accepted · 2026-11-09

Context: constant-restating tests appeared to satisfy the 50 % changed-files gate.
Decision: a test has at most two assertions (Konsist `TestShapeTest`), names one behaviour and would
fail on a concrete production change; constants and data holders are excluded from Kover instead of
tested. Consequences: coverage may read lower; reviewers audit every test in a diff (count, purpose,
regression caught). Revisit when: never, by design.
