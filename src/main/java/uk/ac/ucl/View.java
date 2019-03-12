package uk.ac.ucl;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class View extends JFrame {

    private JPanel panel;

    private JPanel buttonPanel;

    private JPanel listPanel;

    private JPanel infoPanel;

    private JPanel searchPanel;

    private JTextField searchField;

    private JList patientListView;

    private JList infoListView;

    private JScrollPane nameScroller;

    private JScrollPane infoScroller;

    private JLabel searchLabel;

    private JLabel listLabel;

    private JLabel infoLabel;

    private JButton loadButton;

    private JButton saveButton;

    private JButton searchButton;

    private JButton statsButtons;

    private Model model;

    private JFileChooser fileChooser;

    private FileNameExtensionFilter csvFilter;

    private FileNameExtensionFilter txtFilter;

    private List<Patient> listToDisplay;

    public View() {
        setTitle("Patient Data Manager");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension halfScreen = new Dimension();
        halfScreen.setSize((int)screenSize.getWidth()/2,(int)screenSize.getHeight()/2);
        setPreferredSize(halfScreen);
        setLocation((int)screenSize.getWidth()/4,(int)screenSize.getHeight()/4);
        this.model = new Model();
        this.listToDisplay = null;

        createListView();
        createTextField();
        createLabel();
        createListPanel();
        createButton();
        createPenal();

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createButton(){
        loadButton = new JButton("Load..");
        loadButton.addActionListener((ActionEvent e) -> loadFile());

        saveButton = new JButton("Save..");
        saveButton.addActionListener((ActionEvent e) -> saveFile());

        searchButton = new JButton("Match");
        searchButton.addActionListener((ActionEvent e) -> applySearch());

        statsButtons = new JButton("Stats.");
        statsButtons.addActionListener((ActionEvent e) -> showStats());

    }

    private void createListView(){
//        String[] arrayDisplayed = {"          No patient is loaded yet.         "};
        patientListView = new JList();
        patientListView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientListView.addListSelectionListener((ListSelectionEvent e) -> showInfo(e));

        infoListView = new JList();
    }

    private void createTextField(){
        searchField = new JTextField();

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
        searchLabel = new JLabel("Search:");
        listLabel = new JLabel("Patients:");
        infoLabel = new JLabel("Personal Information:");
    }

    private void createPenal(){
        buttonPanel = new JPanel(new GridLayout(1,3));
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(statsButtons);

        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchButton,BorderLayout.EAST);

        panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(buttonPanel,BorderLayout.SOUTH);
        panel.add(listPanel,BorderLayout.WEST);
        panel.add(infoPanel,BorderLayout.CENTER);
        panel.add(searchPanel,BorderLayout.NORTH);
    }

    private void createListPanel(){
        nameScroller = new JScrollPane(patientListView);
        infoScroller = new JScrollPane(infoListView);

        listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(15,10,15,30));
        listPanel.add(nameScroller,BorderLayout.CENTER);
        listPanel.add(listLabel,BorderLayout.NORTH);

        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15,0,15,10));
        infoPanel.add(infoScroller,BorderLayout.CENTER);
        infoPanel.add(infoLabel,BorderLayout.NORTH);
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
                listToDisplay = model.getPatientList();
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
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int operation = fileChooser.showSaveDialog(this);
            if(operation == JFileChooser.APPROVE_OPTION){
                if(model.getPatientList() == null){
                    errorMessage("No patient is loaded");
                }
                else {
                    File fileSelected = fileChooser.getSelectedFile();
                    String filterName = fileChooser.getFileFilter().getDescription();
                    if(filterName.contains("txt")) {
                        model.writeJSONFile(fileSelected.getAbsolutePath());
                    }
                    if(filterName.contains("csv")){
                        model.writeCSVFile(fileSelected.getAbsolutePath(),model.getPatientList());
                    }
                    notificationMessage("Save successfully");
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

    private void showInfo(ListSelectionEvent e){
        try {
            JList source = (JList)e.getSource();
            int indexSelected = source.getSelectedIndex();
            if(indexSelected == -1){
                return;
            }
            infoListView.setListData(model.getPatientInfo(listToDisplay.get(indexSelected)).toArray());
        }
        catch (IllegalAccessException error){
            errorMessage("Cannot find the personal information");
        }
    }

    private void applySearch(){
        try {
            String keyword = searchField.getText();
            listToDisplay = model.searchPatient(keyword);
            patientListView.setListData(model.getPatientNamesList(listToDisplay).toArray());
        }
        catch(NullPointerException e){
            errorMessage("Patient not found");
        }
    }

    private void showStats(){
        if(listToDisplay == null || listToDisplay.size() == 0){
            errorMessage("No patient");
            return;
        }
        SwingUtilities.invokeLater(() -> new StatsView(model,listToDisplay));
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new View());
    }
}
