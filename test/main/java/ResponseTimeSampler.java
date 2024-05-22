package main.java;

import main.java.Controll.Menu;
import main.java.Controll.ViewGame;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class ResponseTimeSampler extends AbstractJavaSamplerClient {
    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("button", "theme"); // Default to testing the theme button
        return params;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        String button = context.getParameter("button");
        SampleResult result = new SampleResult();

        try {
            // Indítsa el a ViewGame alkalmazást
            String[] args = {};
            ViewGame.main(args);
            Menu menu = ViewGame.getMenuInstance();
            menu.showMenu();


            if ("theme".equals(button)) {
                result.sampleStart(); // start timing
                // Téma gomb megnyomása
                JButton themeButton = menu.getThemeButton();
                simulateButtonClick(themeButton);
            } else if ("play".equals(button)) {
                result.sampleStart(); // start timing
                // Play gomb megnyomása
                JButton playButton = menu.getPlayButton();
                simulateButtonClick(playButton);
            } else if ("action".equals(button)) {
                // Action gombok végignyomása
                JButton playButton = menu.getPlayButton();
                simulateButtonClick(playButton);
                result.sampleStart(); // start timing
                ArrayList<JButton> actionButtons = (ArrayList<JButton>) menu.getActionButtons();
                for (JButton bt : actionButtons) {
                    simulateButtonClick(bt);
                }
            } else if ("stresstest".equals(button)) {
                // Action gombok végignyomása
                JButton playButton = menu.getPlayButton();
                simulateButtonClick(playButton);
                result.sampleStart(); // start timing
                ArrayList<JButton> actionButtons = (ArrayList<JButton>) menu.getActionButtons();
                for (int i = 0; i < 1000000; i++){
                    if (i % 100 == 0)
                        System.out.println(i);

                    for (int j = 0; j < 4; j++) {
                        simulateButtonClick(actionButtons.get(j));
                        sleep(1);
                    }
                }
            }

            result.sampleEnd(); // end timing
            result.setSuccessful(true);
            result.setResponseMessage("Button " + button + " clicked successfully.");
            result.setResponseCodeOK(); // 200 status code
        } catch (Exception e) {
            result.sampleEnd(); // end timing
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e.toString());
        }

        return result;
    }

    private void simulateButtonClick(JButton button) {
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, button.getActionCommand());
        for (ActionListener listener : button.getActionListeners()) {
            listener.actionPerformed(event);
        }
    }
}
