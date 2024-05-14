import Controll.Controller;
import Fields.Field;
import Players.Player;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefs {
    @Given("^A szerelő csapat egyik játékosa egy pumpán áll$")
    public void mechanicIsStandingOnAPump() {
    }

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

    @When("{string} mozog {string} re")
    public void movePlayer(String playerName, String toWhere) {
        Player player = (Player) Controller.objectNames.get(playerName);
        Field field = (Field) Controller.objectNames.get(toWhere);
        assertEquals(true, player.move(field));
    }
}
