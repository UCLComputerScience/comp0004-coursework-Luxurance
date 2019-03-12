package uk.ac.ucl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class StatsView extends JFrame {
    private JPanel mainPanel;

    private JLabel meanAgeLabel;

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
        meanAgeLabel = new JLabel("Mean age : " + model.getMeanAge(listToDisplay));
    }

    private void createPanel(){
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        mainPanel.add(closeButton,BorderLayout.SOUTH);
        mainPanel.add(meanAgeLabel,BorderLayout.CENTER);
    }

    private void closeFrame(){
        setVisible(false);
    }
}
