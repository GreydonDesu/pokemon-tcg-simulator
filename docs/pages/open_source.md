# Open Source Quickcheck

## Library: TCGdex

Website Links:

- <https://tcgdex.dev/>
- <https://github.com/tcgdex>

Aktuellste Version: 2.30.0 (Release am 29. März 2025)  
Issue Tracking: <https://github.com/tcgdex/cards-database/issues>  
Dokumentation: <https://tcgdex.dev/>  

### Quickcheck

#### Lizenz

Die API und die dazugehörige Datenbank ist mittels [MIT License](https://github.com/tcgdex/cards-database/blob/master/LICENSE) lizensiert seit 2021.

#### Reifegrad

- Erste Commit auf die Datenbank war am 3. Februar 2020: [Commit 0d2a757](https://github.com/tcgdex/cards-database/commit/0d2a757cae242abfdf567385d0b9a1ec3e84a485)
- Älteste öffentliche Release ist Version 2.5.3 vom 29. Oktober 2021: [2.5.3: Fixed Datas](https://github.com/tcgdex/cards-database/releases/tag/2.5.3)

#### Support

- Es gibt einen Discord mit **über 500 Mitgliedern** zum Zeitpunkt der Erstellung des Dokumentes

![Discord](assets/discord.png)

- Issues werden über normale GitHub Issues dokumentiert: <https://github.com/tcgdex/cards-database/issues>
- Antwortzeiten können etwas lange dauern, da das Projekt größtenteils von 7 Entwicklern maintainted wird.

#### Maven/Gradle

- Die API kann mittels cURL oder mit SDKs für Java/Kotlin, JavaScript, TypeScript, PHP und Python erreicht werden: <https://tcgdex.dev/sdks>

#### Dokumentation

- Die eigene Website der API hat ausführliche Dokumentation, welche Endpunkte angesprochen werden können, in welcher Form und was die Fehler sein können, die zurückgeworfen werden: <https://tcgdex.dev/>

#### Mängel/Bugs

- Es gibt eine GitHub Action für das Testen von Branches, welche in PRs verpackt sind. Beispiel: <https://github.com/tcgdex/cards-database/actions/runs/14120595893/job/39559990434?pr=699>
- Bezüglich CVE und Vulnerabilities konnte ich nicht vieles finden.

#### Innere Qualität

- GitHub Actions werden für den Build verwendet: <https://github.com/tcgdex/cards-database/actions>
- Dependabot ist in den GitHub Actions inkludiert für das Prüfen der Dependencies des Projektes
- Projekt wird als Dockerimage gebaut

#### Projekt-Aktivität

- Im letzten Monat hab es 8 PRs, die gemergt wurden, 7 PRs geöffnet, dazu 1 Issue geschlossen und 7 neue Issues verfasst: <https://github.com/tcgdex/cards-database/pulse/monthly>

#### Bekanntheitsgrad

- In Suchmaschinen taucht die API als zweite Alternative auf neben der etablierteren Pokémon TCG, welche aber einen Entwicklerprofil verlangt: <https://pokemontcg.io/>
