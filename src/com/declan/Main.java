package com.declan;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class Main {

    private JPanel contPanel;
    static public pnlControlPanel panelControl;
    static public pnlSearchPatient panelPatientSearch;
    static public pnlPatientSummaryInfo panelPatientSummaryInfo;
    static public pnlAddUpdatePatientInfo panelAddUpdatePatientInfo;
    static public pnlWellnessSummary panelWellnessSummary;
    static public pnlWellnessLastCheckup panelWellnessLastCheckup;
    static public pnlAddUpdateWellnessVisit panelAddUpdateWellnessVisit;
    static public int workingID = 0;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().displayGUI();
            }
        });
    }

    //create frame and add panels
    public void displayGUI() {
        contPanel = new JPanel();
        contPanel.setLayout(new CardLayout());

        panelControl = new pnlControlPanel(contPanel);
        panelPatientSearch = new pnlSearchPatient(contPanel);
        panelPatientSummaryInfo = new pnlPatientSummaryInfo(contPanel);
        panelAddUpdatePatientInfo = new pnlAddUpdatePatientInfo(contPanel);
        panelWellnessSummary = new pnlWellnessSummary(contPanel);
        panelWellnessLastCheckup = new pnlWellnessLastCheckup(contPanel);
        panelAddUpdateWellnessVisit = new pnlAddUpdateWellnessVisit(contPanel);


        contPanel.add(panelControl.panelOne,"1");
        contPanel.add(panelPatientSearch.pnl2, "2");
        contPanel.add(panelPatientSummaryInfo.pnl2, "3");
        contPanel.add(panelAddUpdatePatientInfo.pnl2, "4");
        contPanel.add(panelWellnessSummary.pnl2, "5");
        contPanel.add(panelWellnessLastCheckup.pnl2, "6");
        contPanel.add(panelAddUpdateWellnessVisit.pnl2, "7");

        JFrame frame = new JFrame("Card Layout Demo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(contPanel);
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.pack();
        frame.setVisible(true);
    }

    //open DB connection
    public Connection openConnection(){
        Connection connect = null;
                try {
                    connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingdb", "root", "password");
                    //JOptionPane.showMessageDialog(null, "Connection Open");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            return connect;
        }




}
