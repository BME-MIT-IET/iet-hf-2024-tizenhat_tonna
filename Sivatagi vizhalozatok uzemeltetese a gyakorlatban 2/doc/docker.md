# Vízhálózat játék deployment (Docker)

Ez a dokumentum leírja a projekthez készített és felhasznált konténerizációs és deploy megoldásokat. A megvalósítás fő vektora a Docker volt.

## Részfeladatok
# 1. Dockerfile készítése
A dockerfile írja le, hogy egy adott docker image elkészítéséhez milyen lépéseket kell végrehajtani. Ebben az esetben a lépések a következők:
- standard docker maven+openjdk11 image használatával:
  - a projekt forráskódjának lemásolása, buildelése
- standard docker openjdk11 image használatával:
  - xauth és egyéb függőségek telepítése (lásd lejjebb: X11)
  - X11 kijelző célpont beállítása (docker virtuális)
  - kész .jar és adatfájlok másolása
  - VNC port nyitása
  - jar file futtatása
# 2. Docker image futtatása
A docker image buildelhető így:
docker build -t IMAGE_NÉV .
A docker image futtatása a következő paranccsal történik:
docker run -e DISPLAY=host.docker.internal:0.0 --name CONTAINER_NÉV IMAGE_NÉV

A program ezután natív ablakként jelenik meg a host gépen és játszható.

# Információ: X11
Az X11 kijelzőszerver Unix rendszereken gyári felszereltségnek tekinthető, viszont Windows esetében ez nem adott. A program csakis grafikus felülettel rendelkezik, így a futtatáshoz elengedhetetlen az X11 szerver megléte.
Windowson erre például az Xming alkalmas, telepítése és futtatása elegendő. A dockerfile beállítja a megfelelő dockeres virtuális kijelzőt, ennek eredményeként a játék natív Windows ablakként jelenik meg és játszható.

Wendl Csongor (HZERYX) - 2024