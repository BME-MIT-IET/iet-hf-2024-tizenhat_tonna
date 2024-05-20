# Vízhálózat Játék BDD Dokumentáció

Ez a dokumentáció leírja a "Sivatagi vízhálózat" nevű játék funkcióit és működését BDD (Behavior-Driven Development) forgatókönyvek segítségével.

A BDD tesztek megvalósításához a [Cucumber](https://cucumber.io/) keretrendszert használjuk. Melyhez egy feature file-t és egy step definition osztály-t kell létrehozni a projekt test könyvtárában. 
Ezt követően a featurefileban megírva bárki számára olvasható formátumban a teszteket és az ezekhez tartozó step definitionöket a Given, When és Then annotationöket használva már futtathatjuk is a teszteket.
A Step Definition osztályban a tesztekhez tartozó metódusokban kell megvalósítani a tesztek lépéseit a valós programmal.

## Scenariok:
## 1. A játékosok mozognak a csöveken

### Scenario: Játékos mozgatása egy pumpáról üres csőre.
A szerelő csapat egyik játékosa az egyik egy pumpáról egy szomszédos csőre lép melyen nem tartózkodik másik játékos. A műveletet sikeresen végrehajtja.

### Scenario: Játékos mozgatása egy pumpáról már foglalt csőre.
A szerelő csapat egyik játékosa az egyik szerelővel egy pumpáról egy szomszédos csőre lép melyen jelenleg tartózkodik egy másik játékos. A műveletet végrehajtása sikertelen.

### Scenario: Játékos mozgatása egy csőröl szomszédos pumpára melyen már tartózkodik játékos.
A szerelő csapat egyik játékosa az egyik szerelővel egy csőröl egy szomszédos pumpára lép melyen jelenleg tartózkodik egy másik játékos. A műveletet végrehajtása sikeres.

## 2. Szerelők a Vízhálózat Javítása

### Scenario: Szerelők megjavítják a meghibásodott pumpát
A játékban egy meghibásodott pumpa van a csőhálózatban. A szerelőknek lehetőségük van megjavítani ezt a pumpát.

## 3. Szabotőrök a Vízhálózat Károsítása

### Scenario: Szabotőrök kilyukasztanak egy fontos csövet
A csőhálózatban található egy fontos cső, amit a szabotőrök kilyukasztanak. Ennek következtében vízveszteség keletkezik, csökkentve a vízszállítás hatékonyságát.

### Scenario: Szabotőrök beavatkoznak a működő pumpákba
A szabotőrök módosítják a működő pumpák beállításait, hogy csökkentsék a vízszállítás hatékonyságát a csőhálózatban.