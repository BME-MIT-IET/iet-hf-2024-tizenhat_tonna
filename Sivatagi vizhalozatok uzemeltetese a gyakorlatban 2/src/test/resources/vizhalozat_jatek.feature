Feature: Sivatagi Vízhálózat Játék

  Scenario: Jatekos mozgatasa egy pumparol ures csore
    Given A jatek inicializalasra kerult
    When "Mec1" mozog "AC2" re a 1 -s scenarioban
    Then A művelet sikeresen végrehajtódik a(z) 1 -s scenarioban

  Scenario: Jatekos mozgatasa egy pumparol mar foglalt csore
    Given A jatek inicializalasra kerult
    When "Mec1" mozog "AB" re a 2 -s scenarioban
    Then A művelet végrehatása sikertelen a(z) 2 -s scenarioban

  Scenario: Jatekos mozgatasa egy csorol szomszedos pumpara melyen mar tartozkodik jatekos
    Given A jatek inicializalasra kerult
    When "Sab1" mozog "A" re a 3 -s scenarioban
    Then A művelet sikeresen végrehajtódik a(z) 3 -s scenarioban

  Scenario: Szerelok megjavitjak a meghibasodott pumpat
    Given A jatek inicializalasra kerult
    When "Mec1" megjavitja a pumpat melyen jelenleg tartozkodik a 4 -s scenarioban
    Then A művelet sikeresen végrehajtódik a(z) 4 -s scenarioban

  Scenario: Szabotorok kilyukasztanak egy fontos csovet
    Given A jatek inicializalasra kerult
    When A "Sab1" tonkreteszi az elemet amin jelenleg tartozkodik a 5 -s scenarioban
    Then A művelet sikeresen végrehajtódik a(z) 5 -s scenarioban

  Scenario: Szabotorok beavatkoznak a mukodo pumpakba
    Given A jatek inicializalasra kerult
    When "Sab1" mozog "B" re a 6 -s scenarioban
    And "Sab1" a jelenlegi poziciojan a bemeneti csovet "BS2" re es kimeneti csovet "CB" re csereli a 6 -s scenarioban
    Then A művelet sikeresen végrehajtódik a(z) 6 -s scenarioban