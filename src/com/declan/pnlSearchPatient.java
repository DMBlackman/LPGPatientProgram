package com.declan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class pnlSearchPatient {
    private JPanel contentPane;

    public JPanel pnl2;
    private JButton btnControlPanel;
    private JPanel pnlHeader;
    private JLabel lblHeaderTitle;
    private JTextField fldFirstName;
    private JTextField fldLastName;
    public JComboBox cboNameList;
    private JButton btnPopulate;
    private JTextField fldPhone;
    private JTextField fldAddress;
    private JTextField fldCity;
    private JTextField fldState;
    private JTextField fldZip;
    private JTextField fldPrimaryPhysician;
    private JTextField fldInsuranceCompany;
    private JTextField fldInsuranceGroupNumber;
    private JTextField fldInsuranceMemberNumber;
    private JButton btnAddUpdatePatient;
    private JButton btnWellnessSummary;
    private JButton btnLastWellnessCheck;
    private JButton btnAddUpdateWellness;
    private JButton btnPatientSummary;
    private JButton btnPatientSearch;

    public pnlSearchPatient(JPanel panel) {
        contentPane = panel;
        fillCboName();
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
            btnWellnessSummary.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                    cardLayout.show(contentPane, "5");
                }
            });
            btnLastWellnessCheck.addActionListener(new ActionListener() {
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

            //updates Main.workingId for referencing patient in other JPanels
            //Populates fields based on selected item in combobox
            btnPopulate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.panelControl.fillAllPanelFields();
                }
            });

        }

        //fill in field in Patient Search JPanel based on item selected
        public void fillPatientSearch(){
            Connection con = null;
            Statement statement1 = null;
            ResultSet resultSet1 = null;
            Item uniqueKey = (Item) cboNameList.getSelectedItem();
            Main.workingID = uniqueKey.getId();
            try {
                con = new Main().openConnection();
                String sqlString1 = "select * from customerinfo where id = " + Main.workingID;
                statement1 = con.createStatement();
                resultSet1 = statement1.executeQuery(sqlString1);

                //load values in rs to fields
                while (resultSet1.next()) {
                    fldFirstName.setText(resultSet1.getString(2));
                    fldLastName.setText(resultSet1.getString(3));
                    fldPhone.setText(resultSet1.getString(4));
                    fldAddress.setText(resultSet1.getString(5));
                    fldCity.setText(resultSet1.getString(6));
                    fldState.setText(resultSet1.getString(7));
                    fldZip.setText(resultSet1.getString(8));
                    fldPrimaryPhysician.setText(resultSet1.getString(9));
                    fldInsuranceCompany.setText(resultSet1.getString(10));
                    fldInsuranceGroupNumber.setText(resultSet1.getString(11));
                    fldInsuranceMemberNumber.setText(resultSet1.getString(12));
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

        //populate combobox on load. called in constructor
    public void fillCboName(){
        Connection con = null;
        Statement statement1 = null;
        ResultSet resultSet1 = null;
        cboNameList.removeAllItems();
        try{
            con = new Main().openConnection();
            String sqlString1 = "select * from customerinfo";
            statement1 = con.createStatement();
            resultSet1 = statement1.executeQuery(sqlString1);
            Item newItem;

            while (resultSet1.next()) {

                String name = resultSet1.getString(2) + " " + resultSet1.getString(3);
                int uniqueId = resultSet1.getInt(1);
                newItem = new Item(uniqueId, name);
                cboNameList.addItem(newItem);
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
}



