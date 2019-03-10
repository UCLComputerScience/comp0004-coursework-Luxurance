package uk.ac.ucl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class View extends JFrame {

    private JPanel panel;

    private JPanel buttonPanel;

    private JPanel listPanel;

    private JList patientListView;

    private JScrollPane nameScroller;

    private JLabel label;

    private JButton loadButton;

    private JButton saveButton;

    private Model model;

    private JFileChooser fileChooser;

    private FileNameExtensionFilter csvFilter;

    private FileNameExtensionFilter txtFilter;

    public View() {
        setTitle("Patient Data Manager");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
        setLocation((int)screenSize.getWidth()/2,(int)screenSize.getHeight()/2);
        this.model = new Model();

        createListView();
        createListPanel();
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

    private void createListView(){
        String[] arrayDisplayed = {};
        patientListView = new JList(arrayDisplayed);
    }

    private void createFileFilter(){
        csvFilter = new FileNameExtensionFilter(".csv CSV formatted", "csv");
        txtFilter = new FileNameExtensionFilter(".txt JSON formatted", "txt");
    }

    private void createFileChooser(){
        this.fileChooser = new JFileChooser("/Users/Lance 1 2/IdeaProjects/cwBag/patient_data/patient_data");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(csvFilter);
        fileChooser.addChoosableFileFilter(txtFilter);
    }

    private void createLabel(){
        this.label = new JLabel("test");
    }

    private void createPenal(){
        this.buttonPanel = new JPanel(new GridLayout(1,2));
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);

        this.panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(buttonPanel,BorderLayout.SOUTH);
        panel.add(label,BorderLayout.NORTH);
        panel.add(listPanel,BorderLayout.WEST);
    }

    private void createListPanel(){
        this.nameScroller = new JScrollPane(patientListView);

        this.listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        listPanel.add(nameScroller,BorderLayout.CENTER);
    }

    private void errorMessage(String text){
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void notificationMessage(String text){
        JOptionPane.showMessageDialog(this, text, "Done",JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadFile() {
        createFileFilter();
        createFileChooser();
        try {
            int operation = fileChooser.showOpenDialog(this);
            if (operation == JFileChooser.APPROVE_OPTION) {
                File fileSelected = fileChooser.getSelectedFile();
                if(fileSelected.getName().endsWith(".csv")) {
                    model.readCSVFile(fileSelected.getAbsolutePath());
                }
                if(fileSelected.getName().endsWith(".txt")){
                    model.readJSONFile(fileSelected.getAbsolutePath());
                }
                notificationMessage("Load successfully");
                patientListView.setListData(model.getPatientNamesList().toArray());
            }
        }
        catch (IOException e1){
            errorMessage("Invalid format");
        }
        catch (ArrayIndexOutOfBoundsException e2){
            errorMessage("Invalid format");
        }
        catch (IllegalAccessException e3){
            errorMessage("Read fails");
        }
    }

    private void saveFile(){
        createFileFilter();
        createFileChooser();
        try{
            int operation = fileChooser.showSaveDialog(this);
            if(operation == JFileChooser.APPROVE_OPTION){
                if(model.getPatientList() == null){
                    errorMessage("No patient is loaded");
                }
                else {
                    File fileSelected = fileChooser.getSelectedFile();
                    if(fileSelected.getName().endsWith(".txt")) {
                        model.writeJSONFile(fileChooser.getSelectedFile().getAbsolutePath());
                        notificationMessage("Save successfully");

                    }
                    if(fileSelected.getName().endsWith(".csv")){
                        model.writeCSVFile(fileSelected.getAbsolutePath(),model.getPatientList());
                        notificationMessage("Save successfully");
                    }
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
