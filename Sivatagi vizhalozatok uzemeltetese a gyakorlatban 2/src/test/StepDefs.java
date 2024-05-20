import Controll.Controller;
import Fields.Field;
import Players.Player;
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
}
