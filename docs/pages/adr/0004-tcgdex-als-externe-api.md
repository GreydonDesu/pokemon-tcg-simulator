# 4 - TCGdex als externe API

Date: 2025-04-12
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Als Anforderung des Projektes wird die Anbinding des Backends an eine externe API für Informationen gebraucht. Das Projekt soll eine Simulation vom dem Öffnen von Kartenpacks wie aus dem Spiel Pokémon TCG Pocket sein, welches bereits in arc42 beschrieben wurde.

## Decision

Als externe API für die Informationen der Karten wird [TCGdex](https://tcgdex.dev/) verwendet. Ich habe es bereits im [Open Source Quickcheck](https://pokemon-tcg-simulator.readthedocs.io/de/latest/open_source/) geprüft, ob diese API geeignet für die Anwendung ist.

## Consequences

Über diese API ist nicht viel öffentlich zugänglich. Ebenso weiß ich nicht, ob die API DDoS-Schutz hat, was dazu führen kann, das meine Applikation ausgesperrt wird, wenn ich zu viele Anfragen zur API stelle.
