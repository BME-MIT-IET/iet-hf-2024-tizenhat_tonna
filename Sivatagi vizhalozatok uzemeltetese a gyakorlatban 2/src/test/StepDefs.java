import Controll.Controller;
import Fields.Field;
import Fields.Pipe;
import Players.Player;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefs {
    private final Map<Integer, Boolean> actionResults = new HashMap<>();

    @Given("A jatek inicializalasra kerult")
    public void gameInitialized() {
        Controller.load("src/testpalya.txt");

        try {
            Controller.Run(); // egyszer fut le, felépíti a pályát, utána a függvényeit kell majd hívni
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        Controller.create();
        Controller.SetActivePlayer(Controller.getAllPlayers().get(0));
    }

    @When("{string} mozog {string} re a {int} -s scenarioban")
    public void movePlayer(String playerName, String toWhere, Integer scenarioNumber) {
        Player player = (Player) Controller.objectNames.get(playerName);
        Field field = (Field) Controller.objectNames.get(toWhere);
        actionResults.put(scenarioNumber, player.move(field));
    }

    @Then("A művelet sikeresen végrehajtódik a\\(z) {int} -s scenarioban")
    public void actionSuccessful(int scenarioNumber) {
        assertTrue(actionResults.get(scenarioNumber));
    }

    @Then("A művelet végrehatása sikertelen a\\(z) {int} -s scenarioban")
    public void actionUnsuccessful(int scenarioNumber) {
        assertFalse(actionResults.get(scenarioNumber));
    }

    @When("{string} megjavitja a pumpat melyen jelenleg tartozkodik a {int} -s scenarioban")
    public void megjavitjaAPumpatMelyenJelenlegTartozkodikASScenarioban(String playerName, int scenarioNumber) {
        Player player = (Player) Controller.objectNames.get(playerName);
        actionResults.put(scenarioNumber, player.repair());
    }

    @When("A {string} tonkreteszi az elemet amin jelenleg tartozkodik a {int} -s scenarioban")
    public void saboteurBreaksField(String playerName, int scenarioNumber) {
        Player player = (Player) Controller.objectNames.get(playerName);
        actionResults.put(scenarioNumber, player.breakField());
    }

    @And("{string} a jelenlegi poziciojan a bemeneti csovet {string} re es kimeneti csovet {string} re csereli a {int} -s scenarioban")
    public void setCurrentPipeInOut(String playerName, String inPipe, String outPipe, int scenarioNumber) {
        Player player = (Player) Controller.objectNames.get(playerName);
        Pipe in = (Pipe) Controller.objectNames.get(inPipe);
        Pipe out = (Pipe) Controller.objectNames.get(outPipe);
        actionResults.put(scenarioNumber, player.set(in, out));
    }
}
