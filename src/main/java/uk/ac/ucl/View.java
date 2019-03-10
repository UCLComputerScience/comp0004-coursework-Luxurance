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

    private JButton loadButton;

    private JButton saveButton;

    private Model model;

    private JFileChooser fileChooser;

    public View() {
        setTitle("Test");
        setLocation(600,400);
        this.model = new Model();

        createButton();
        createLabel();
        createPenal();

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createButton(){
        this.loadButton = new JButton("Load..");
        loadButton.addActionListener((ActionEvent e) -> loadFile());

        this.saveButton = new JButton("Save..");
        saveButton.addActionListener((ActionEvent e) -> saveFile());
    }

    private void createFileChooser(){
        this.fileChooser = new JFileChooser("/Users/Lance 1 2/IdeaProjects/cwBag/patient_data/patient_data");
    }

    private void createLabel(){
        this.label = new JLabel("test");
    }

    private void createPenal(){
        this.bottomPanel = new JPanel(new GridLayout(1,4));
        bottomPanel.add(loadButton);
        bottomPanel.add(saveButton);

        this.panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(60,80,0,80));
        panel.add(bottomPanel,BorderLayout.SOUTH);
        panel.add(label,BorderLayout.NORTH);
    }

    private void errorMessage(String text){
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void loadFile(){
        createFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        System.out.println(fileChooser.getAcceptAllFileFilter().toString());
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Only .csv file is allowed", "csv");
        fileChooser.addChoosableFileFilter(csvFilter);
        try {
            int operation = fileChooser.showOpenDialog(this);
            if (operation == JFileChooser.APPROVE_OPTION) {
                model.readFile(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        catch (IOException e1){
            errorMessage("Invalid format");
        }
        catch (ArrayIndexOutOfBoundsException e2){
            errorMessage("Invalid format");
        }
    }

    private void saveFile(){
        createFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Only .txt file is allowed", "txt");
        fileChooser.addChoosableFileFilter(txtFilter);
        try{
            int operation = fileChooser.showSaveDialog(this);
            if(operation == JFileChooser.APPROVE_OPTION){
                if(model.getPatientList() == null){
                    errorMessage("No patient is loaded");
                }
                else {
                    model.writeFile(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        }
        catch (IOException e1){
            errorMessage("Invalid file");
        }
        catch (IllegalAccessException e2) {
            errorMessage("Invalid file");
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new View());
    }
}
