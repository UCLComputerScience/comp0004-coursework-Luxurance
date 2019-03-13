package uk.ac.ucl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class StatsView extends JFrame {
    private JPanel mainPanel;

    private JPanel labelPanel;

    private JLabel meanAgeLabel;

    private JLabel youngestLabel;

    private JLabel eldestLabel;

    private JButton closeButton;

    private Model model;

    private List<Patient> listToDisplay;

    public StatsView(Model model, List<Patient> listToDisplay){
        setTitle("Statistics");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension halfScreen = new Dimension();
        halfScreen.setSize((int)screenSize.getWidth()/4,(int)screenSize.getHeight()/4);
        setPreferredSize(halfScreen);
        setLocation((int)screenSize.getWidth()*3/8,(int)screenSize.getHeight()*3/8);

        this.model = model;
        this.listToDisplay = listToDisplay;

        createButton();
        createLabel();
        createPanel();

        add(mainPanel,BorderLayout.CENTER);

//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    private void createButton(){
        closeButton = new JButton("Close");
        closeButton.addActionListener((ActionEvent e) -> closeFrame());
    }

    private void createLabel(){
        youngestLabel = new JLabel("Youngest : " + model.getYoungest(listToDisplay));
        meanAgeLabel = new JLabel("Mean age : " + model.getMeanAge(listToDisplay));
        eldestLabel = new JLabel("Eldest : " + model.getEldest(listToDisplay));
    }

    private void createPanel(){
        labelPanel = new JPanel(new GridLayout(1,3));
        labelPanel.add(youngestLabel);
        labelPanel.add(meanAgeLabel);
        labelPanel.add(eldestLabel);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        mainPanel.add(closeButton,BorderLayout.SOUTH);
        mainPanel.add(labelPanel,BorderLayout.CENTER);
    }

    private void closeFrame(){
        setVisible(false);
    }
}
