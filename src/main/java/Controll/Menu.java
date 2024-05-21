package Controll;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;


public class Menu extends JFrame implements ActionListener {
    private String currentTheme;
    private JButton newGame;
    private JButton exitGame;
    private JButton theme;
    private JComboBox<Integer> mechanics;
    private JComboBox<Integer> saboteurs;
    private JTextField mechanic;
    private JTextField saboteur;

    /**
     * konstruktor
     * @param name az ablak neve
     * @param t a játék témája
     */
    public Menu(String name, String t) {
        currentTheme = t;
        setTitle(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setBounds(700, 250, 500, 500);

        newGame = new JButton("New Game");
        newGame.setBounds(140, 95, 200, 60);
        newGame.addActionListener(this);

        exitGame = new JButton("Exit");
        exitGame.setBounds(140, 300, 200, 60);
        exitGame.addActionListener(this);

        theme = new JButton("Change theme");
        theme.setBounds(140, 225, 200, 60);
        theme.addActionListener(this);

        add(newGame);
        add(exitGame);
        add(theme);

        Integer[] numOfMechanics = {2, 3, 4, 5, 6, 7, 8, 9};
        mechanics = new JComboBox<Integer>(numOfMechanics);
        mechanics.setBounds(300, 165, 40, 20);

        Integer[] numOfSaboteurs = {2, 3, 4, 5, 6, 7, 8, 9};
        saboteurs = new JComboBox<Integer>(numOfSaboteurs);
        saboteurs.setBounds(300, 195, 40, 20);

        add(mechanics);
        add(saboteurs);

        mechanic = new JTextField("Number of mechanics:");
        mechanic.setBounds(140, 165, 160, 20);
        mechanic.setEditable(false);

        saboteur = new JTextField("Number of saboteurs:");
        saboteur.setBounds(140, 195, 160, 20);
        saboteur.setEditable(false);

        add(mechanic);
        add(saboteur);

        if (currentTheme.equals("Black")) changeTheme(currentTheme);
        setVisible(false);
    }

    /**
     * láthatóvá válik a menü
     */
    public void showMenu() {
        setVisible(true);
    }

    /**
     * sötét téma be/ki kapcsolása
     */
    public void changeTheme(String s) {
        if (s.equals("White")) {
            currentTheme = "Black";
            getContentPane().setBackground(Color.darkGray);
            newGame.setBackground(Color.gray);
            exitGame.setBackground(Color.gray);
            theme.setBackground(Color.gray);
            mechanics.setBackground(Color.gray);
            saboteurs.setBackground(Color.gray);
            mechanic.setBackground(Color.gray);
            saboteur.setBackground(Color.gray);
        }
        else {
            currentTheme = "White";
            getContentPane().setBackground(null);
            newGame.setBackground(null);
            exitGame.setBackground(null);
            theme.setBackground(null);
            mechanics.setBackground(null);
            saboteurs.setBackground(null);
            mechanic.setBackground(null);
            saboteur.setBackground(null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // új játék elkezdése a kiválasztott adatokkal
        if (e.getSource() == newGame) {

            Controller.load("src/palya.txt");


            if ((int)mechanics.getSelectedItem() > 2) {
                for (int i = 3; i <= (int)mechanics.getSelectedItem(); i++) {
                    Controller.commandList.add("mechanic Mec" + i + " E");
                    Controller.commandList.add("addplayer E Mec" + i);
                }
            }
            if ((int)saboteurs.getSelectedItem() > 2) {
                for (int i = 3; i <= (int)saboteurs.getSelectedItem(); i++) {
                    Controller.commandList.add("saboteur Sab" + i + " D");
                    Controller.commandList.add("addplayer D Sab" + i);
                }
            }
            try {
                Controller.Run(); // egyszer fut le, felépíti a pályát, utána a függvényeit kell majd hívni
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            Controller.create();
            Controller.SetActivePlayer(Controller.getAllPlayers().get(0));
            this.dispose();
            ViewGame vg = new ViewGame();
            vg.setBackgroundColor(currentTheme);
        }

        // téma változtatása
        if (e.getSource() == theme) {
            changeTheme(currentTheme);
        }

        // kilépés a játékból
        if (e.getSource() == exitGame) {
            System.exit(0);
        }
    }
}