package fr.limayrac.b3rpi;

import fr.limayrac.b3rpi.util.Fichier;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Start extends JFrame {

    int size = 0;
    Fichier f = new Fichier();
    private JPanel[][] gridPanels;

    private JLabel[][] lettre;
    private int rows; // Previously width
    private int columns; // Previously height
    private static final int CELL_SIZE = 70;

    public Start() {
        setTitle("Mot Fléché");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getGridDimensions();
        initializeGrid();

        setLayout(new GridLayout(rows, columns)); // Set GridLayout for the main panel
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void getGridDimensions() {
        do {
            rows = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrez le nombre de lignes (1 à 10):"));
            columns = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrez le nombres de colonnes (1 à 10):"));
            if (rows <= 0 || columns <= 0 || rows > 10 || columns > 10) {
                JOptionPane.showMessageDialog(null, "Invalid grid dimensions. Please enter values between 1 and 10.");
            } else {
                break;
            }
        } while (true);
    }

    private void initializeGrid() {
        gridPanels = new JPanel[rows][columns];
        lettre = new JLabel[rows][columns];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                gridPanels[x][y] = new JPanel();
                gridPanels[x][y].setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                gridPanels[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                lettre[x][y] = new JLabel(" ");
                gridPanels[x][y].add(lettre[x][y]);
                gridPanels[x][y].addMouseListener(new PanelMouseListener(x, y));
                add(gridPanels[x][y]);
            }
        }
    }


    private class PanelMouseListener extends MouseAdapter {
        private final int x;
        private final int y;

        public PanelMouseListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String direction() {
            String[] optionsToChoose = {"DOWN", "RIGHT", "DOWNRIGHT", "RIGHTDOWN"};
            int right = y;
            int down = x;
            size = 0;
            String getDir = (String) JOptionPane.showInputDialog(
                    null,
                    "Dans quel direction le mot doit s'afficher",
                    "Choix direction",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    optionsToChoose,
                    optionsToChoose[0]);
            if (Objects.equals(getDir, optionsToChoose[0])) {
                while (down < rows - 1) {
                    size++;
                    down++;
                }
            }
            if (Objects.equals(getDir, optionsToChoose[2])) {
                while (right < rows) {
                    size++;
                    right++;
                }
            }
            if (Objects.equals(getDir, optionsToChoose[1])) {
                while (right < columns - 1) {
                    size++;
                    right++;
                }
            }
            if (Objects.equals(getDir, optionsToChoose[3])) {
                while (down < columns) {
                    size++;
                    down++;
                }
            }
            return getDir;
        }

        public String definition(){
            String[] optionsToChoose = {"Simple", "Double"};
            String definitionType = (String) JOptionPane.showInputDialog(
                    null,
                    "Quel type de définition voulez-vous ajouter ?",
                    "Type de définition",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    optionsToChoose,
                    optionsToChoose[0]);

            if (Objects.equals(definitionType, optionsToChoose[0])) {
                // Simple definition
                // Demander la définition et l'afficher sur la case
                String definition = JOptionPane.showInputDialog("Entrez la définition :");
                JLabel def = new JLabel(definition);

                gridPanels[x][y].add(def);
                gridPanels[x][y].setEnabled(false); // Rendre la case non cliquable

            } else if (Objects.equals(definitionType, optionsToChoose[1])) {
                // Double definition
                String definition1 = JOptionPane.showInputDialog("Entrez la première définition :");
                String definition2 = JOptionPane.showInputDialog("Entrez la deuxième définition :");
                JLabel def1 = new JLabel(definition1);
                JLabel def2 = new JLabel(definition2);

                JPanel mainPanel = new JPanel(new GridLayout(2, 1));
                JPanel topPanel = new JPanel();
                topPanel.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE/2));
                topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                topPanel.add(def1);

                JPanel botPanel = new JPanel();
                botPanel.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE/2));
                botPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                botPanel.add(def2);

                mainPanel.add(topPanel);
                mainPanel.add(botPanel);

                gridPanels[x][y].removeAll();
                gridPanels[x][y].add(mainPanel);
                gridPanels[x][y].setEnabled(false);
            }
            return definitionType;
        }
        public void afficherMot(){
            String input;
            input = JOptionPane.showInputDialog("Enter a letter:");
            String direction = direction();
            f.printMotsDebut(input, size, x, y, direction, rows, columns, gridPanels , lettre);
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(f.mots.toArray(new String[0]));
            JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
            JOptionPane.showMessageDialog(null, comboBox, "Mots", JOptionPane.QUESTION_MESSAGE);
            String selectedValue = (String) comboBox.getSelectedItem();
            char[] lettres = selectedValue.toCharArray();
            int down = x + 1;
            int right = y + 1;
            int downright = y;
            int rightdown = x;
            if (direction.equals("DOWN")) {
                for (char c : lettres) {
                    lettre[down][y].setText(String.valueOf(c));
                    gridPanels[down][y].add(lettre[down][y]);
                    down++;
                }
            }
            if (direction.equals("RIGHT")) {
                for (char c : lettres) {
                    lettre[x][right].setText(String.valueOf(c));
                    gridPanels[x][right].add(lettre[x][right]);
                    right++;
                }
            }
            if (direction.equals("DOWNRIGHT")) {
                for (char c : lettres) {
                    lettre[down][downright].setText(String.valueOf(c));
                    gridPanels[down][downright].add(lettre[down][downright]);
                    downright++;
                }
            }
            if (direction.equals("RIGHTDOWN")) {
                for (char c : lettres) {
                    lettre[rightdown][right].setText(String.valueOf(c));
                    gridPanels[rightdown][right].add(lettre[rightdown][right]);
                    rightdown++;
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            String def = definition();
            if(Objects.equals(def, "Double")){
                afficherMot();
                afficherMot();
            }else{
                afficherMot();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Start::new);
    }
}
