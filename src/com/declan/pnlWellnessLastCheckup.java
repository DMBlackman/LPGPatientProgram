package com.declan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class pnlWellnessLastCheckup {
    private JPanel contentPane;

    public JPanel pnl2;
    private JPanel pnlHeader;
    private JLabel lblHeaderTitle;
    private JTextField fldFirstName;
    private JTextField fldAge;
    private JTextField fldLastName;
    private JButton btnControlPanel;
    private JButton btnPatientSummary;
    private JButton btnPatientSearch;
    private JButton btnAddUpdatePatientInfo;
    private JButton btnWellnessSummary;
    private JButton btnAddUpdateWellnessCheckups;
    private JTextField fldPrimaryPhysician;
    private JTextField fldHeight;
    private JTextField fldWeight;
    private JTextField fldBloodPressure;
    private JTextField fldBPM;
    private JTextField fldDiastolicBP;
    private JTextField fldSystolicBP;
    private JList lstMedication;
    private JComboBox cboDate;
    private JButton btnPopulate;

    public pnlWellnessLastCheckup(JPanel panel) {
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
        btnAddUpdatePatientInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "4");
            }
        });
        btnWellnessSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "5");
            }
        });
        btnAddUpdateWellnessCheckups.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "7");
            }
        });
        btnPopulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillUponDateSelection();
            }
        });
    }

    //populate Patient Summary when btnPopulate entered on pnlPatientSearch
    public void fillWellnessLastCheckup(){
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
            }
            sqlString1 = "select * from wellnesscheckups where Id = " + Main.workingID + " order by Date DESC";
            resultSet1 = statement1.executeQuery(sqlString1);

            resultSet1.first();
            fldAge.setText(resultSet1.getString("Age"));
            fldHeight.setText(resultSet1.getString("Height"));
            fldWeight.setText(resultSet1.getString("Weight"));
            fldBloodPressure.setText(resultSet1.getString("BloodPressureUp")+"/"+
                    resultSet1.getString("BloodPressureDown"));
            fldBPM.setText(resultSet1.getString("BPM"));
            fldDiastolicBP.setText(resultSet1.getString("DiastolicBP"));
            fldSystolicBP.setText(resultSet1.getString("SystolicBP"));

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
        fillCboDate();
    }

    public void fillUponDateSelection(){
        Connection con = null;
        Statement statement1 = null;
        ResultSet resultSet1 = null;
        try {
            Item wItem = (Item) cboDate.getSelectedItem();
            int wellnessId = wItem.getId();
            con = new Main().openConnection();
            String sqlString1 = "select * from wellnesscheckups where WellnessId = " + wellnessId + " order by Date DESC";
            statement1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet1 = statement1.executeQuery(sqlString1);

            resultSet1.first();
            fldAge.setText(resultSet1.getString("Age"));
            fldHeight.setText(resultSet1.getString("Height"));
            fldWeight.setText(resultSet1.getString("Weight"));
            fldBloodPressure.setText(resultSet1.getString("BloodPressureUp")+"/"+
                    resultSet1.getString("BloodPressureDown"));
            fldBPM.setText(resultSet1.getString("BPM"));
            fldDiastolicBP.setText(resultSet1.getString("DiastolicBP"));
            fldSystolicBP.setText(resultSet1.getString("SystolicBP"));

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
        fillCboDate();
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

    //populate combobox on load. called in constructor
    public void fillCboDate(){
        Connection con = null;
        Statement statement1 = null;
        ResultSet resultSet1 = null;
        cboDate.removeAllItems();
        try{
            con = new Main().openConnection();
            String sqlString1 = "select WellnessId, Date from wellnesscheckups where Id = " + Main.workingID + " order by Date DESC";
            statement1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet1 = statement1.executeQuery(sqlString1);

            while (resultSet1.next()) {

                String name = resultSet1.getString(2);
                int uniqueId = resultSet1.getInt(1);
                cboDate.addItem(new Item(uniqueId, name));
            }
        }catch (Exception b){
            b.printStackTrace();
        }finally {
            try {
                statement1.close();
                resultSet1.close();
                con.close();
            }catch (Exception c){
                c.printStackTrace();
            }
        }
    }

    public void clearWellnessFields(){
            fldAge.setText(" ");
            fldHeight.setText(" ");
            fldWeight.setText(" ");
            fldBloodPressure.setText(" ");
            fldBPM.setText(" ");
            fldDiastolicBP.setText(" ");
            fldSystolicBP.setText(" ");
            lstMedication.removeAll();
    }
}
