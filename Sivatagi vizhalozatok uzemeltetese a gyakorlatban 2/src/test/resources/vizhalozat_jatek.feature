Feature: Sivatagi Vízhálózat Játék

  Scenario: Jatekos mozgatása egy pumpáról üres csőre
    Given A jatek inicializalasra kerult
    Given test
    When "Mec1" mozog "AC2" re a 1 -s scenarioban
    Then A művelet sikeresen végrehajtódik a(z) 1 -s scenarioban

  #Scenario: Jatekos mozgatasa egy pumparol mar foglalt csore
   # Given A jatek inicializalasra kerult
    #When "Mec1" mozog "AB" re a(z) 2 -s scenarioban
    #Then A művelet végrehatása sikertelen a(z) 2 -s scenarioban

  Scenario: Játékos mozgatása egy csőről szomszédos pumpára melyen már tartózkodik játékos
    Given A szerelő csapat egyik játékosa egy csőn áll
    And Egy másik játékos tartózkodik a cél pumpán
    When Mozog a cél pumpára
    Then A művelet sikeresen végrehajtódik

  Scenario: Szerelők megjavítják a meghibásodott pumpát
    Given A játékban egy meghibásodott pumpa van a csőhálózatban
    When A szerelők megjavítják a pumpát
    Then A meghibásodott pumpa ismét működőképes lesz

  Scenario: Szerelők bővítik a csőhálózatot egy új csővel
    Given A városi ciszternák mellett találhatók szabad csövek
    When A szerelők illesztenek egy új csövet a csőhálózatba a ciszternához
    Then Az új cső segíti a hatékonyabb vízszállítást a városi ciszternákba

  Scenario: Szabotőrök kilyukasztanak egy fontos csövet
    Given A csőhálózatban található egy fontos cső
    When A szabotőrök kilyukasztják ezt a csövet
    Then Vízveszteség keletkezik, csökkentve a vízszállítás hatékonyságát

  Scenario: Szabotőrök beavatkoznak a működő pumpákba
    Given A csőhálózatban működő pumpák vannak
    When A szabotőrök módosítják a pumpák beállításait
    Then Csökkenti a vízszállítás hatékonyságát a csőhálózatban
