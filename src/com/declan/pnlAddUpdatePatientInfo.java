package com.declan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class pnlAddUpdatePatientInfo {
    private JPanel contentPane;

    public JPanel pnl2;
    private JPanel pnlHeader;
    private JLabel lblHeaderTitle;
    private JTextField fldFirstName;
    private JTextField fldAddress;
    private JTextField fldPhone;
    private JTextField fldCity;
    private JTextField fldState;
    private JTextField fldPrimaryPhysician;
    private JTextField fldInsuranceCompany;
    private JTextField fldInsuranceGroupNumber;
    private JTextField fldLastName;
    private JTextField fldZip;
    private JTextField fldInsuranceMemberNumber;
    private JButton btnControlPanel;
    private JButton btnPatientSearch;
    private JButton btnPatientSummary;
    private JButton btnWellnessSummary;
    private JButton btnWellnessCheckups;
    private JButton btnAddUpdateWellness;
    private JButton btnAddNewPatient;
    private JButton btnUpdatePatientInformation;
    private JButton btnEnableUpdatePatientInfo;
    private JButton btnEnableAddNewPatient;

    public pnlAddUpdatePatientInfo(JPanel panel) {
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
        btnWellnessSummary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "5");
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
        btnUpdatePatientInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((fldFirstName != null)&&(fldLastName != null)) {
                    updatePatientInfo();
                    Main.panelPatientSearch.fillPatientSearch();
                    Main.panelPatientSummaryInfo.fillPatientSummary();
                }else{
                    JOptionPane.showMessageDialog(null, "Please fill out first and last name at a minimum.");
                }
                btnUpdatePatientInformation.setEnabled(false);
            }
        });
        btnAddNewPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  Main.workingID = null;
                if((fldFirstName != null)&&(fldLastName != null)) {
                    addNewPatientInfo();
                    Main.panelPatientSearch.fillPatientSearch();
                    Main.panelPatientSummaryInfo.fillPatientSummary();
                }else{
                    JOptionPane.showMessageDialog(null, "Please fill out first and last name at a minimum.");
                }
                btnAddNewPatient.setEnabled(false);
            }
        });
        btnEnableUpdatePatientInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnUpdatePatientInformation.setEnabled(true);
            }
        });
        btnEnableAddNewPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    btnAddNewPatient.setEnabled(true);
            }
        });
    }

    public void fillAddUpdatePatientInfo(){
        Connection con = null;
        Statement statement1 = null;
        ResultSet resultSet1 = null;
        try {
            con = new Main().openConnection();
            String sqlString1 = "select * from customerinfo where id = " + Main.workingID;
            statement1 = con.createStatement();
            resultSet1 = statement1.executeQuery(sqlString1);

            //load values in rs to fields
            while (resultSet1.next()) {
                fldFirstName.setText(resultSet1.getString("FirstName"));
                fldLastName.setText(resultSet1.getString("LastName"));
                fldPhone.setText(resultSet1.getString("Phone"));
                fldAddress.setText(resultSet1.getString("Address"));
                fldCity.setText(resultSet1.getString("City"));
                fldState.setText(resultSet1.getString("State"));
                fldZip.setText(resultSet1.getString("Zip"));
                fldPrimaryPhysician.setText(resultSet1.getString("Physician"));
                fldInsuranceCompany.setText(resultSet1.getString("Insurance"));
                fldInsuranceGroupNumber.setText(resultSet1.getString("GroupNumber"));
                fldInsuranceMemberNumber.setText(resultSet1.getString("MemberNumber"));
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
    public void updatePatientInfo(){
        Connection con = null;
        PreparedStatement prepStatement1 = null;
        try {
            con = new Main().openConnection();
            //update selected patient info based on field value text
            String sqlString1 = "Update customerinfo " +
                    "SET  FirstName = '" + fldFirstName.getText() +
                    "', LastName = '" + fldLastName.getText() +
                    "', Phone = '" + fldPhone.getText() +
                    "', Address = '" + fldAddress.getText() +
                    "', City = '" + fldCity.getText() +
                    "', State = '" + fldState.getText() +
                    "', Zip = '" + fldZip.getText() +
                    "', Physician = '" + fldPrimaryPhysician.getText() +
                    "', Insurance = '" + fldInsuranceCompany.getText() +
                    "', GroupNumber = '" + fldInsuranceGroupNumber.getText() +
                    "', MemberNumber = '" + fldInsuranceMemberNumber.getText() +
                    "' WHERE id = " + Main.workingID;
            prepStatement1 = con.prepareStatement(sqlString1);
            prepStatement1.executeUpdate();
            btnAddNewPatient.setEnabled(false);

            Main.panelPatientSearch.fillCboName();

            String name = fldFirstName + " " + fldLastName;
            Main.panelPatientSearch.cboNameList.setSelectedItem(new Item(Main.workingID, name));
            Main.panelControl.fillAllPanelFields();
        } catch (Exception b) {
            b.printStackTrace();
        } finally {
            try {
                prepStatement1.close();
                con.close();
            } catch (Exception c) {
                c.printStackTrace();
            }
        }
    }
    public void addNewPatientInfo(){
        Connection con = null;
        Statement statement1 = null;
        Statement statement2 = null;
        ResultSet resultSet1 = null;
        try {
            con = new Main().openConnection();
            //add selected patient info based on field value text
            String sqlString1 = "INSERT INTO customerinfo(Firstname, LastName, Phone, Address, City, State, Zip" +
                    ", Physician, Insurance, GroupNumber, MemberNumber) VALUES('" + fldFirstName.getText() + "', '" +
                    fldLastName.getText() + "', '" + fldPhone.getText() + "', '" + fldAddress.getText() + "', '" +
                    fldCity.getText() + "', '" + fldState.getText() + "', '" + fldZip.getText() + "', '" +
                    fldPrimaryPhysician.getText() + "', '" +fldInsuranceCompany.getText() + "', '" +
                    fldInsuranceGroupNumber.getText() + "', '" + fldInsuranceMemberNumber.getText() + "')";
            statement1 = con.createStatement();
            statement1.executeUpdate(sqlString1);
            btnAddNewPatient.setEnabled(false);

            String sqlString2 = "Select * from customerinfo";
            statement2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet1 = statement2.executeQuery(sqlString2);

            Main.panelPatientSearch.fillPatientSearch();

            String name = null;
            while (resultSet1.next()) {
                if(resultSet1.last()) {
                    Main.workingID = resultSet1.getInt(1);
                    name = resultSet1.getString(2) + " " + resultSet1.getString(3);
                 }
            }
            Main.panelPatientSearch.fillCboName();
            Main.panelPatientSearch.cboNameList.setSelectedItem(new Item(Main.workingID, name));
            Main.panelControl.fillAllPanelFields();
            Main.panelPatientSummaryInfo.clearWellnessFields();
            Main.panelWellnessLastCheckup.clearWellnessFields();
            Main.panelAddUpdateWellnessVisit.clearAllFields();
            Main.panelWellnessSummary.clearWellnessFields();

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
    }
}
