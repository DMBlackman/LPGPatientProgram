package com.declan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class pnlWellnessSummary {
    private JPanel contentPane;

    public JPanel pnl2;
    private JPanel pnlHeader;
    private JLabel lblHeaderTitle;
    private JTextField fldFirstName;
    private JTextField fldPrimaryPhysician;
    private JTextField fldInsuranceCompany;
    private JTextField fldInsuranceGroupNumber;
    private JTextField fldLastName;
    private JTextField fldInsuranceMemberNumber;
    private JButton btnControlPanel;
    private JButton btnPatientSearch;
    private JButton btnPatientSummary;
    private JButton btnAddUpdatePatient;
    private JButton btnWellnessCheckups;
    private JButton btnAddUpdateWellness;
    private JTextField fldAvgAge;
    private JTextField fldHeight;
    private JTextField fldWeight;
    private JTextField fldAvgBP;
    private JTextField fldAvgBPM;
    private JTextField fldAvgDiastolicBP;
    private JTextField fldAvgSystolicBP;
    private JList lstMedication;

    public pnlWellnessSummary(JPanel panel) {
        contentPane = panel;
        createActionListener();

    }

    //adds actionlisteners to components on form
    public void createActionListener() {
        //adds navigation to bottom buttons for other JPanels
        btnControlPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "1");
            }
        });
        btnPatientSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "2");
            }
        });
        btnPatientSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "3");
            }
        });
        btnAddUpdatePatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "4");
            }
        });
        btnWellnessCheckups.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "6");
            }
        });
        btnAddUpdateWellness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "7");
            }
        });
    }

    //populate Patient Summary when btnPopulate entered on pnlPatientSearch
    public void fillWellnessSummary(){
        Connection con = null;
        Statement statement1 = null;
        ResultSet resultSet1 = null;
        try {
            con = new Main().openConnection();
            String sqlString1 = "select * from customerinfo where id = " + Main.workingID;
            statement1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet1 = statement1.executeQuery(sqlString1);

            //load values in rs to fields
            while (resultSet1.next()) {
                fldFirstName.setText(resultSet1.getString(2));
                fldLastName.setText(resultSet1.getString(3));
                fldPrimaryPhysician.setText(resultSet1.getString(9));
                fldInsuranceCompany.setText(resultSet1.getString(10));
                fldInsuranceGroupNumber.setText(resultSet1.getString(11));
                fldInsuranceMemberNumber.setText(resultSet1.getString(12));
            }
            sqlString1 = "select * from wellnesscheckups where Id = " + Main.workingID + " order by Date DESC";
            resultSet1 = statement1.executeQuery(sqlString1);

            resultSet1.first();
            fldAvgAge.setText(resultSet1.getString("Age"));
            fldHeight.setText(resultSet1.getString("Height"));
            fldWeight.setText(resultSet1.getString("Weight"));

            sqlString1 = "select AVG(CAST(BloodPressureUp AS FLOAT)), AVG(CAST(BloodPressureDown AS FLOAT)) from wellnesscheckups where Id = " + Main.workingID + " order by Date DESC";
            resultSet1 = statement1.executeQuery(sqlString1);
            resultSet1.first();
            fldAvgBP.setText(resultSet1.getString(1)+"/"+
                    resultSet1.getString(2));

            sqlString1 = "select AVG(CAST(BPM AS FLOAT)), AVG(CAST(DiastolicBP AS FLOAT))," +
                    "AVG(CAST(SystolicBP AS FLOAT)) from wellnesscheckups where Id = " + Main.workingID + " order by Date DESC";
            resultSet1 = statement1.executeQuery(sqlString1);
            resultSet1.first();
            fldAvgBPM.setText(resultSet1.getString(1));
            fldAvgDiastolicBP.setText(resultSet1.getString(2));
            fldAvgSystolicBP.setText(resultSet1.getString(3));

        } catch (Exception b) {
            b.printStackTrace();
        } finally {
            try {
                statement1.close();
                resultSet1.close();
                con.close();
            } catch (Exception c) {
                c.printStackTrace();
            }
        }
        fillMedicationList();
    }

    private void fillMedicationList(){
        Connection con = null;
        Statement statement1 = null;
        ResultSet resultSet1 = null;
        try {
            con = new Main().openConnection();
            String sqlString1 = "select * from medications where Id = " + Main.workingID + " order by Medication ASC";
            statement1 = con.createStatement();
            resultSet1 = statement1.executeQuery(sqlString1);

            //load values in rs to fields
            DefaultListModel m = new DefaultListModel();
            while (resultSet1.next()) {
                String med = resultSet1.getString("Medication") + " - Dose: " +
                        resultSet1.getString("Dose");
                m.addElement(new Item(resultSet1.getInt(1), med));
            }
            lstMedication.setModel(m);
        } catch (Exception b) {
            b.printStackTrace();
        } finally {
            try {
                statement1.close();
                resultSet1.close();
                con.close();
            } catch (Exception c) {
                c.printStackTrace();
            }
        }
    }

    public void clearWellnessFields(){
            fldAvgAge.setText(" ");
            fldHeight.setText(" ");
            fldWeight.setText(" ");
            fldAvgBP.setText(" ");
            fldAvgBPM.setText(" ");
            fldAvgDiastolicBP.setText(" ");
            fldAvgSystolicBP.setText(" ");
            lstMedication.removeAll();
    }
}
