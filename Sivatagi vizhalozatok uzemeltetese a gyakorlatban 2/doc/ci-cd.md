# Vízhálózat játék CI-CD

Ez a dokumentum leírja a projekthez készített és felhasznált CI-CD rendszereket. A megvalósítás fő vektora a GitHub Actions volt.

## Részfeladatok
# 1. Maven beüzemelése
Az eredeti projekt Mavent használt a program fordításához és futtatásához. Ez többnyire működőképes volt, de a pom.xml fájlon változtatni kellett a kívánt hatás eléréséhez.

Fel kellett venni a maven compiler és jar pluginokat, ezek segítségével a projekt forráskódja fordítható, és további függőségektől mentes, önálló .jar fájlba csomagolható.
A kapott fájl bármilyen, a Java 11-es verzióját támogató környezetben futtatható, JDK telepítése nélkül, kizárólag a JRE használatával.

# 2. GitHub Actions
A GitHub Actions lehetővé teszi repository-k automata tesztelését, ellenőrzését, futtatását. A projekt másik egységében ezt a SonarCloud-hoz is kihasználtuk.

Az általam létrehozott action a következő lépéseket hajtja végre:
1. A legújabb stabil Ubuntu verzió használata
2. A projekt forráskódjának lemásolása
3. 11-es verziójú AdoptOpenJDK telepítése
4. A projekt buildelése a maven pom fájl segítségével, tesztekkel, tisztítással és csomagolással

Amennyiben ezek a lépések mind sikeresek, nagy bizonyossággal állíthatjuk, hogy a projekt futtatható a specifikált környezetben.
A sikert zöld pipa jelzi, a hibát piros X; ez utóbbi esetben értesítést kapunk a repository beállításai szerint.

Wendl Csongor (HZERYX) - 2024