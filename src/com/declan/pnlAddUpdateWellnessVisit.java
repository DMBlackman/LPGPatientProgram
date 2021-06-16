package com.declan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class pnlAddUpdateWellnessVisit {
    private JPanel contentPane;

    public JPanel pnl2;
    private JButton btnControlPanel;
    private JButton btnPatientSearch;
    private JButton btnPatientSummary;
    private JButton btnAddUpdatePatientInfo;
    private JButton btnWellnessSummary;
    private JPanel pnlHeader;
    private JLabel lblHeaderTitle;
    private JTextField fldFirstName;
    private JTextField fldAge;
    private JTextField fldLastName;
    private JTextField fldHeight;
    private JTextField fldPrimaryPhysician;
    private JTextField fldWeight;
    private JTextField fldBloodPressureUp;
    private JTextField fldDiastolicBP;
    private JTextField fldBPM;
    private JTextField fldSystolicBP;
    private JList lstMedication;
    private JComboBox cboDate;
    private JButton btnPopulate;
    private JButton btnLastWellnessCheckup;
    private JComboBox cboMedication;
    private JButton btnRemoveMedication;
    private JButton btnAddMedication;
    private JButton btnReadyAddNewRecord;
    private JButton btnReadyUpdateRecord;
    private JButton btnUpdateRecord;
    private JButton btnAddNewRecord;
    private JTextField fldMedicationName;
    private JTextField fldMedicationDose;
    private JTextField fldDate;
    private JTextField fldTime;
    private JTextField fldBloodPressureDown;
    private JButton btnUpdateExistingPatient;

    public pnlAddUpdateWellnessVisit(JPanel panel) {
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
        btnLastWellnessCheckup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "6");
            }
        });
        btnPopulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillUponDateSelection();
            }
        });
        btnRemoveMedication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removecboMedciation();
            }
        });
        btnAddMedication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewMedication();
            }
        });
        btnReadyUpdateRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnUpdateRecord.setEnabled(true);
            }
        });
        btnReadyAddNewRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddNewRecord.setEnabled(true);
            }
        });
        btnUpdateRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateWellnessVisit();
                btnUpdateRecord.setEnabled(false);
            }
        });
        btnAddNewRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewWellnessVisit();
                btnAddNewRecord.setEnabled(false);
            }
        });
    }
    //populate Patient Summary when btnPopulate entered on pnlPatientSearch
    public void fillAddUpdateWellness(){
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
            fldDate.setText(resultSet1.getString("Date"));
            fldTime.setText(resultSet1.getString("Time"));
            fldAge.setText(resultSet1.getString("Age"));
            fldHeight.setText(resultSet1.getString("Height"));
            fldWeight.setText(resultSet1.getString("Weight"));
            fldBloodPressureUp.setText(resultSet1.getString("BloodPressureUp"));
            fldBloodPressureDown.setText(resultSet1.getString("BloodPressureDown"));
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
        fillcboMedciation();
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
            fldDate.setText(resultSet1.getString("Date"));
            fldTime.setText(resultSet1.getString("Time"));
            fldAge.setText(resultSet1.getString("Age"));
            fldHeight.setText(resultSet1.getString("Height"));
            fldWeight.setText(resultSet1.getString("Weight"));
            fldBloodPressureUp.setText(resultSet1.getString("BloodPressureUp"));
            fldBloodPressureDown.setText(resultSet1.getString("BloodPressureDown"));
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
        fillcboMedciation();
        fillCboDate();
    }

    private void fillcboMedciation(){
        Connection con = null;
        Statement statement1 = null;
        ResultSet resultSet1 = null;
        try {
            con = new Main().openConnection();
            String sqlString1 = "select * from medications where Id = " + Main.workingID + " order by Medication ASC";
            statement1 = con.createStatement();
            resultSet1 = statement1.executeQuery(sqlString1);

            //load values in rs to fields
            cboMedication.removeAllItems();
            while (resultSet1.next()) {
                String med = resultSet1.getString("Medication") + " - Dose: " +
                        resultSet1.getString("Dose");
                cboMedication.addItem(new Item(resultSet1.getInt(1), med));
            }
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

    private void removecboMedciation(){
        Connection con = null;
        Statement statement1 = null;
        try {
            Item medId = (Item) cboMedication.getSelectedItem();
            int mId = medId.getId();
            con = new Main().openConnection();
            String remove = "Delete from medications where idmedications =" + mId;
            statement1 = con.createStatement();
            statement1.executeUpdate(remove);
        } catch (Exception b) {
            b.printStackTrace();
        } finally {
            try {
                statement1.close();
                con.close();
            } catch (Exception c) {
                c.printStackTrace();
            }
        }
        fillcboMedciation();
        Main.panelWellnessSummary.fillWellnessSummary();
        Main.panelWellnessLastCheckup.fillWellnessLastCheckup();
    }

    private void addNewMedication(){
        Connection con = null;
        Statement statement1 = null;
        try {
            con = new Main().openConnection();
            String sqlString2 = "INSERT INTO medications (Id, Medication, Dose) VALUES ('" + Main.workingID + "', '" +
                    fldMedicationName.getText() + "', '" + fldMedicationDose.getText() + "')";
            statement1 = con.createStatement();
            statement1.executeUpdate(sqlString2);
        } catch (Exception b) {
            b.printStackTrace();
        } finally {
            try {
                statement1.close();
                con.close();
            } catch (Exception c) {
                c.printStackTrace();
            }
        }
        fillcboMedciation();
        Main.panelWellnessSummary.fillWellnessSummary();
        Main.panelWellnessLastCheckup.fillWellnessLastCheckup();
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
    public void updateWellnessVisit(){
        Connection con = null;
        Statement statement1 = null;
        try {
            con = new Main().openConnection();
            //update selected patient info based on field value text
            Item dateItem = (Item) cboDate.getSelectedItem();
            int checkupId = dateItem.getId();

            String sqlString1 = "Update wellnesscheckups " +
                    "SET Age = '" + fldAge.getText() +
                    "', Height = '" + fldHeight.getText() +
                    "', Weight = '" + fldWeight.getText() +
                    "', BloodPressureUp = '" + fldBloodPressureUp.getText() +
                    "', BloodPressureDown = '" + fldBloodPressureDown.getText() +
                    "', BPM = '" + fldBPM.getText() +
                    "', DiastolicBP = '" + fldDiastolicBP.getText() +
                    "', SystolicBP = '" + fldSystolicBP.getText() +
                    "' WHERE WellnessId = " + checkupId + " and Date = '" + fldDate.getText() +
                    "' and Time = " + fldTime.getText();
            statement1 = con.createStatement();
            statement1.executeUpdate(sqlString1);


        } catch (Exception b) {
            b.printStackTrace();
        } finally {
            try {
                statement1.close();
                con.close();
            } catch (Exception c) {
                c.printStackTrace();
            }
        }
        btnUpdateRecord.setEnabled(false);
        Main.panelPatientSummaryInfo.fillPatientSummary();
        Main.panelWellnessSummary.fillWellnessSummary();
        Main.panelWellnessLastCheckup.fillWellnessLastCheckup();
    }
    public void addNewWellnessVisit(){
        Connection con = null;
        Statement statement1 = null;
        try {
            con = new Main().openConnection();
            //add selected patient info based on field value text
            String sqlString1 = "INSERT INTO wellnesscheckups(Id, Date, Time, Age, Height, Weight, BloodPressureUp, " +
                    "BloodPressureDown, BPM, DiastolicBP, SystolicBP) VALUES('" + Main.workingID + "', '" +
                    fldDate.getText() + "', '" + fldTime.getText() + "', '" + fldAge.getText() + "', '" + fldHeight.getText()
                    + "', '" + fldWeight.getText() + "', '" + fldBloodPressureUp.getText() + "', '" +
                    fldBloodPressureDown.getText() + "', '" + fldBPM.getText() + "', '" +
                    fldDiastolicBP.getText() + "', '" + fldSystolicBP.getText() + "')";
            statement1 = con.createStatement();
            statement1.executeUpdate(sqlString1);
        } catch (Exception b) {
            b.printStackTrace();
        } finally {
            try {
                statement1.close();
                con.close();
            } catch (Exception c) {
                c.printStackTrace();
            }
        }
        btnAddNewRecord.setEnabled(false);
        fillCboDate();
        Main.panelPatientSummaryInfo.fillPatientSummary();
        Main.panelWellnessSummary.fillWellnessSummary();
        Main.panelWellnessLastCheckup.fillWellnessLastCheckup();
    }
    public void clearAllFields(){
            fldDate.setText(" ");
            fldTime.setText(" ");
            fldAge.setText(" ");
            fldHeight.setText(" ");
            fldWeight.setText(" ");
            fldBloodPressureUp.setText(" ");
            fldBloodPressureDown.setText(" ");
            fldBPM.setText(" ");
            fldDiastolicBP.setText(" ");
            fldSystolicBP.setText(" ");
            fldMedicationName.setText(" ");
            fldMedicationDose.setText(" ");
            cboDate.removeAllItems();
            cboMedication.removeAllItems();
    }

}
