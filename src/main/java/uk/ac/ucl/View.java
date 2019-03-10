package uk.ac.ucl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class View extends JFrame {
    private JPanel panel;

    private JPanel bottomPanel;

    private JLabel label;

    private JButton button;

    private JButton testButton;

    private Model model;

    private JFileChooser fileChooser;

    public View() {
        setTitle("Test");
        setLocation(600,400);
        this.model = new Model();

        createButton();
        createLabel();
        createPenal();

        this.fileChooser = new JFileChooser("/Users/Lance 1 2/IdeaProjects/cwBag/patient_data/patient_data");

        panel.setBorder(BorderFactory.createEmptyBorder(60,80,0,80));
        bottomPanel.add(button);
        bottomPanel.add(testButton);
        panel.add(bottomPanel,BorderLayout.SOUTH);
        panel.add(label,BorderLayout.NORTH);


        button.addActionListener((ActionEvent e) -> loadFile());

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createButton(){
        this.button = new JButton("Load..");
        this.testButton = new JButton("Test");
    }

    private void createLabel(){
        this.label = new JLabel("test");
    }

    private void createPenal(){
        this.panel = new JPanel(new BorderLayout());
        this.bottomPanel = new JPanel(new GridLayout(1,4));
    }

    public void loadFile(){
        try {
            fileChooser.setAcceptAllFileFilterUsed(false);

            FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Only .csv file is allowed", "csv");
            fileChooser.addChoosableFileFilter(csvFilter);

            int operation = fileChooser.showOpenDialog(null);
            if (operation == JFileChooser.APPROVE_OPTION) {
                model.readFile(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        catch (IOException e1){
            System.out.println("Invalid file.");
        }
        catch (ArrayIndexOutOfBoundsException e2){
            System.out.println("Invalid-format file.");
        }

    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new View());
    }
}
